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

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Set;

import static io.github.hellovie.exception.CommonExceptionType.*;

/**
 * 全局额外异常捕获处理, 最先开始处理. <br>
 * 额外异常: 非自己定义的第三方异常. <br>
 *
 * @author hellovie <br>
 * @version 1.0.0 2023/4/23 <br>
 * @Email hellovie@foxmail.com <br>
 * @since JDK 1.8
 */
@Order(1)
@RestControllerAdvice
@Slf4j
public class ExtraExceptionHandler {
    /**
     * Http Code: 403(无权限访问.)
     * <p>无权限访问异常 spring security 授权异常处理.</p>
     *
     * @param ex 异常信息.
     * @return ResultResponse (code + message).
     */
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResultResponse accessDeniedHandler(Exception ex) {
        return ResultResponse.fail(HttpStatus.FORBIDDEN.value(), "无权限访问！");
    }

    /**
     * Http Code: 400(请求错误.)
     * <p>1. 请求数据参数不符合要求.</p>
     * <p>2. 请求方法有误.</p>
     * <p>3. 未知 Host 异常.</p>
     *
     * @param ex 异常信息.
     * @return ResultResponse (code + message).
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            HttpMessageNotReadableException.class, // HTTP 消息不可读异常
            HttpRequestMethodNotSupportedException.class, // HTTP 请求方法不支持异常
            UnknownHostException.class // 未知 Host 异常
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
     * Http Code: 400(请求错误.)
     * <p>请求数据校验失败.</p>
     *
     * @param ex 异常信息.
     * @return ResultResponse (code + message).
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            MethodArgumentNotValidException.class, // 转换对象类 Request 的校验失败结果
            ConstraintViolationException.class // 转换单一属性 Request 的校验失败结果
    })
    public ResultResponse validationExceptionHandler(Exception ex) {
        if (ex instanceof ConstraintViolationException) {
            ConstraintViolationException e = (ConstraintViolationException) ex;
            // 判断异常中是否有错误信息, 如果存在就使用异常中的消息, 否则使用默认消息.
            if (!e.getConstraintViolations().isEmpty()) {
                // 这里列出了全部错误参数, 按正常逻辑, 只需要第一条错误即可.
                String message = "";
                for (ConstraintViolation<?> constraintViolation : e.getConstraintViolations()) {
                    message = constraintViolation.getMessage();
                    if (message != null && !"".equals(message)) {
                        return ResultResponse.fail(VALIDATION_FAILED.getCode(), message);
                    }
                }
            }
        } else if (ex instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException e = (MethodArgumentNotValidException) ex;
            BindingResult exceptions = e.getBindingResult();
            // 判断异常中是否有错误信息, 如果存在就使用异常中的消息, 否则使用默认消息.
            if (exceptions.hasErrors()) {
                List<ObjectError> errors = exceptions.getAllErrors();
                if (!errors.isEmpty()) {
                    // 这里列出了全部错误参数, 按正常逻辑, 只需要第一条错误即可.
                    FieldError fieldError = (FieldError) errors.get(0);
                    return ResultResponse.fail(VALIDATION_FAILED.getCode(), fieldError.getDefaultMessage());
                }
            }
        }
        return ResultResponse.fail(VALIDATION_FAILED);
    }
}
