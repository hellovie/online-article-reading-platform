package io.github.hellovie.security.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import io.github.hellovie.security.SecurityConfig;

import java.util.Date;

/**
 * JWT 工具类. <br>
 *
 * @author hellovie <br>
 * @version 1.0.0 2023/4/19 <br>
 * @Email hellovie@foxmail.com <br>
 * @since JDK 1.8
 */
public class TokenUtil {
    private TokenUtil() { }

    /**
     * 生成 token 字符串, 将账号放入 JWT 载荷中.
     *
     * @param username 账号.
     * @return token 字符串.
     * @throws JWTCreationException token 创建失败.
     */
    public static String createToken(String username) throws JWTCreationException {
        String token = "";
        JWTCreator.Builder builder = JWT.create().withSubject(username).withExpiresAt(new Date(System.currentTimeMillis() + SecurityConfig.EXPIRATION_TIME));
        // 调用者需要 catch JWTCreationException
        token = builder.sign(Algorithm.HMAC512(SecurityConfig.SECRET.getBytes()));

        return token;
    }
}
