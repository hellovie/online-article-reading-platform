package io.github.hellovie.exception;

import lombok.Data;

import java.util.Date;

/**
 * 返回前端信息封装
 *
 * @author hellovie
 * @Email hellovie@foxmail.com
 * @createTime 2023/4/19 12:02
 */
@Data
public class ResultResponse<T> {
    /** 响应数据 */
    private T data;

    /** 响应描述 */
    private String message;

    /** 响应状态码 */
    private int code;

    /** 接口调用时间 */
    private String timestamp;

    /**
     * 无参构造默认生成当前时间戳
     */
    public ResultResponse() {
        Date date = new Date();
        this.timestamp = date.toString();
    }

    /**
     * 接口调用成功，携带数据
     *
     * @param data 传递数据
     * @param <T> 数据类型
     * @return 数据封装集
     */
    public static <T> ResultResponse<T> success(T data) {
        ResultResponse<T> result = new ResultResponse<>();
        result.setCode(CommonExceptionType.SUCCESS.getCode());
        result.setMessage(CommonExceptionType.SUCCESS.getMessage());
        result.setData(data);
        return result;
    }

    /**
     * 接口调用失败，携带错误信息
     *
     * @param exceptionType 状态码和提示信息的枚举类
     * @param <T>
     * @return 数据封装集
     */
    public static <T> ResultResponse<T> fail(IExceptionType exceptionType) {
        int code = exceptionType.getCode();
        String message = exceptionType.getMessage();
        ResultResponse<T> result = new ResultResponse<>();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    /**
     * 接口调用失败，携带错误信息
     *
     * @param code 状态码
     * @param message 错误提示信息
     * @param <T>
     * @return 数据封装集
     */
    public static <T> ResultResponse<T> fail(int code, String message) {
        ResultResponse<T> result = new ResultResponse<>();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }
}
