package io.github.hellovie.exception;

import io.github.hellovie.ModuleManage;

/**
 * 调用成功统一状态码和提示信息
 *
 * @author hellovie
 * @Email hellovie@foxmail.com
 * @createTime 2023/4/19 12:26
 */
public enum CommonExceptionType implements IExceptionType {
    /** 调用成功 */
    SUCCESS(1, "调用成功！"),
    /** Request Object是字段验证失败 */
    VALIDATION_FAILED(2, "数据不符合要求！"),
    /** 无效访问或异常访问 */
    ACCESS_EXCEPTION(3, "访问异常！"),
    /** HTTP消息不可读异常 */
    HTTP_MESSAGE_NOT_READABLE(4, "无法解析请求参数！"),
    /** HTTP请求方法不支持异常 */
    HTTP_REQUEST_METHOD_NOT_SUPPORT(5, "无法解析请求方法！"),
    /** 未知Host异常 */
    UNKNOWN_HOST(6, "无法识别主机！"),
    /** 未知错误 */
    UNKNOWN_EXCEPTION(999, "服务器繁忙，请稍后再试！");

    /** 状态码 */
    private int code;
    /** 获取提示信息 */
    private String message;

    CommonExceptionType(int exceptionNum, String message) {
        this.code = ModuleManage.COMMON_MODULE.num() + exceptionNum;
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
