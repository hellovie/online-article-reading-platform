package io.github.hellovie.exception.business;

import io.github.hellovie.exception.IExceptionType;
import org.springframework.http.HttpStatus;

/**
 * 业务异常：数据库字段值冲突异常。
 * Http Code：409
 *
 * @author hellovie
 * @Email hellovie@foxmail.com
 * @createTime 2023/4/20 20:28
 */
public class DatabaseFieldConflictException extends BusinessException {
    /**
     * 通过状态码和异常信息的枚举类初始化异常
     *
     * @param exceptionType 异常状态码和异常信息的枚举类
     */
    public DatabaseFieldConflictException(IExceptionType exceptionType) {
        super(exceptionType);
    }

    /**
     * 获取HttpStatus
     *
     * @return Http Code
     */
    @Override
    public HttpStatus getHttpCode() {
        return HttpStatus.CONFLICT;
    }
}
