package io.github.hellovie.file.repository;

import io.github.hellovie.file.domain.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 文件持久层. <br>
 *
 * @author hellovie <br>
 * @version 1.0.0 2023/5/23 <br>
 * @Email hellovie@foxmail.com <br>
 * @since JDK 1.8
 */

@Repository("fileRepository")
public interface FileRepository extends JpaRepository<File, String> {
}
