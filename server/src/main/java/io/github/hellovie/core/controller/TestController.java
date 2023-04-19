package io.github.hellovie.core.controller;

import io.github.hellovie.exception.CommonExceptionType;
import io.github.hellovie.exception.ResultResponse;
import io.github.hellovie.exception.business.BusinessException;
import io.github.hellovie.exception.system.SystemException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试搭建环境
 *
 * @author hellovie
 * @Email hellovie@foxmail.com
 * @createTime 2023/4/18 22:22
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping
    public String test() {
        return "Hello world!";
    }

    @GetMapping("/success")
    public ResultResponse<String> success() {
        return ResultResponse.success(null);
    }

    @GetMapping("/fail")
    public ResultResponse<String> fail() {
        throw new SystemException(CommonExceptionType.UNKNOWN_EXCEPTION);
    }
}
