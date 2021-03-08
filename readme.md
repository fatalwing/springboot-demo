## 工程目录
典型maven项目和springboot目录结构  
```
doc
 +- database.sql 数据库和用户的创建脚本
 +- ddl.sql 表的定义脚本
 +- dml.sql 数据库初始化数据脚本
 +- upgrade.sql 产品迭代过程中的数据库表更新脚本
src
 +- main
 |   +- java
 |   |   +- com
 |   |       +- townmc
 |   |           +- boot
 |   |               +- BootApplication.java  // springboot入口类
 |   |               |
 |   |               +- component // 相关组件
 |   |               |
 |   |               +- configuration // springboot配置文件目录
 |   |               |
 |   |               +- constants // 系统中使用的常量定义
 |   |               |
 |   |               +- controller // http请求控制器代码
 |   |               |
 |   |               +- dao // 持久化层代码 主要是JPA和mybatis，详见数据库文档
 |   |               |   |
 |   |               |   +- impl redis和基本sql的实现
 |   |               |   |
 |   |               |   +- mybatis
 |   |               | 
 |   |               +- domain // 实体bean代码
 |   |               |   |
 |   |               |   +- bo // 业务描述或在service间传递的实体定义
 |   |               |   |
 |   |               |   +- dto // 数据传输实体定义，包括业务与页面之间的参数传递
 |   |               |   |
 |   |               |   +- enums // 枚举实体的定义
 |   |               |   |
 |   |               |   +- po  // 数据库对应实体定义
 |   |               |  
 |   |               +- mq // 队列业务逻辑
 |   |               |  
 |   |               +- service // 业务逻辑层代码
 |   |               |
 |   |               +- utils // 帮助类
 |   |               |
 |   |               +- web // 网络请求相关定义,拦截器错误处理等
 |   |               |
 |   +- resources // 配置文件资源文件
 +- test // 测试代码
     +- java
     |
     +- resources
```

## 项目的部署  
### 项目需要的环境和资源  
1. jdk1.8  
2. mysql5.7
3. redis4.0+
4. rabbitmq  

### 数据库文档
数据库在设计上尽量避免需要多表关联的情况（可以做部分数据冗余）。  
每个表都必须有四个元数据（说明数据自身的数据）字段：  
`version`、`date_created`、`last_updated`、`deleted`  

数据库脚本放在doc下：`database.sql` 、`ddl.sql`、`dml.sql`、`upgrade.sql`       

项目中同时使用JPA和MyBatis两个持久化框架。  
> __约定：__  
> JPA用于insert、update、insert和update之前的校验查询，以及一些访问不频繁的单表查询；  
> MyBatis用于复杂多表关联查询和高频查询。      
> 便于将来迅速做读写分离（将Mybatis数据源配置为读库即可）。  

- JPA的接口直接写在dao包下  
- Mybatis的接口和xml写在dao.mybatis包下，  
    > 接口和xml均用`表名`(下划线转为大驼峰)+`Mapper`，  
      例如`user_info`表，对应的mapper接口名为： `UserInfoMapper.java`.  
      对应的sql定义文件名则为`UserInfoMapper.xml`。 

### JPA和Mybatis代码自动生成
创建好数据库表之后，可以根据表自动生成对应的持久化代码
#### JPA
JPA持久化需要一个实体PO（在domain的po下）和一个继承了JpaRepository的接口。  
在`test/java`下有一个`GenerateCodeByDb`类，可用于生成JPA需要的jpa接口和数据库实体类。  
配置好该类的包路径以及数据库访问口令，需要生成的目标table，运行main函数即可。  

#### MyBatis
MyBatis持久化需要一个实体PO、一个接口和一个对应的xml文件。  
配置好`src/main/resources`下的`generatorConfig.xml`(配置文件的注释可以参考doc下的该文件)。  

运行`mvn mybatis-generator:generate`会将配置文件中定义的`<table>`全部生成。因为实体PO统一使用JPA生成的对象，
因此这里会有重复生成的实体类，需要将其删除。  
生成的接口和sqlmap xml可以选择使用。  
_生成的接口需要加上`@Repository`标注，以供springboot进行对象自动注入。_  

### 图片验证码
当需要防止机器人登录或者注册的时候就需要用到图片验证码，图片验证码使用`com.google.code.kaptcha`包来生成图片，使用方法如下：  
1. 前端通过调用`/common/get-valid-image`接口，将获得如下返回：  
```
{
  "status": 200,
  "error": "0",
  "message": "",
  "timestamp": 1615212658241,
  "data": {
    "imageValidKey": "ba39a1d648e64871878dff51ee282ed6",
    "imageContent": "data:image/jpg;base64,/9j/4AAQSkZJRgA...=="
  }
}
```
`imageContent`可用于`<img>`标签的`src`属性，用户可看到一张带有混淆效果的4位字符。  
2. 前端获取用户输入的图片验证码值，
与上一步获得的`imageValidKey`一起作为参数调用`/common/get-image-valid-token?imageValidKey=xxx&imageValidCode=xxx`接口,
(xxx替换为实际值)，如果验证通过，得到的结果值为图片验证通过凭证，可以在具体的业务接口中作为值传递。  
验证凭证需要在1分钟內使用，否则将会失效。  

### 项目配置文件
配置文件的sample在doc下`application.yml`  
主要修改如下几处：  
1. 端口号   
```
server:
  port: 8888
```
服务将使用该配置的端口号启动  

2. 数据库连接   
```
    datasource:
        url: jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&serverTimezone=GMT%2B8&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true&useSSL=false
        username: root
        password: 123456
```
修改成创建的数据库地址  

3. redis配置  
```
    redis:
        host: 123456
        password: 123456
        port: 6379
        database: 6
        timeout: 1000
```
修改成对应的redis配置参数     

4. rabbitmq配置  
```
    rabbitmq:
        host: 123456
        port: 5672
        username: abc
        password: 123456
        virtual-host: /msg
```
修改成对应的rabbitmq配置参数  

### 编译打包
在项目源代码根目录下运行maven打包命令  

> 项目中可能有test依赖特定的测试环境，因此运行命令时务必用`-Dmaven.test.skip=true`命令将测试忽略

```
mvn clean package -Dmaven.test.skip=true
```

在target下会生成jar包：`sb-demo.jar`   

### 运行  
将`sb-demo.jar`和配置文件`application.yml`放在同一个目录下。   

用如下命令即可启动服务：   
```
nohup java -Xms512m -Xmx1g -Xmn512m -server -jar sb-demo.jar >> console.log 2>&1 &
```
可以根据需要配置内存的大小。  

查看服务log  
```
tail -f -n 200 console.log
```

如果在日志中能看到大概如下信息表示启动没有错误  
```
Tomcat started on port(s): 6002 (http) with context path ''
Started Application in 5.443 seconds (JVM running for 6.15)
```

同时用如下命令可查看到进程信息  
```
ps -ef|grep 'sb-demo'
```
