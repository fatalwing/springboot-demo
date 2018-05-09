## 工程目录
典型maven项目和springboot目录结构  
```
doc
 |
src
 +- main
 |   +- java
 |   |   +- com
 |   |       +- townmc
 |   |           +- demo
 |   |               +- Application.java  // springboot入口类
 |   |               |
 |   |               +- configuration // springboot配置文件目录
 |   |               |
 |   |               +- controller // http请求控制器代码
 |   |               |
 |   |               +- dao // 持久化层代码
 |   |               |
 |   |               +- domain // 实体bean代码
 |   |               |   +- dto // 数据传输实体定义
 |   |               |   |
 |   |               |   +- po  // 数据库对应实体定义
 |   |               |   |
 |   |               +- service // 业务逻辑层代码
 |   |               |
 |   |               +- utils // 帮助类
 |   |               |
 |   |               +- web // 网络请求相关定义,拦截器错误处理等
 |   |               |
 |   +- resources // 配置文件资源文件
 |   |   +- application-dev.yml // 开发环境配置
 |   |   |
 |   |   +- application-product.yml // 生产环境配置
 +- test // 测试代码
     +- java
     |
     +- resources
```

## 编译打包启动
在项目根目录下运行maven打包命令  
```
mvn clean package -Dmaven.test.skip=true
```
在target下会生成jar包，名字根据为pom中定义产生，artifactId-version.jar  
可以指定对应环境(product/dev)的配置文件运行jar包并输出控制台打印：  
```
nohup java -jar artifactId-version.jar --spring.profiles.active=product >> console.log 2>&1 &

## dao层规范
### 基本操作使用Jpa方式处理
```
public interface JpaDao extends JpaRepository<Demo, String> {
}
```
这种方式不需要做具体的实现即可完成基本的CRUD，简单、不容易出错。  

### 复杂操作使用Sql处理
定义好接口后，在impl包中做实现，直接使用JdbcTemplate查询。操作接口自行参照JdbcTemplate文档。  
```
@Repository
public class SqlDaoImpl implements SqlDao {
    @Autowired private JdbcTemplate jdbcTemplate;
    
    public Object xxx() {
    
    }
}
```
这种方式主要用于处理不得已的多表关联查询和一些特殊的sql处理，查询灵活但容易出现bug，需要谨慎。尽量不要用于增删改。

### 临时数据使用redis存储
根据数据定义好接口，在impl包中做实现，使用RedisTemplate操作redis。操作接口自行参照RedisTemplate文档。  
```
@Component
public class RedisDaoImpl implements RedisDao {
    @Autowired private RedisTemplate redisTemplate;

    public Object xxx() {
    
    }
}
```