package io.github.hellovie.exception.system;

import io.github.hellovie.exception.IExceptionType;
import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * 系统异常：服务器异常，交由开发者处理。
 * Http Code：500
 *
 * @author hellovie
 * @Email hellovie@foxmail.com
 * @createTime 2023/4/19 12:02
 */
@Data
public class SystemException extends RuntimeException {
    /** 异常状态码 */
    private int code;

    /**
     * 通过状态码和异常信息的枚举类初始化异常
     *
     * @param exceptionType 异常状态码和异常信息的枚举类
     */
    public SystemException(IExceptionType exceptionType) {
        super(exceptionType.getMessage());
        this.code = exceptionType.getCode();
    }

    /**
     * 获取HttpStatus
     *
     * @return Http Code
     */
    public HttpStatus getHttpCode() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}

