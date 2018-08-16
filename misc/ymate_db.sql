-- ----------------------------
--  Table structure for `ym_security_group`
-- ----------------------------
DROP TABLE IF EXISTS `ym_security_group`;
CREATE TABLE `ym_security_group` (
  `id` varchar(32) NOT NULL COMMENT '安全组唯一标识',
  `name` varchar(32) NOT NULL COMMENT '组名称',
  `permission` text COMMENT '权限列表',
  `is_admin_role` smallint(1) unsigned DEFAULT '0' COMMENT '是否管理员角色',
  `is_operator_role` smallint(1) unsigned DEFAULT '0' COMMENT '是否操作员角色',
  `is_user_role` smallint(1) DEFAULT '0' COMMENT '是否普通用户角色',
  `type` smallint(2) unsigned DEFAULT '0' COMMENT '类型',
  `status` smallint(2) unsigned DEFAULT '0' COMMENT '状态',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建者标识',
  `owner` varchar(32) DEFAULT NULL COMMENT '拥有者标识',
  `create_time` bigint(13) NOT NULL COMMENT '创建时间',
  `last_modify_time` bigint(13) DEFAULT '0' COMMENT '最后修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `group_name_UNIQUE` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='权限安全组';

-- ----------------------------
--  Table structure for `ym_security_group_user`
-- ----------------------------
DROP TABLE IF EXISTS `ym_security_group_user`;
CREATE TABLE `ym_security_group_user` (
  `id` varchar(32) NOT NULL COMMENT '安全组用户关系唯一标识',
  `group_id` varchar(32) NOT NULL COMMENT '安全组唯一标识',
  `u_id` varchar(32) NOT NULL COMMENT '用户唯一标识',
  `type` smallint(2) unsigned DEFAULT '0' COMMENT '类型',
  `status` smallint(2) unsigned DEFAULT '0' COMMENT '状态',
  `create_time` bigint(13) NOT NULL COMMENT '创建时间',
  `last_modify_time` bigint(13) DEFAULT '0' COMMENT '最后修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='权限安全组用户';