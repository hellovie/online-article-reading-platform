package io.github.hellovie.exception.handler;

import io.github.hellovie.core.util.ResultResponse;
import io.github.hellovie.exception.business.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局业务异常捕获处理, 处理完第三方异常后才开始处理. <br>
 *
 * @author hellovie <br>
 * @version 1.0.0 2023/4/23 <br>
 * @Email hellovie@foxmail.com <br>
 * @since JDK 1.8
 */
@Order(2)
@RestControllerAdvice
@Slf4j
public class BusinessExceptionHandler {
    /**
     * Http Code: 403 (无权限访问.)
     * <p>用户角色无权限访问异常.</p>
     *
     * @param ex 异常信息.
     * @return ResultResponse (code + message).
     */
    @ExceptionHandler({ForbiddenException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResultResponse accessDeniedHandler(BusinessException ex) {
        return warningException(ex);
    }

    /**
     * Http Code: 409 (请求与服务器端目标资源的当前状态相冲突.)
     * <p>1. 数据库字段值冲突异常.</p>
     *
     * @param ex 异常信息.
     * @return ResultResponse (code + message).
     */
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler({
            DatabaseFieldConflictException.class, // 数据库字段值冲突异常
    })
    public ResultResponse conflictExceptionHandler(BusinessException ex) {
        return warningException(ex);
    }

    /**
     * Http Code: 404 (服务器无法找到所请求的资源.)
     * <p>1. 数据库字段值不存在异常.</p>
     *
     * @param ex 异常信息.
     * @return ResultResponse (code + message).
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({
            DatabaseFieldNotFoundException.class, // 数据库字段值不存在异常
    })
    public ResultResponse notFoundExceptionHandler(BusinessException ex) {
        return warningException(ex);
    }

    /**
     * Http Code: 422(服务器理解请求实体的内容类型, 并且请求实体的语法是正确的, 但是服务器无法处理所包含的指令.)
     * <p>1. 数据库字段值验证异常.</p>
     *
     * @param ex 异常信息.
     * @return ResultResponse (code + message).
     */
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler({
            DatabaseFieldVerifyException.class, // 数据库字段值验证异常
    })
    public ResultResponse unprocessableEntityExceptionHandler(BusinessException ex) {
        return warningException(ex);
    }

    /**
     * Http Code: 400 (请求错误.)
     * <p>BusinessException 是所有业务异常的父类, 放置到最后进行捕获.</p>
     * <p>1. 业务异常.</p>
     *
     * @param ex 异常信息.
     * @return ResultResponse (code + message).
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BusinessException.class)
    public ResultResponse badRequestExceptionHandler(BusinessException ex) {
        return warningException(ex);
    }

    /**
     * 警告异常, 系统异常统一处理. 根据异常类的 IExceptionType 进行返回.
     * <p>打印 Warn 级别日志.</p>
     *
     * @param ex BusinessException 及其子类.
     * @return IExceptionType (code + message).
     */
    private ResultResponse warningException(BusinessException ex) {
        log.warn("[#" + ex.getCode() + "]{ " + ex.getMessage() + " }");
        return ResultResponse.fail(ex.getCode(), ex.getMessage());
    }
}
