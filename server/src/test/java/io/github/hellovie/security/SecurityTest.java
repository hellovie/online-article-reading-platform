package io.github.hellovie.security;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 测试安全模块
 *
 * @author hellovie
 * @Email hellovie@foxmail.com
 * @createTime 2023/4/19 16:38
 */
@SpringBootTest
public class SecurityTest {
    @Test
    public void testPasswordEncoder() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String text = "hellovie";
        String encode = passwordEncoder.encode(text);
        Assertions.assertEquals(true, passwordEncoder.matches(text, encode));
    }

    @Test
    public void test() {

    }
}
