package io.github.hellovie.exception.handler;

import io.github.hellovie.core.util.ResultResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
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

import java.net.UnknownHostException;
import java.util.List;

import static io.github.hellovie.exception.CommonExceptionType.*;

/**
 * 全局额外异常捕获处理，最后再进行处理。
 * 额外异常：非自己定义的第三方异常
 *
 * @author hellovie
 * @Email hellovie@foxmail.com
 * @createTime 2023/4/23 17:01
 */
@Order(1)
@RestControllerAdvice
@Slf4j
public class ExtraExceptionHandler {
    /**
     * Http Code: 403(无权限访问！)
     * 无权限访问异常 spring security授权异常处理
     *
     * @param ex 异常信息
     * @return ResultResponse(code + message)
     */
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResultResponse accessDeniedHandler(Exception ex) {
        return ResultResponse.fail(HttpStatus.FORBIDDEN.value(), "无权限访问！");
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
}
