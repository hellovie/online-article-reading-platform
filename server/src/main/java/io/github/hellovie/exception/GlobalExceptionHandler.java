package io.github.hellovie.exception;

import io.github.hellovie.core.ResultResponse;
import io.github.hellovie.exception.business.*;
import io.github.hellovie.exception.system.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.UnknownHostException;
import java.util.List;

import static io.github.hellovie.exception.CommonExceptionType.*;

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
     * @return ResultResponse(code + message)
     */
    @ExceptionHandler({AccessDeniedException.class, ForbiddenException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResultResponse accessDeniedHandler(Exception ex) {
        if (ex.getClass() == ForbiddenException.class) {
            ForbiddenException forbiddenEx = (ForbiddenException) ex;
            return ResultResponse.fail(forbiddenEx.getCode(), forbiddenEx.getMessage());
        }
        return ResultResponse.fail(HttpStatus.FORBIDDEN.value(), "无权限访问！");
    }

    /**
     * Http Code: 400(请求错误)
     * 请求数据校验失败
     *
     * @param ex 异常信息
     * @return ResultResponse(code + message)
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultResponse validationExceptionHandler(MethodArgumentNotValidException ex) {

        BindingResult exceptions = ex.getBindingResult();
        // 判断异常中是否有错误信息，如果存在就使用异常中的消息，否则使用默认消息
        if (exceptions.hasErrors()) {
            List<ObjectError> errors = exceptions.getAllErrors();
            if (!errors.isEmpty()) {
                // 这里列出了全部错误参数，按正常逻辑，只需要第一条错误即可
                FieldError fieldError = (FieldError) errors.get(0);
                return ResultResponse.fail(VALIDATION_FAILED.getCode(), fieldError.getDefaultMessage());
            }
        }
        return ResultResponse.fail(VALIDATION_FAILED);
    }

    /**
     * Http Code: 400(请求错误)
     * 1. 请求数据参数不符合要求
     * 2. 请求方法有误
     * 3. 未知Host异常
     *
     * @param ex 异常信息
     * @return ResultResponse(code + message)
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            HttpMessageNotReadableException.class, // HTTP消息不可读异常
            HttpRequestMethodNotSupportedException.class, // HTTP请求方法不支持异常
            UnknownHostException.class // 未知Host异常
    })
    public ResultResponse HTTPExceptionHandler(Exception ex) {
        if (ex.getClass() == HttpMessageNotReadableException.class) {
            return ResultResponse.fail(HTTP_MESSAGE_NOT_READABLE);
        } else if (ex.getClass() == HttpRequestMethodNotSupportedException.class) {
            return ResultResponse.fail(HTTP_REQUEST_METHOD_NOT_SUPPORT);
        } else if (ex.getClass() == UnknownHostException.class) {
            return ResultResponse.fail(UNKNOWN_HOST);
        }
        return ResultResponse.fail(ACCESS_EXCEPTION);
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
        // 记录日志
        log.warn("[#" + ex.getCode() + "]{ " + ex.getMessage() + " }");
        return ResultResponse.fail(ex.getCode(), ex.getMessage());
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
        // 记录日志
        log.warn("[#" + ex.getCode() + "]{ " + ex.getMessage() + " }");
        return ResultResponse.fail(ex.getCode(), ex.getMessage());
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
        // 记录日志
        log.warn("[#" + ex.getCode() + "]{ " + ex.getMessage() + " }");
        return ResultResponse.fail(ex.getCode(), ex.getMessage());
    }

    /**
     * Http Code: 400(请求错误)
     * 1. 业务异常
     *
     * @param ex 异常信息
     * @return ResultResponse(code + message)
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            BusinessException.class, // 业务异常
    })
    public ResultResponse badRequestExceptionHandler(BusinessException ex) {
        // 记录日志
        log.warn("[#" + ex.getCode() + "]{ " + ex.getMessage() + " }");
        return ResultResponse.fail(ex.getCode(), ex.getMessage());
    }

    /**
     * Http Code: 500(服务器错误)
     * 1. 系统异常
     *
     * @param ex 异常信息
     * @return ResultResponse(code + message)
     */
    @ExceptionHandler({
            SystemException.class, // 系统异常
    })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultResponse systemExceptionHandler(SystemException ex) {
        // 记录日志
        log.error("[$" + ex.getCode() + "]{ " + ex.getMessage() + " }" + "\n==>" + getStackTrace(ex));
        return ResultResponse.fail(UNKNOWN_EXCEPTION);
    }

    /**
     * Http Code: 500(未知错误)
     *
     * @param ex 异常信息
     * @return ResultResponse(code + message)
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultResponse exceptionHandler(Exception ex) {
        // 记录日志
        log.error("[$" + UNKNOWN_EXCEPTION.getCode() + "]{ 捕获到未知错误！ }" + "\n==>" + getStackTrace(ex));
        return ResultResponse.fail(UNKNOWN_EXCEPTION);
    }

    /**
     * 获取完整堆栈信息
     *
     * @param throwable throwable
     * @return 完整堆栈信息字符串
     */
    private static String getStackTrace(Throwable throwable) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        try {
            throwable.printStackTrace(pw);
            return sw.toString();
        } finally {
            pw.close();
        }
    }
}
