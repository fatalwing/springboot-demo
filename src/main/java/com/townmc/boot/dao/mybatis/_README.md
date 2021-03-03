### 文件名命名规范约定
接口和xml均用`表名`(下划线转为大驼峰)+`Mapper`，  
例如`user_info`表，对应的mapper接口名为： `UserInfoMapper.java`.  
对应的sql定义文件名则为`UserInfoMapper.xml`。  

### 分页
分页统一在`service`中使用`pagehelper`插件，不需要在sql中写`limit`。  
使用方法参看`DemoService`的`findAllByName`  
