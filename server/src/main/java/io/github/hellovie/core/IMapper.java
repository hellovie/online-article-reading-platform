package io.github.hellovie.core;

import io.github.hellovie.core.domain.BaseDTO;
import io.github.hellovie.core.domain.BaseEntity;
import io.github.hellovie.core.domain.BaseRequest;
import io.github.hellovie.core.domain.BaseVO;

/**
 * Entity、Request、DTO、VO之间的转换
 *
 * @author hellovie
 * @Email hellovie@foxmail.com
 * @createTime 2023/4/20 17:41
 */
public interface IMapper {
    /**
     * DTO to Entity
     *
     * @param dto DTO
     * @return Entity
     */
    BaseEntity toEntity(BaseDTO dto);

    /**
     * Entity to DTO
     *
     * @param entity Entity
     * @return DTO
     */
    BaseDTO toDto(BaseEntity entity);

    /**
     * Request to DTO
     *
     * @param request Request
     * @return DTO
     */
    BaseDTO toDto(BaseRequest request);

    /**
     * DTO to VO
     *
     * @param dto DTO
     * @return VO
     */
    BaseVO toVO(BaseDTO dto);
}
