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
 * 全局业务异常捕获处理，最后再进行处理。
 *
 * @author hellovie
 * @Email hellovie@foxmail.com
 * @createTime 2023/4/23 16:55
 */
@Order(2)
@RestControllerAdvice
@Slf4j
public class BusinessExceptionHandler {
    /**
     * Http Code: 403(无权限访问！)
     * 用户角色无权限访问异常
     *
     * @param ex 异常信息
     * @return ResultResponse(code + message)
     */
    @ExceptionHandler({ForbiddenException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResultResponse accessDeniedHandler(BusinessException ex) {
        return warningException(ex);
    }

    /**
     * Http Code: 409(请求与服务器端目标资源的当前状态相冲突)
     * 1. 数据库字段值冲突异常
     *
     * @param ex 异常信息
     * @return ResultResponse(code + message)
     */
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler({
            DatabaseFieldConflictException.class, // 数据库字段值冲突异常
    })
    public ResultResponse conflictExceptionHandler(BusinessException ex) {
        return warningException(ex);
    }

    /**
     * Http Code: 404(服务器无法找到所请求的资源)
     * 1. 数据库字段值不存在异常
     *
     * @param ex 异常信息
     * @return ResultResponse(code + message)
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({
            DatabaseFieldNotFoundException.class, // 数据库字段值不存在异常
    })
    public ResultResponse notFoundExceptionHandler(BusinessException ex) {
        return warningException(ex);
    }

    /**
     * Http Code: 422(服务器理解请求实体的内容类型，并且请求实体的语法是正确的，但是服务器无法处理所包含的指令)
     * 1. 数据库字段值验证异常
     *
     * @param ex 异常信息
     * @return ResultResponse(code + message)
     */
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler({
            DatabaseFieldVerifyException.class, // 数据库字段值验证异常
    })
    public ResultResponse unprocessableEntityExceptionHandler(BusinessException ex) {
        return warningException(ex);
    }

    /**
     * Http Code: 400(请求错误)
     * BusinessException是所有业务异常的父类，放置到最后进行捕获。
     * 1. 业务异常
     *
     * @param ex 异常信息
     * @return ResultResponse(code + message)
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BusinessException.class)
    public ResultResponse badRequestExceptionHandler(BusinessException ex) {
        return warningException(ex);
    }

    /**
     * 警告异常
     * 系统异常统一处理，根据异常类的IExceptionType进行返回。
     * 打印Warn级别日志
     *
     * @param ex
     * @return IExceptionType(code + message)
     */
    private ResultResponse warningException(BusinessException ex) {
        log.warn("[#" + ex.getCode() + "]{ " + ex.getMessage() + " }");
        return ResultResponse.fail(ex.getCode(), ex.getMessage());
    }
}
