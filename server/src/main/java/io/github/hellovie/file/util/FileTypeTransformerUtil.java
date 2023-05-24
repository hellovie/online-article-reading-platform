package io.github.hellovie.file.util;

import io.github.hellovie.exception.business.FileUploadException;
import io.github.hellovie.file.config.UploadFileConfig;
import io.github.hellovie.file.domain.enums.FileType;

import static io.github.hellovie.file.domain.enums.FileExceptionType.UNSUPPORTED_FILE_FORMATS;

/**
 * 文件后缀和 FileType 之间转换. <br>
 *
 * @author hellovie <br>
 * @version 1.0.0 2023/5/23 <br>
 * @Email hellovie@foxmail.com <br>
 * @since JDK 1.8
 */
public class FileTypeTransformerUtil {
    private FileTypeTransformerUtil() {}
    /**
     * 文件后缀转换为FileType
     *
     * @param ext 文件后缀
     * @return File Type
     */
    public static final FileType extToFileType(String ext) {
        if (isAudio(ext)) {
            return FileType.AUDIO;
        }

        if (isImage(ext)) {
            return FileType.IMAGE;
        }

        if (isVideo(ext)) {
            return FileType.VIDEO;
        }
        throw new FileUploadException(UNSUPPORTED_FILE_FORMATS);
        // return FileType.OTHER;
    }

    /**
     * 根据后缀判断是否为视频
     *
     * @param ext 文件后缀
     * @return 是-true
     */
    private static Boolean isVideo(String ext) {
        for (String videoExt : UploadFileConfig.ALLOWED_VIDEO_EXT) {
            if (videoExt.equals(ext)) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    /**
     * 根据后缀判断是否为图片
     *
     * @param ext 文件后缀
     * @return 是 - true
     */
    private static Boolean isImage(String ext) {
        for (String imageExt : UploadFileConfig.ALLOWED_IMAGE_EXT) {
            if (imageExt.equals(ext)) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    /**
     * 根据后缀判断是否为音频
     *
     * @param ext 文件后缀
     * @return 是 - true
     */
    private static Boolean isAudio(String ext) {
        for (String audioExt : UploadFileConfig.ALLOWED_AUDIO_EXT) {
            if (audioExt.equals(ext)) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }
}
