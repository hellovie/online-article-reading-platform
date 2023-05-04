package io.github.hellovie.core.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 基于 spring-boot-starter-validation 的枚举校验注解. <br>
 *
 * @author hellovie <br>
 * @version 1.0.0 2023/4/23 <br>
 * @Email hellovie@foxmail.com <br>
 * @since JDK 1.8
 */
@Constraint(validatedBy = EnumValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EnumValid {
    String message() default "{io.github.hellovie.core.validation.EnumValid.message}";

    /** 枚举类 (枚举枚举类变量名) */
    Class<? extends Enum<?>> enumClass();

    /** 枚举值 (枚举的具体值, 可以是自定义的字符串数组) */
    String[] enumValue() default {};

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
