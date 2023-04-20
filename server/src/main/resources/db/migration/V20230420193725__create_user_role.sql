CREATE TABLE `user_role`
(
    `user_id` VARCHAR(32) NOT NULL COMMENT '用户ID',
    `role_id` VARCHAR(32) NOT NULL COMMENT '角色ID',
    CONSTRAINT FK_UserRole_User_On_UserId
        FOREIGN KEY (user_id) REFERENCES `user` (id),
    CONSTRAINT FK_UserRole_Role_On_RoleId
        FOREIGN KEY (role_id) REFERENCES `role` (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT '用户角色表';