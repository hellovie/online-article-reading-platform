package io.github.hellovie.security.exception;

import cn.hutool.json.JSONUtil;
import io.github.hellovie.core.util.ResultResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 处理匿名用户访问无权限资源时的异常
 * spring security自带认证异常处理，在认证过程中所有抛出的异常将会被它处理，最后统一返回信息。
 * 如果使用springboot的全局异常处理的话，无法预知在认证过程中可能发生的所有异常，就无法做到对于认证失败后，统一返回。
 *
 * @author hellovie
 * @Email hellovie@foxmail.com
 * @createTime 2023/4/19 15:06
 */
@Component("userAuthenticationEntryPoint")
public class UserAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        // Http Code: 401
        ResultResponse<Object> fail = ResultResponse.fail(HttpStatus.UNAUTHORIZED.value(), "用户未登录！");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().println(JSONUtil.parse(fail));
        response.getWriter().flush();
    }
}
