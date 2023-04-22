package io.github.hellovie.user.repository;

import io.github.hellovie.user.domain.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Todo 描述一下该类吧
 *
 * @author hellovie
 * @Email hellovie@foxmail.com
 * @createTime 2023/4/22 12:16
 */
@Repository("roleRepository")
public interface RoleRepository extends JpaRepository<Role, String> {
}
