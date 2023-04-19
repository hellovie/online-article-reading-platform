package io.github.hellovie.security.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import io.github.hellovie.security.SecurityConfig;

import java.util.Date;

/**
 * JWT工具类
 *
 * @author hellovie
 * @Email hellovie@foxmail.com
 * @createTime 2023/4/19 18:05
 */
public class TokenUtil {
    private TokenUtil() {}

    /**
     * 生成token字符串，将账号放入JWT载荷中。
     *
     * @param username 账号
     * @return token字符串
     * @throws JWTCreationException token创建失败
     */
    public static String createToken(String username) throws JWTCreationException {
        String token = "";
        JWTCreator.Builder builder = JWT.create().withSubject(username).withExpiresAt(new Date(System.currentTimeMillis() + SecurityConfig.EXPIRATION_TIME));
        // 调用者需要catch JWTCreationException
        token = builder.sign(Algorithm.HMAC512(SecurityConfig.SECRET.getBytes()));

        return token;
    }
}
