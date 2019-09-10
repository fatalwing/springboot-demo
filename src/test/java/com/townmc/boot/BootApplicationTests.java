package com.townmc.boot;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class BootApplicationTests {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void table2Bean() {
        String dbName = "test";
        String tableName = "demo";
        List<Map<String, Object>> cols = jdbcTemplate.queryForList("select COLUMN_NAME, DATA_TYPE, COLUMN_COMMENT from information_schema.COLUMNS where table_name = ? and table_schema = ?",
                new Object[]{tableName, dbName});
        String str = "public class " + this._2Hump(tableName, true) + " { \n";
        for (Map<String, Object> col : cols) {
            str += "    private " + this.type2Class((String) col.get("DATA_TYPE")) + " " + this._2Hump((String) col.get("COLUMN_NAME"), false) + "; // " + col.get("COLUMN_COMMENT") + "  \n";
        }
        str += "} \n";

        System.out.println(str);
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

}

