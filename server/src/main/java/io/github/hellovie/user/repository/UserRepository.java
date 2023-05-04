package io.github.hellovie.user.repository;

import io.github.hellovie.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 用户持久层. <br>
 *
 * @author hellovie <br>
 * @version 1.0.0 2023/4/20 <br>
 * @Email hellovie@foxmail.com <br>
 * @since JDK 1.8
 */
@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, String> {
    /**
     * 根据用户名获取用户信息.
     *
     * @param username username.
     * @return Optional.
     */
    Optional<User> findByUsername(String username);
}

