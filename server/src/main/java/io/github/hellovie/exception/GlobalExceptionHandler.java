package io.github.hellovie.exception;

import io.github.hellovie.core.vo.ResultResponse;
import io.github.hellovie.exception.business.BusinessException;
import io.github.hellovie.exception.system.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 全局异常处理
 *
 * @author hellovie
 * @Email hellovie@foxmail.com
 * @createTime 2022/12/30 16:22
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /**
     * Http Code: 403(无权限访问！)
     * 无权限访问异常 spring security授权异常处理
     *
     * @param ex 异常信息
     * @return ResultResponse(code+message)
     */
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResultResponse accessDeniedHandler(Exception ex)
    {
        return ResultResponse.fail(HttpStatus.FORBIDDEN.value(), "无权限访问！");
    }

    /**
     * Http Code: 400(请求错误)
     * 1. 业务异常
     *
     * @param ex 异常信息
     * @return ResultResponse(code+message)
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            BusinessException.class, // 业务异常
    })
    public ResultResponse badRequestExceptionHandler(BusinessException ex)
    {
        // 记录日志
        log.warn("[#" + ex.getCode() + "]{ " + ex.getMessage() + " }");
        return ResultResponse.fail(ex.getCode(), ex.getMessage());
    }

    /**
     * Http Code: 500(服务器错误)
     * 1. 系统异常
     *
     * @param ex 异常信息
     * @return ResultResponse(code+message)
     */
    @ExceptionHandler({
            SystemException.class, // 系统异常
    })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultResponse systemExceptionHandler(SystemException ex)
    {
        // 记录日志
        log.error("[$" + ex.getCode() + "]{ " + ex.getMessage() + " }" + "\n==>" + getStackTrace(ex));
        return ResultResponse.fail(CommonExceptionType.UNKNOWN_EXCEPTION);
    }

    /**
     * Http Code: 500(未知错误)
     *
     * @param ex 异常信息
     * @return ResultResponse(code+message)
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultResponse exceptionHandler(Exception ex)
    {
        // 记录日志
        log.error("[$" + CommonExceptionType.UNKNOWN_EXCEPTION.getCode() + "]{ 捕获到未知错误！ }" + "\n==>" + getStackTrace(ex));
        return ResultResponse.fail(CommonExceptionType.UNKNOWN_EXCEPTION);
    }

    /**
     * 获取完整堆栈信息
     *
     * @param throwable throwable
     * @return 完整堆栈信息字符串
     */
    private static String getStackTrace(Throwable throwable)
    {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        try
        {
            throwable.printStackTrace(pw);
            return sw.toString();
        }
        finally
        {
            pw.close();
        }
    }
}
