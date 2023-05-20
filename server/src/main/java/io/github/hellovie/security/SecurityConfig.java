package io.github.hellovie.security;

import io.github.hellovie.file.config.FilePathMappingConfig;
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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * spring security 配置类. <br>
 *
 * @author hellovie <br>
 * @version 1.0.0 2023/4/19 <br>
 * @Email hellovie@foxmail.com <br>
 * @since JDK 1.8
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {
    @Resource(name = "userServiceImpl")
    private UserDetailsService userDetailsService;

    /** 放行接口 */
    private static final String[] ALLOWED_INTERFACES = {
            "/users/login", "/users/register",
            // swagger 放行
            "/swagger-ui.html", "/swagger-ui/*", "/swagger-resources/**", "/v2/api-docs", "/v3/api-docs", "/webjars/**",
            // 资源路径
            FilePathMappingConfig.FILE_ROOT + "/**",
            // 查询文章放行
            "/articles/views/**"
    };

    /** JWT 加密密钥 */
    public static final String SECRET = "HellovieSecretKey";

    /** JWT 有效时长 10 天 */
    public static final long EXPIRATION_TIME = 864000000;

    /** JWT token 前缀 */
    public static final String TOKEN_PREFIX = "Bearer ";

    /** JWT Header key */
    public static final String HEADER_KEY = "Authorization";

    /** 匿名用户访问无权限资源时的异常 */
    @Resource(name = "userAuthenticationEntryPoint")
    private UserAuthenticationEntryPoint authenticationEntryPoint;

    /**
     * DelegatingPasswordEncoder 可拓展的加密方式.
     * <p>默认bcrypt (采用 SHA-256 + 随机盐 + 密钥对密码进行加密).</p>
     *
     * @return BCryptPasswordEncoder.
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
     * 获取 AuthenticationManager (认证管理器), 登录时认证使用.
     *
     * @param authenticationConfiguration AuthenticationConfiguration.
     * @return AuthenticationManager.
     * @throws Exception
     */
    @Bean(name = "authenticationManagerBean")
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * spring-security 配置.
     *
     * @param http HttpSecurity.
     * @return SecurityFilterChain.
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authenticationManagerBean) throws Exception {
        return http
                // 基于 token, 不需要 csrf.
                .csrf().disable()
                // 开启跨域以便前端调用接口.
                .cors().and().authorizeRequests()
                // 放行接口.
                .antMatchers(ALLOWED_INTERFACES).permitAll()
                // 其它所有接口需要认证才能访问.
                .anyRequest().authenticated().and()
                // 认证拦截器.
                .addFilter(new JwtAuthorizationFilter(authenticationManagerBean, userDetailsService))
                // 配置异常处理.
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint).and()
                // 基于 token, 不需要 session.
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().build();
    }

    /**
     * 跨域配置.
     *
     * @return org.springframework.web.cors.CorsConfigurationSource.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.setAllowedOriginPatterns(Arrays.asList("*"));
        // configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setMaxAge(Duration.ofHours(1));
        source.registerCorsConfiguration("/**",configuration);
        return source;
    }
}
