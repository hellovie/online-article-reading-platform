package io.github.hellovie.exception.business;

import io.github.hellovie.exception.IExceptionType;
import org.springframework.http.HttpStatus;

/**
 * 业务异常：用户角色权限不足。
 * Http Code：403
 *
 * @author hellovie
 * @Email hellovie@foxmail.com
 * @createTime 2023/4/23 0:00
 */
public class ForbiddenException extends BusinessException {
    /**
     * 通过状态码和异常信息的枚举类初始化异常
     *
     * @param exceptionType 异常状态码和异常信息的枚举类
     */
    public ForbiddenException(IExceptionType exceptionType) {
        super(exceptionType);
    }

    /**
     * 获取HttpStatus
     *
     * @return Http Code
     */
    @Override
    public HttpStatus getHttpCode() {
        return HttpStatus.FORBIDDEN;
    }
}
