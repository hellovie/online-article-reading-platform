package io.github.hellovie.file.domain.dto;

import io.github.hellovie.core.domain.BaseDTO;
import io.github.hellovie.file.domain.enums.FileStatus;
import io.github.hellovie.file.domain.enums.FileType;
import io.github.hellovie.file.domain.enums.StorageType;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * 上传文件 DTO.<br>
 *
 * @author hellovie <br>
 * @version 1.0.0 2023/5/23 <br>
 * @Email hellovie@foxmail.com <br>
 * @since JDK 1.8
 */
@Data
public class FileUploadDTO extends BaseDTO {
    /** 文件名 */
    private String name;

    /** 文件 hash 值 */
    private String fileKey;

    /** 文件存储的相对路径 */
    private String path;

    /** 文件后缀 */
    private String ext;

    /** 文件大小 (byte) */
    private Long size;

    /** 文件类型 */
    private FileType type;

    /** 存储供应商 */
    private StorageType storage;

    /** 文件状态 */
    private FileStatus status;

    /** 文件 */
    private MultipartFile file;
}
