CREATE TABLE `user`
(
    `id`              VARCHAR(32) PRIMARY KEY COMMENT 'ID',
    `username`        VARCHAR(64)          NOT NULL COMMENT '用户名(索引)',
    `nickname`        VARCHAR(64)          NOT NULL COMMENT '用户昵称',
    `password`        VARCHAR(255)          NOT NULL COMMENT '密码(加密)',
    `locked`          TINYINT(1) DEFAULT 0 NOT NULL COMMENT '是否锁定(1-是, 0-否)',
    `enabled`         TINYINT(1) DEFAULT 1 NOT NULL COMMENT '是否启用(1-是, 0-否)',
    `last_login_ip`   VARCHAR(64)          NULL COMMENT '最后登录IP',
    `last_login_time` DATETIME(6)          NULL COMMENT '最后登录时间',
    `gmt_create`      DATETIME(6)          NOT NULL COMMENT '创建时间(GMT时间)',
    `gmt_modified`    DATETIME(6)          NOT NULL COMMENT '更新时间(GMT时间)',
    CONSTRAINT UK_User_On_Username
        UNIQUE (username)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT '用户表';


