ALTER TABLE `user`
    ADD COLUMN `avatar` VARCHAR(32) NOT NULL COMMENT '用户头像' AFTER `username`,
    ADD CONSTRAINT `FK_User_File_On_Avatar`
        FOREIGN KEY (`avatar`) REFERENCES `file` (`id`);