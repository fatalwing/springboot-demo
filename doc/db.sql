CREATE TABLE `demo`(
  `demo_id` varchar(40) PRIMARY KEY COMMENT 'id',
  `demo_name` varchar(200) NOT NULL COMMENT '名字',
  `status` varchar(20) COMMENT '状态, normal/warn',
  `version` bigint(20) DEFAULT 0,
  `date_created` datetime  DEFAULT CURRENT_TIMESTAMP,
  `last_updated` datetime  DEFAULT CURRENT_TIMESTAMP,
  `deleted` int(1) DEFAULT 0
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='demo';