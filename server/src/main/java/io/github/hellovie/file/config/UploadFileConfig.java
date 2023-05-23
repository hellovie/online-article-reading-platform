package io.github.hellovie.file.config;

/**
 * 上传文件配置. <br>
 *
 * @author hellovie <br>
 * @version 1.0.0 2023/5/23 <br>
 * @Email hellovie@foxmail.com <br>
 * @since JDK 1.8
 */
public class UploadFileConfig {
    /** 允许上传的视频后缀 */
    public static final String[] ALLOWED_VIDEO_EXT= {"vob", "mp4", "avi",
                                                     "flv", "f4v", "wmv", "mov", "rmvb",
                                                     "mkv", "mpg", "mv4", "webm", "rm",
                                                     "mpeg", "asf", "ts", "mts"};

    /** 允许上传的音频后缀 */
    public static final String[] ALLOWED_AUDIO_EXT= {"mp3", "wav", "m4a"};

    /** 允许上传的图片后缀 */
    public static final String[] ALLOWED_IMAGE_EXT= {"png", "jpg", "jpeg"};
}
