package io.github.hellovie;

import io.github.hellovie.article.domain.enums.ArticleExceptionType;
import io.github.hellovie.exception.CommonExceptionType;
import io.github.hellovie.exception.IExceptionType;
import io.github.hellovie.file.enums.FileExceptionType;
import io.github.hellovie.user.domain.enums.UserExceptionType;

/**
 * 模块管理. <br>
 *
 * @author hellovie <br>
 * @version 1.0.0 2023/4/19 <br>
 * @Email hellovie@foxmail.com <br>
 * @since JDK 1.8
 */
public enum ModuleManage {
    /** 通用模块 */
    COMMON_MODULE(10, CommonExceptionType.class),
    /** 用户模块 */
    USER_MODULE(11, UserExceptionType.class),
    /** 文件模块 */
    FILE_MODULE(12, FileExceptionType.class),
    /** 文章模块 */
    ARTICLE_MODULE(13, ArticleExceptionType.class),

    ;

    /** 模块编号 */
    private int num;
    /** 模块异常枚举类 */
    private Class<? extends IExceptionType> typeClass;

    ModuleManage(int num, Class<? extends IExceptionType> typeClass) {
        this.num = num;
        this.typeClass = typeClass;
    }

    /**
     * 获取模块编号.
     *
     * @return 模块编号.
     */
    public final int num() {
        return this.num * 1000;
    }

    /**
     * 获取模块对应异常枚举类.
     *
     * @return 异常枚举类.
     */
    public final Class typeClass() {
        return this.typeClass;
    }
}
