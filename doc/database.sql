-- 采用mysql5.7版本数据库。--

-- 安装好mysql后通过root用户创建数据库和创建用户：--
CREATE DATABASE `test` DEFAULT CHARACTER SET utf8mb4 DEFAULT COLLATE utf8mb4_unicode_ci;
CREATE USER 'test'@'localhost' IDENTIFIED BY 'Test#123456';
CREATE USER 'test'@'%' IDENTIFIED BY 'Test#123456';

GRANT ALL PRIVILEGES ON tuotuo.* to 'test'@'localhost';
GRANT ALL PRIVILEGES ON  tuotuo.* to 'test'@'%';
flush privileges;

-- 建表
CREATE TABLE `demo` (
  `id` varchar(40) PRIMARY KEY COMMENT 'id',
  `name` varchar(100) NOT NULL COMMENT '名称',
  `version` bigint(20) DEFAULT 0,
  `date_created` datetime  DEFAULT CURRENT_TIMESTAMP,
  `last_updated` datetime  DEFAULT CURRENT_TIMESTAMP,
  `deleted` int(1) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='demo';
