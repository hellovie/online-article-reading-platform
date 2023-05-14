package io.github.hellovie.file.service;

import java.net.UnknownHostException;

/**
 * Todo 存储服务. <br>
 *
 * @author hellovie <br>
 * @version 1.0.0 2023/5/13 <br>
 * @Email hellovie@foxmail.com <br>
 * @since JDK 1.8
 */
public interface StorageService {
    /**
     * 获取根路径.
     *
     * @return 根路径.
     */
    String getRootPath() throws UnknownHostException;
}
