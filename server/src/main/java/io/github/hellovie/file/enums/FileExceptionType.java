package io.github.hellovie.file.enums;

import io.github.hellovie.ModuleManage;
import io.github.hellovie.exception.IExceptionType;

/**
 * 文件模块异常. <br>
 *
 * @author hellovie <br>
 * @version 1.0.0 2023/5/19 <br>
 * @Email hellovie@foxmail.com <br>
 * @since JDK 1.8
 */
public enum FileExceptionType implements IExceptionType {
    ;

    /** 状态码 */
    private int code;
    /** 获取提示信息 */
    private String message;

    FileExceptionType(int exceptionNum, String message) {
        this.code = ModuleManage.USER_MODULE.num() + exceptionNum;
        this.message = message;
    }

    /**
     * 获取异常状态码.
     *
     * @return 异常状态码.
     */
    @Override
    public int getCode() {
        return this.code;
    }

    /**
     * 获取异常信息.
     *
     * @return 异常信息.
     */
    @Override
    public String getMessage() {
        return this.message;
    }
}