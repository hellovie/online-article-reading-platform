package io.github.hellovie.core.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 通用校验器，对用户注入的校验器进行统一处理。
 *
 * @author hellovie
 * @Email hellovie@foxmail.com
 * @createTime 2023/4/23 10:22
 */
public class EnumValidator implements ConstraintValidator<EnumValid, Object> {
    /** 枚举类(枚举枚举类变量名) */
    private Class<? extends Enum> enumClass;

    /** 枚举值(枚举的具体值，可以是自定义的字符串数组) */
    private String[] enumValue;

    @Override
    public void initialize(EnumValid constraintAnnotation) {
        enumClass = constraintAnnotation.enumClass();
        enumValue = constraintAnnotation.enumValue();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext context) {
        if (o == null) {
            return false;
        }

        String value = (String) o;

        Enum[] enums = enumClass.getEnumConstants();
        if (enums != null && enums.length != 0) {
            // 校验枚举变量名
            for (Enum e : enums) {
                if (e.name().equals(value)) {
                    return true;
                }
            }
        }

        // 不是空字符串数组
        if (enumValue != null && enumValue.length != 0) {
            for (String s : enumValue) {
                if (s.equals(value)) {
                    return true;
                }
            }
        }

        return false;
    }
}
