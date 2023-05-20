package io.github.hellovie.user.mapper;

import io.github.hellovie.file.mapper.FileTypeConversionWorker;
import io.github.hellovie.user.domain.dto.UserDTO;
import io.github.hellovie.user.domain.entity.User;
import io.github.hellovie.user.domain.vo.LoginVO;
import io.github.hellovie.user.domain.vo.UserVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * 用户模块 Java Bean 映射器, mapstruct 实现. <br>
 *
 * @author hellovie <br>
 * @version 1.0.0 2023/4/20 <br>
 * @Email hellovie@foxmail.com <br>
 * @since JDK 1.8
 */
@Mapper(componentModel = "spring", uses = FileTypeConversionWorker.class)
public interface UserMapper {
    UserDTO toDTO(User entity);

    @Mapping(target = "avatar", source = "avatar", qualifiedByName = "toFullPath")
    LoginVO toLoginVO(UserDTO dto);

    @Mapping(target = "avatar", source = "avatar", qualifiedByName = "toFullPath")
    UserVO toVO(UserDTO dto);
}
