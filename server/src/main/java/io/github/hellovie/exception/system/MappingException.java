package io.github.hellovie.exception.system;

import io.github.hellovie.exception.IExceptionType;
import org.springframework.http.HttpStatus;

/**
 * 系统异常: 映射异常. <br>
 * 例如: 枚举类与 Spring bean 映射失败. <br>
 * Http Code: 500 <br>
 *
 * @author hellovie <br>
 * @version 1.0.0 2023/5/6 <br>
 * @Email hellovie@foxmail.com <br>
 * @since JDK 1.8
 */
public class MappingException extends SystemException {
    /**
     * 通过状态码和异常信息的枚举类初始化异常.
     *
     * @param exceptionType 异常状态码和异常信息的枚举类.
     */
    public MappingException(IExceptionType exceptionType) {
        super(exceptionType);
    }

    /**
     * 获取 HttpStatus.
     *
     * @return Http Code.
     */
    @Override
    public HttpStatus getHttpCode() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
