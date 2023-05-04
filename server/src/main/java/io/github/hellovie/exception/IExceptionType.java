package io.github.hellovie.exception;

/**
 * 异常类型枚举类需要实现的接口, 作为参数传入每个异常类的构造函数中. <br>
 * 规定每个异常类型枚举类都必须含有 code 和 message. <br>
 *
 * @author hellovie <br>
 * @version 1.0.0 2023/4/19 <br>
 * @Email hellovie@foxmail.com <br>
 * @since JDK 1.8
 */
public interface IExceptionType {
    /**
     * 获取异常状态码.
     *
     * @return 异常状态码.
     */
    int getCode();

    /**
     * 获取异常信息.
     *
     * @return 异常信息.
     */
    String getMessage();

    /**
     * 获取完整的异常信息 (code + message).
     *
     * @return json 格式的完整异常信息.
     */
    default String exceptionInfo() {
        return "{\n" + "\t\"code\": " + this.getCode() + ",\n" + "\t\"message\": " + this.getMessage() + ",\n" + "}";
    }
}
