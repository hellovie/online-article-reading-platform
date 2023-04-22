package io.github.hellovie.user.domain.enums;

import lombok.AllArgsConstructor;

/**
 * 系统已存在的角色实例
 *
 * @author hellovie
 * @Email hellovie@foxmail.com
 * @createTime 2023/4/22 13:14
 */
@AllArgsConstructor
public enum RoleEnum {
    /** 普通管理员 */
    ROLE_ADMIN("2OlcAyRpp1Bx2W1Vjm6gAj4LW6o"),
    /** 普通用户 */
    ROLE_USER("2OlcB1ojrqMknyLcLM01XwRTNS0"),

    ;
    /** 用户角色Id */
    private String roleId;

    /**
     * 获取角色ID
     *
     * @return 角色ID
     */
    public String roleId() {
        return roleId;
    }
}
