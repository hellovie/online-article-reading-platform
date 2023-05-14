package io.github.hellovie.file.domain.vo;

import io.github.hellovie.core.domain.BaseVO;
import io.github.hellovie.file.enums.FileStatus;
import io.github.hellovie.file.enums.FileType;
import io.github.hellovie.file.enums.StorageType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * 包含资源路径的文件 VO. <br>
 *
 * @author hellovie <br>
 * @version 1.0.0 2023/5/14 <br>
 * @Email hellovie@foxmail.com <br>
 * @since JDK 1.8
 */
@ApiModel("文件信息视图")
@Data
public class FileVO extends BaseVO {
    /** 文件名 */
    @ApiModelProperty(value = "文件名")
    private String name;

    /** 文件 hash 值 */
    @ApiModelProperty(value = "文件 hash 值")
    private String fileKey;

    /** 资源路径 */
    @ApiModelProperty(value = "资源路径")
    private String url;

    /** 文件大小 (byte) */
    @ApiModelProperty(value = "文件大小 (byte)")
    private Integer size;

    /** 文件类型 */
    @ApiModelProperty(value = "文件类型")
    private FileType type;

    /** 文件状态 */
    @ApiModelProperty(value = "文件状态")
    private FileStatus status;
}
