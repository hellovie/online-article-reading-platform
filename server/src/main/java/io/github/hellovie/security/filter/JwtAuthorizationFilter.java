package io.github.hellovie.security.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.github.hellovie.security.CustomUser;
import io.github.hellovie.security.SecurityConfig;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Java Web Token 认证. <br>
 *
 * @author hellovie <br>
 * @version 1.0.0 2023/4/19 <br>
 * @Email hellovie@foxmail.com <br>
 * @since JDK 1.8
 */
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private UserDetailsService userDetailsService;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserDetailsService userDetailsService) {
        super(authenticationManager);
        this.userDetailsService = userDetailsService;
    }

    /**
     * 具体拦截逻辑.
     *
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = request.getHeader(SecurityConfig.HEADER_KEY);
        // 请求头无token，直接跳出拦截器
        if (token == null || !token.startsWith(SecurityConfig.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authenticationToken = getAuthentication(token);
        // 将认证信息存储在SecurityContextHolder中
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request, response);
    }

    /**
     * JWT 验证, 验证成功返回认证信息, 认证失败返回 null.
     *
     * @param token token.
     * @return 认证信息.
     */
    private UsernamePasswordAuthenticationToken getAuthentication(String token) {
        if (token != null) {
            String username = null;
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC512(SecurityConfig.SECRET.getBytes())).build();
            try {
                DecodedJWT decodedJWT = jwtVerifier.verify(token.replace(SecurityConfig.TOKEN_PREFIX, ""));
                username = decodedJWT.getSubject();
            } catch (JWTVerificationException ex) {
                throw new JWTVerificationException("JWT验证异常！");
            }

            // JWT验证成功，封装用户名密码认证信息
            if (username != null && userDetailsService != null) {
                CustomUser user = (CustomUser) userDetailsService.loadUserByUsername(username);
                // 暂无Credentials
                return new UsernamePasswordAuthenticationToken(username, null, user.getAuthorities());
            }
        }
        return null;
    }
}
