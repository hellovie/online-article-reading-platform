CREATE TABLE `file`
(
    `id`                 VARCHAR(32)                     NOT NULL
        PRIMARY KEY COMMENT '文件ID',
    `name`               VARCHAR(64)                     NOT NULL COMMENT '文件名',
    `file_key`           VARCHAR(255)                    NOT NULL COMMENT '文件hash值',
    `path`               VARCHAR(255)                    NOT NULL COMMENT '相对路径',
    `ext`                VARCHAR(12)                     NOT NULL COMMENT '文件后缀',
    `size`               BIGINT      DEFAULT 0           NOT NULL COMMENT '文件大小(byte)',
    `type`               VARCHAR(32)                     NOT NULL COMMENT '文件类型',
    `storage`            VARCHAR(16)                     NOT NULL COMMENT '存储供应商: 本地-LOCAL_STORAGE',
    `status`             VARCHAR(32) DEFAULT 'UPLOADING' NOT NULL COMMENT '文件状态: UPLOADING-上传中, UPLOADED-已上传, CANCEL-已取消',
    `created_by_user_id` VARCHAR(32)                     NULL COMMENT '创建者用户ID',
    `updated_by_user_id` VARCHAR(32)                     NULL COMMENT '更新者用户ID',
    `gmt_create`         DATETIME(6)                     NOT NULL COMMENT '创建时间(GMT时间)',
    `gmt_modified`       DATETIME(6)                     NOT NULL COMMENT '更新时间(GMT时间)',
    CONSTRAINT `UK_File_On_FileKey`
        UNIQUE (`file_key`),
    CONSTRAINT `FK_File_User_On_CreatedByUserId`
        FOREIGN KEY (`created_by_user_id`) REFERENCES `user` (`id`),
    CONSTRAINT `FK_File_User_On_UpdatedByUserId`
        FOREIGN KEY (`updated_by_user_id`) REFERENCES `user` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT '文件表';