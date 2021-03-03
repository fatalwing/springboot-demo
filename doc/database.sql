-- 采用mysql5.7版本数据库。--

-- 安装好mysql后通过root用户创建数据库和创建用户：--
CREATE DATABASE `demo` DEFAULT CHARACTER SET utf8mb4 DEFAULT COLLATE utf8mb4_unicode_ci;
CREATE USER 'demo'@'localhost' IDENTIFIED BY 'Demo#123456';
CREATE USER 'demo'@'%' IDENTIFIED BY 'Demo#123456';

GRANT ALL PRIVILEGES ON demo.* to 'demo'@'localhost';
GRANT ALL PRIVILEGES ON  demo.* to 'demo'@'%';
flush privileges;
