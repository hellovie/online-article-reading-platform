package io.github.hellovie.file.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 本地上传服务的数据配置. <br>
 *
 * @author hellovie <br>
 * @version 1.0.0 2023/5/14 <br>
 * @Email hellovie@foxmail.com <br>
 * @since JDK 1.8
 */
@Component
public class LocalUploadProperties implements InitializingBean {
    /** 本地存储根地址 */
    @Value("${hellovie.file.root-path}")
    private String rootPath;

    /** 头像存储地址 */
    @Value("${hellovie.file.avatar-path}")
    private String avatarPath;

    /** 默认文件存储地址 */
    @Value("${hellovie.file.default-path}")
    private String defaultPath;

    /** 文章封面存储地址 */
    @Value("${hellovie.file.cover-path}")
    private String coverPath;

    public static String ROOT_PATH;
    public static String AVATAR_PATH;
    public static String DEFAULT_PATH;
    public static String COVER_PATH;

    @Override
    public void afterPropertiesSet() throws Exception {
        ROOT_PATH = rootPath;
        AVATAR_PATH = rootPath + avatarPath;
        DEFAULT_PATH = rootPath + defaultPath;
        COVER_PATH = rootPath + coverPath;
    }
}
