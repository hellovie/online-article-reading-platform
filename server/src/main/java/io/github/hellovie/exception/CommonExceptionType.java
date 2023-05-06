package io.github.hellovie.exception;

import io.github.hellovie.ModuleManage;

/**
 * 通过模块异常状态信息枚举. <br>
 *
 * @author hellovie <br>
 * @version 1.0.0 2023/4/19 <br>
 * @Email hellovie@foxmail.com <br>
 * @since JDK 1.8
 */
public enum CommonExceptionType implements IExceptionType {
    /** 调用成功. */
    SUCCESS(0, "调用成功！"),
    /** Request Object 的字段验证失败. */
    VALIDATION_FAILED(1, "数据不符合要求！"),
    /** 无效访问或异常访问. */
    ACCESS_EXCEPTION(2, "访问异常！"),
    /** HTTP 消息不可读异常. */
    HTTP_MESSAGE_NOT_READABLE(3, "无法解析请求参数！"),
    /** HTTP 请求方法不支持异常. */
    HTTP_REQUEST_METHOD_NOT_SUPPORT(4, "无法解析请求方法！"),
    /** 未知 Host 异常. */
    UNKNOWN_HOST(5, "无法识别主机！"),
    /** 策略类型与策略组件无法映射. */
    STRATEGY_TYPE_NOT_MAPPER_STRATEGY_COMPONENT(5, "策略类型与策略组件无法映射！"),
    /** 未知错误. */
    UNKNOWN_EXCEPTION(999, "服务器繁忙，请稍后再试！");

    /** 状态码 */
    private int code;
    /** 提示信息 */
    private String message;

    CommonExceptionType(int exceptionNum, String message) {
        this.code = ModuleManage.COMMON_MODULE.num() + exceptionNum;
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
