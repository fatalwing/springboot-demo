CREATE TABLE `demo` (
  `id` varchar(40) PRIMARY KEY COMMENT 'id',
  `name` varchar(100) NOT NULL COMMENT '名称',
  `version` bigint(20) DEFAULT 0,
  `date_created` datetime  DEFAULT CURRENT_TIMESTAMP,
  `last_updated` datetime  DEFAULT CURRENT_TIMESTAMP,
  `deleted` int(1) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='demo';
