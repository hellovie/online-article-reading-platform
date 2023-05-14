package io.github.hellovie.file.domain.entity;

import io.github.hellovie.core.domain.TraceableBaseEntity;
import io.github.hellovie.file.enums.FileStatus;
import io.github.hellovie.file.enums.FileType;
import io.github.hellovie.file.enums.StorageType;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * 文件实体. <br>
 *
 * @author hellovie <br>
 * @version 1.0.0 2023/5/14 <br>
 * @Email hellovie@foxmail.com <br>
 * @since JDK 1.8
 */
@Data
@Entity(name = "File")
public class File extends TraceableBaseEntity {
    /** 文件名 */
    private String name;

    /** 文件 hash 值 */
    @Column(name = "file_key")
    private String fileKey;

    /** 文件存储的相对路径 */
    private String path;

    /** 文件后缀 */
    private String ext;

    /** 文件大小 (byte) */
    private Integer size = 0;

    /** 文件类型 */
    @Enumerated(EnumType.STRING)
    private FileType type;

    /** 存储供应商 */
    @Enumerated(EnumType.STRING)
    private StorageType storage;

    /** 文件状态 */
    @Enumerated(EnumType.STRING)
    private FileStatus status = FileStatus.UPLOADING;
}
