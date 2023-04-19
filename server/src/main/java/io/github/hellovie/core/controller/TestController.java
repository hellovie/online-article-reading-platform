package io.github.hellovie.core.controller;

import io.github.hellovie.exception.CommonExceptionType;
import io.github.hellovie.core.vo.ResultResponse;
import io.github.hellovie.exception.system.SystemException;
import io.github.hellovie.security.util.TokenUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

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

    @GetMapping("/login")
    public ResultResponse<String> login() {
        String token = TokenUtil.createToken("root");
        return ResultResponse.success(token);
    }

    @GetMapping("/super")
    @RolesAllowed("ROLE_SUPER_ADMIN")
    public ResultResponse<String> superAdmin() {
        return ResultResponse.success(null);
    }

    @GetMapping("/admin")
    @RolesAllowed("ROLE_ADMIN")
    public ResultResponse<String> admin() {
        return ResultResponse.success(null);
    }
}
