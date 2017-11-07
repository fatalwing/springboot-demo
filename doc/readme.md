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
 |   |               +- Application.java  // springboot入口
 |   |               |
 |   |               +- configuration // springboot配置文件
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
 |   |               +- web // 网络请求入口。 控制器等代码
 |   |                   +- MainController.java
 |   +- resources // 配置文件资源文件
 +- test // 测试代码
     +- java
     |
     +- resources
```