package io.github.hellovie;

import io.github.hellovie.exception.CommonExceptionType;
import io.github.hellovie.exception.IExceptionType;
import lombok.AllArgsConstructor;

/**
 * 模块管理
 *
 * @author hellovie
 * @Email hellovie@foxmail.com
 * @createTime 2023/4/19 13:11
 */
@AllArgsConstructor
public enum ModuleManage {
    /** 通用模块 */
    COMMON_MODULE(10, CommonExceptionType.class),;

    /** 模块编号 */
    private int num;
    /** 模块异常枚举类 */
    private Class<? extends IExceptionType> typeClass;

    /**
     * 获取模块编号
     *
     * @return 模块编号
     */
    public final int num() {
        return this.num * 1000;
    }

    /**
     * 获取模块对应异常枚举类
     *
     * @return 异常枚举类
     */
    public final Class typeClass() {
        return this.typeClass;
    }
}
