package io.github.hellovie.user.mapper;

import io.github.hellovie.user.domain.dto.LoginDTO;
import io.github.hellovie.user.domain.dto.UserDTO;
import io.github.hellovie.user.domain.entity.User;
import io.github.hellovie.user.domain.vo.LoginVO;
import io.github.hellovie.user.domain.vo.UserVO;
import org.mapstruct.Mapper;

/**
 * 用户模块Java Bean映射器
 *
 * @author hellovie
 * @Email hellovie@foxmail.com
 * @createTime 2023/4/20 20:02
 */
@Mapper(componentModel = "spring")
public interface UserMapper {
    public LoginDTO toDto(User entity);

    public LoginVO toVO(LoginDTO dto);

    public UserVO toVO(UserDTO dto);

    public UserDTO toEntity(User user);
}
