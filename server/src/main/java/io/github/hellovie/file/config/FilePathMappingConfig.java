package io.github.hellovie.file.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 虚拟路径映射. <br>
 *
 * @author hellovie <br>
 * @version 1.0.0 2023/5/14 <br>
 * @Email hellovie@foxmail.com <br>
 * @since JDK 1.8
 */
@Configuration
public class FilePathMappingConfig  implements WebMvcConfigurer {
    public static final String FILE_ROOT = "/file-source";

    /**
     * 设置文件虚拟路径映射.
     *
     * @param registry ResourceHandlerRegistry.
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(FILE_ROOT + "/**").addResourceLocations("file:" + LocalUploadProperties.ROOT_PATH + "/");
    }
}
