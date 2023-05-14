package io.github.hellovie.user.domain.enums;

/**
 * 系统已存在的角色实例. <br>
 *
 * @author hellovie <br>
 * @version 1.0.0 2023/4/22 <br>
 * @Email hellovie@foxmail.com <br>
 * @since JDK 1.8
 */
public class RolesConstant {
    private RolesConstant() { }
    /** 普通用户 */
    public static final String ROLE_USER_KEY = "ROLE_USER";

    /** 普通管理员 */
    public static final String ROLE_ADMIN_KEY = "ROLE_ADMIN";

    /** 超级管理员 */
    public static final String ROLE_SUPER_ADMIN_KEY = "ROLE_SUPER_ADMIN";
}
