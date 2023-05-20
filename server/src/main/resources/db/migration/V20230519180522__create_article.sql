CREATE TABLE `article`
(
    `id`            VARCHAR(32) PRIMARY KEY COMMENT 'ID',
    `title`         VARCHAR(50)  NOT NULL COMMENT '标题',
    `cover`         VARCHAR(32)  NULL COMMENT '封面',
    `abstract`      VARCHAR(255) NOT NULL COMMENT '摘要',
    `body`          TEXT         NOT NULL COMMENT '文章主体',
    `status`        VARCHAR(32)  NOT NULL DEFAULT 'DRAFT' COMMENT '状态: 默认 (DRAFT)',
    `author`        VARCHAR(32)  NOT NULL COMMENT '作者',
    `creation_type` VARCHAR(32)  NOT NULL COMMENT '创作类型: ORIGINAL(原创), REPRODUCE(转载), TRANSLATE(翻译)',
    `copyright`     VARCHAR(255) NOT NULL COMMENT '版权',
    `gmt_create`    DATETIME(6)  NOT NULL COMMENT '创建时间(GMT时间)',
    `gmt_modified`  DATETIME(6)  NOT NULL COMMENT '更新时间(GMT时间)',
    CONSTRAINT `FK_Article_File_On_Cover`
        FOREIGN KEY (`cover`) REFERENCES `file` (`id`),
    CONSTRAINT `FK_Article_User_On_Author`
        FOREIGN KEY (`author`) REFERENCES `user` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT '文章表';



