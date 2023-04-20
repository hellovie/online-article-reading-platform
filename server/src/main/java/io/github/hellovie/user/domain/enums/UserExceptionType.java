package io.github.hellovie.user.domain.enums;

import io.github.hellovie.ModuleManage;
import io.github.hellovie.exception.IExceptionType;

/**
 * 用户模块异常
 *
 * @author hellovie
 * @Email hellovie@foxmail.com
 * @createTime 2023/4/20 23:25
 */
public enum UserExceptionType implements IExceptionType {
    /** 用户已存在 */
    USER_EXIST(1, "用户已存在！"),
    /** 用户不存在 */
    USER_NOT_FOUND(2, "用户不存在！"),
    /** 账号或密码错误 */
    LOGIN_FAILED(3, "账号或密码错误！"),

    ;

    /** 状态码 */
    private int code;
    /** 获取提示信息 */
    private String message;

    UserExceptionType(int exceptionNum, String message) {
        this.code = ModuleManage.USER_MODULE.num() + exceptionNum;
        this.message = message;
    }

    /**
     * 获取状态码
     *
     * @return 状态码
     */
    @Override
    public int getCode() {
        return this.code;
    }

    /**
     * 获取提示信息
     *
     * @return 提示信息
     */
    @Override
    public String getMessage() {
        return this.message;
    }
}
