-- ----------------------------
--  Table structure for `ym_security_group`
-- ----------------------------
DROP TABLE IF EXISTS `ym_security_group`;
CREATE TABLE `ym_security_group` (
  `id` varchar(32) NOT NULL,
  `name` varchar(32) NOT NULL,
  `permission` text,
  `is_admin_role` smallint(1) unsigned DEFAULT '0',
  `is_operator_role` smallint(1) unsigned DEFAULT '0',
  `is_user_role` smallint(1) DEFAULT '0',
  `site_id` varchar(32) NOT NULL,
  `status` smallint(2) unsigned DEFAULT '0',
  `type` smallint(2) unsigned DEFAULT '0',
  `create_time` bigint(13) NOT NULL,
  `last_modify_time` bigint(13) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `group_site_UNIQUE` (`name`,`site_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `ym_security_group_user`
-- ----------------------------
DROP TABLE IF EXISTS `ym_security_group_user`;
CREATE TABLE `ym_security_group_user` (
  `id` varchar(32) NOT NULL,
  `group_id` varchar(32) NOT NULL,
  `uid` varchar(32) NOT NULL,
  `site_id` varchar(32) NOT NULL,
  `status` smallint(2) unsigned DEFAULT '0',
  `type` smallint(2) unsigned DEFAULT '0',
  `create_time` bigint(13) NOT NULL,
  `last_modify_time` bigint(13) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;