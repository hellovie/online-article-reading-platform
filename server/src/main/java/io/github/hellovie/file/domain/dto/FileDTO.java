package io.github.hellovie.file.domain.dto;

import io.github.hellovie.core.domain.BaseDTO;
import io.github.hellovie.file.enums.FileStatus;
import io.github.hellovie.file.enums.FileType;
import io.github.hellovie.file.enums.StorageType;
import lombok.Data;

/**
 * 去除敏感数据的文件 DTO. <br>
 *
 * @author hellovie <br>
 * @version 1.0.0 2023/5/14 <br>
 * @Email hellovie@foxmail.com <br>
 * @since JDK 1.8
 */
@Data
public class FileDTO extends BaseDTO {
    /** 文件名 */
    private String name;

    /** 文件 hash 值 */
    private String fileKey;

    /** 文件存储的相对路径 */
    private String path;

    /** 文件后缀 */
    private String ext;

    /** 文件大小 (byte) */
    private Integer size;

    /** 文件类型 */
    private FileType type;

    /** 存储供应商 */
    private StorageType storage;

    /** 文件状态 */
    private FileStatus status;

    /**
     * 获取完整资源路径
     *
     * @param rootPath 根路径
     * @return 拼接路径的值缺失则返回 null.
     */
    public String getFullPath(String rootPath) {
        if (fileKey != null && path != null && ext != null && !fileKey.isEmpty() && !path.isEmpty() && !ext.isEmpty()) {
            return rootPath + path + "/" + fileKey + '.' + ext;
        }
        return null;
    }
}
