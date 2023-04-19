package io.github.hellovie.exception.business;

import io.github.hellovie.exception.IExceptionType;
import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * 业务异常：客户端异常，用户可以通过提示信息处理。
 * Http Code：400
 *
 * @author hellovie
 * @Email hellovie@foxmail.com
 * @createTime 2023/4/19 12:02
 */
@Data
public class BusinessException extends RuntimeException {
    /** 异常状态码 */
    private int code;

    /**
     * 通过状态码和异常信息的枚举类初始化异常
     *
     * @param exceptionType 异常状态码和异常信息的枚举类
     */
    public BusinessException(IExceptionType exceptionType) {
        super(exceptionType.getMessage());
        this.code = exceptionType.getCode();
    }

    /**
     * 获取HttpStatus
     *
     * @return Http Code
     */
    public HttpStatus getHttpCode() {
        return HttpStatus.BAD_REQUEST;
    }
}
