CREATE TABLE `role`
(
    `id`           VARCHAR(32) PRIMARY KEY COMMENT 'ID',
    `role_key`     VARCHAR(64) NOT NULL COMMENT '角色标识(索引)',
    `role_name`    VARCHAR(64) NOT NULL COMMENT '角色昵称(用于显示的角色昵称)',
    `gmt_create`   DATETIME(6) NOT NULL COMMENT '创建时间(GMT时间)',
    `gmt_modified` DATETIME(6) NOT NULL COMMENT '更新时间(GMT时间)',
    CONSTRAINT UK_Role_On_RoleKey
        UNIQUE (role_key)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT '角色表';