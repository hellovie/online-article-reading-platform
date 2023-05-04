package io.github.hellovie.exception.business;

import io.github.hellovie.exception.IExceptionType;
import org.springframework.http.HttpStatus;

/**
 * 业务异常: 用户角色权限不足. <br>
 * Http Code: 403 <br>
 *
 * @author hellovie <br>
 * @version 1.0.0 2023/4/23 <br>
 * @Email hellovie@foxmail.com <br>
 * @since JDK 1.8
 */
public class ForbiddenException extends BusinessException {
    /**
     * 通过状态码和异常信息的枚举类初始化异常.
     *
     * @param exceptionType 异常状态码和异常信息的枚举类.
     */
    public ForbiddenException(IExceptionType exceptionType) {
        super(exceptionType);
    }

    /**
     * 获取 HttpStatus.
     *
     * @return Http Code.
     */
    @Override
    public HttpStatus getHttpCode() {
        return HttpStatus.FORBIDDEN;
    }
}
