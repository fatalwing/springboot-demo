CREATE TABLE `demo`(
  `demo_id` varchar(40) PRIMARY KEY COMMENT 'id',
  `demo_name` varchar(200) NOT NULL COMMENT '名字',
  `version` bigint(20) DEFAULT 0,
  `date_created` datetime  DEFAULT CURRENT_TIMESTAMP,
  `last_updated` datetime  DEFAULT CURRENT_TIMESTAMP,
  `is_deleted` int(1) DEFAULT 0
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='demo';