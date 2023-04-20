package io.github.hellovie.security;

import io.github.hellovie.security.exception.UserAuthenticationEntryPoint;
import io.github.hellovie.security.filter.JwtAuthorizationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * spring security配置类
 *
 * @author hellovie
 * @Email hellovie@foxmail.com
 * @createTime 2023/4/19 14:03
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true
)
public class SecurityConfig {
    @Resource(name="userServiceImpl")
    private UserDetailsService userDetailsService;

    /** 放行接口 */
    private static final String[] ALLOWED_INTERFACES = {"/users/login", "/users/register"};

    /** JWT加密密钥 */
    public static final String SECRET = "HellovieSecretKey";

    /** JWT有效时长10天 */
    public static final long EXPIRATION_TIME = 864000000;

    /** JWT token前缀 */
    public static final String TOKEN_PREFIX = "Bearer ";

    /** JWT Header key */
    public static final String HEADER_KEY = "Authorization";

    /** 匿名用户访问无权限资源时的异常 */
    @Resource(name = "userAuthenticationEntryPoint")
    private UserAuthenticationEntryPoint authenticationEntryPoint;

    /**
     * DelegatingPasswordEncoder可拓展的加密方式。
     * 默认bcrypt(采用SHA-256 + 随机盐 + 密钥对密码进行加密)
     *
     * @return BCryptPasswordEncoder
     */
    @Bean(name = "passwordEncoder")
    public static PasswordEncoder passwordEncoder() {
        String idForEncode = "bcrypt";
        Map encoders = new HashMap<>(1);
        encoders.put(idForEncode, new BCryptPasswordEncoder(16));
        PasswordEncoder passwordEncoder = new DelegatingPasswordEncoder(idForEncode, encoders);
        return passwordEncoder;
    }

    /**
     * 获取AuthenticationManager(认证管理器)，登录时认证使用。
     *
     * @param authenticationConfiguration
     * @return AuthenticationManager
     * @throws Exception
     */
    @Bean(name = "authenticationManagerBean")
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * spring-security配置
     *
     * @param http
     * @return
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authenticationManagerBean) throws Exception {
        return http
                // 基于token，不需要csrf
                .csrf().disable()
                // 开启跨域以便前端调用接口
                .cors().and().authorizeRequests()
                // 放行接口
                .antMatchers(ALLOWED_INTERFACES).permitAll()
                // 其它所有接口需要认证才能访问
                .anyRequest().authenticated().and()
                // 认证拦截器
                .addFilter(new JwtAuthorizationFilter(authenticationManagerBean, userDetailsService))
                // 配置异常处理
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .and()
                // 基于token，不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .build();
    }
}
