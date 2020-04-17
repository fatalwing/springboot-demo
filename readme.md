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

### 数据库
项目最好独立建数据库以免表名冲突  
数据库脚本请见doc下的`database.sql`  

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
