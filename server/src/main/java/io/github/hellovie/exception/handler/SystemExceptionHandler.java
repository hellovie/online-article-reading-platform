package io.github.hellovie.exception.handler;

import io.github.hellovie.core.util.ResultResponse;
import io.github.hellovie.exception.system.SystemException;
import io.github.hellovie.exception.util.ExceptionStackUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static io.github.hellovie.exception.CommonExceptionType.UNKNOWN_EXCEPTION;

/**
 * 全局系统异常捕获处理, 最后再进行处理. <br>
 *
 * @author hellovie <br>
 * @version 1.0.0 2023/4/23 <br>
 * @Email hellovie@foxmail.com <br>
 * @since JDK 1.8
 */
@Order(100)
@RestControllerAdvice
@Slf4j
public class SystemExceptionHandler {
    /**
     * Http Code: 500(服务器错误.)
     * <p>SystemException 是所有系统异常的父类, 放置到最后进行捕获.</p>
     * <p>1. 系统异常.</p>
     * @param ex 异常信息.
     * @return ResultResponse (code + message).
     */
    @ExceptionHandler({
            SystemException.class, // 系统异常
    })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultResponse internalServerExceptionHandler(SystemException ex) {
        return errorException(ex);
    }

    /**
     * Http Code: 500(未知错误.)
     * <p>打印 Error 级别日志和异常信息栈日志, 记录为捕获到未知错误.</p>
     * @param ex 异常信息.
     * @return ResultResponse (code + message).
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultResponse exceptionHandler(Exception ex) {
        log.error("[$" + UNKNOWN_EXCEPTION.getCode() + "]{ 捕获到未知错误！ }" + "\n==>" + ExceptionStackUtil.getStackTrace(ex));
        return ResultResponse.fail(UNKNOWN_EXCEPTION);
    }

    /**
     * 错误异常, 系统异常统一处理. 统一返回 UNKNOWN_EXCEPTION.
     * <p>打印 Error 级别日志和异常信息栈日志.</p>
     * @param ex SystemException 及其子类.
     * @return UNKNOWN_EXCEPTION (服务器繁忙).
     */
    private ResultResponse errorException(SystemException ex) {
        log.error("[$" + ex.getCode() + "]{ " + ex.getMessage() + " }" + "\n==>" + ExceptionStackUtil.getStackTrace(ex));
        return ResultResponse.fail(UNKNOWN_EXCEPTION);
    }
}
