import com.townmc.utils.StringUtil;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GenerateCodeByDb {
    private static final String DAO_PACKAGE = "com.lianwuzizai.operation.dao";
    private static final String PO_PACKAGE = "com.lianwuzizai.operation.domain.po";

    private static final String DB_URL = "jdbc:mysql://59.110.6.168:3306/operation?useUnicode=true&serverTimezone=GMT%2B8&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true&useSSL=false"; // 数据库地址
    private static final String DB_USER = "root"; // 数据库用户名
    private static final String DB_PWD = "Dianka0522"; // 数据库密码

    private static final String[] TABLES = new String[]{"store_audit_record"}; // 要生成代码的表

    private String rootPath = ""; // 项目目录 D:/workspaces/demo 不指定时程序自动获取（但不确定有效，只测试过mac系统）

    private String daoPath, poPath, dbName;

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    GenerateCodeByDb() {

        if (blankStr(DB_URL) || blankStr(DB_USER) || blankStr(DB_PWD)) {
            throw new RuntimeException("请配置数据库参数！`DB_URL`,`DB_USER`,`DB_PWD`");
        }

        Pattern pattern = Pattern.compile("jdbc:mysql://(.+)?:(\\d+)?/(.+)\\?.*");
        Matcher matcher = pattern.matcher(DB_URL);
        if (matcher.find()) {
            dbName = matcher.group(3);
        } else {
            throw new RuntimeException("数据库参数`DB_URL`格式配置不正确!");
        }

        if (null == TABLES || TABLES.length < 1) {
            throw new RuntimeException("请指定要生成代码的表名！`TABLES`参数");
        }

        // 定位项目路径
        if (blankStr(rootPath)) {
            rootPath = System.getProperty("user.dir");
        }
        if (!rootPath.endsWith("/")) {
            rootPath = rootPath + "/";
        }

        daoPath = rootPath + "src/main/java/" + DAO_PACKAGE.replaceAll("\\.", "/") + "/";
        poPath = rootPath + "src/main/java/" + PO_PACKAGE.replaceAll("\\.", "/") + "/";

        // 数据库连接
        HikariConfig config = new HikariConfig();
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        config.setJdbcUrl(DB_URL);
        config.setUsername(DB_USER);
        config.setPassword(DB_PWD);
        config.setMaximumPoolSize(100);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.setConnectionTimeout(20 * 1000);
        if (dataSource == null) {
            dataSource = new HikariDataSource(config);
        }

        jdbcTemplate = new JdbcTemplate(dataSource);

    }

    public static void main(String ... args) {
        GenerateCodeByDb generateCodeByDb = new GenerateCodeByDb();
        generateCodeByDb.start();
    }

    public void start() {

        for(String tableName : TABLES) {
            String poObject = this.createPo(tableName);
            this.createDao(poObject);
        }

    }

    private String createPo(String tableName) {
        List<Map<String, Object>> cols = jdbcTemplate.queryForList("select COLUMN_NAME, DATA_TYPE, COLUMN_COMMENT, COLUMN_KEY from information_schema.COLUMNS where table_name = ? and table_schema = ?",
                new Object[]{tableName, dbName});


        String packageName = "package " + PO_PACKAGE + ";\n";

        String importPackage = "";
        importPackage += "import lombok.AllArgsConstructor;\n" +
                "import lombok.Builder;\n" +
                "import lombok.Data;\n" +
                "import lombok.NoArgsConstructor;\n" +
                "import javax.persistence.Entity;\n" +
                "import javax.persistence.Id; \n";

        String str = "" +
                "@Data \n" +
                "@Builder \n" +
                "@AllArgsConstructor \n" +
                "@NoArgsConstructor \n" +
                "@Entity \n";
        str += "public class " + this._2Hump(tableName, true) + " extends MetadataColumn { \n\n";
        for (Map<String, Object> col : cols) {

            String columnName = (String)col.get("COLUMN_NAME");
            if (StringUtil.inSet(columnName, Arrays.asList(new String[]{"version", "date_created", "last_updated", "deleted"}))) {
                continue;
            }

            String type = this.type2Class((String) col.get("DATA_TYPE"));
            if (type.equals("Date") && !importPackage.contains("import java.util.Date;")) {
                importPackage += "import java.util.Date;\n";
            } else if(type.equals("BigDecimal") && !importPackage.contains("import java.math.BigDecimal;")) {
                importPackage += "import java.math.BigDecimal;\n";
            }

            if ("PRI".equals(col.get("COLUMN_KEY"))) {
                str += "    @Id\n";
            }
            str += "    private " + type + " " + this._2Hump(columnName, false) + "; // " + col.get("COLUMN_COMMENT") + "  \n";
        }
        str += "\n} \n";

        String content = packageName + "\n" + importPackage + "\n" + str;

        try {
            File file = new File(poPath + this._2Hump(tableName, true) + ".java");
            if (file.exists()) {
                throw new RuntimeException("创建失败！ 对象" + this._2Hump(tableName, true) + "已经存在！");
            }
            OutputStream outstr = new FileOutputStream(file);
            outstr.write(content.getBytes());
            outstr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return this._2Hump(tableName, true);
    }

    private String createDao(String objectName) {
        String packageName = "package " + DAO_PACKAGE + ";\n";

        String importPackage = "" +
                "import " + PO_PACKAGE + "." + objectName + ";\n" +
                "import org.springframework.data.jpa.repository.JpaRepository;\n";

        String str = "";
        String jpaObjectName = objectName + "JpaDao";
        str += "public interface " + jpaObjectName + " extends JpaRepository<" + objectName + ", String> {\n\n";
        str += "    " + objectName + " findByIdAndDeleted(String id, int deleted);";
        str += "\n}\n";

        String content = packageName + "\n" + importPackage + "\n" + str;

        try {
            File file = new File(daoPath + jpaObjectName + ".java");
            if (file.exists()) {
                throw new RuntimeException("创建失败！ 对象" + jpaObjectName + "已经存在！");
            }
            OutputStream outstr = new FileOutputStream(file);
            outstr.write(content.getBytes());
            outstr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return jpaObjectName;
    }

    private String _2Hump(String str, boolean firstUp) {
        String[] arr = str.split("_");
        String re = "";
        for (String a : arr) {
            re += a.substring(0, 1).toUpperCase() + a.substring(1);
        }
        if (!firstUp) {
            return re.substring(0, 1).toLowerCase() + re.substring(1);
        }
        return re;
    }

    /**
     * 根据数据库类型返回java对象类型
     * @param type
     * @return
     */
    private String type2Class(String type) {
        if ("varchar".equals(type) || "text".equals(type)) {
            return "String";
        } else if ("int".equals(type)) {
            return "Integer";
        } else if ("bigint".equals(type)) {
            return "Long";
        } else if ("datetime".equals(type)) {
            return "Date";
        } else if ("decimal".equals(type)) {
            return "BigDecimal";
        } else {
            return "String";
        }
    }

    /**
     * 判断字符对象
     * @param str
     * @return
     */
    private static boolean blankStr(String str) {
        return (null == str || "".equals(str.trim()));
    }
}
