package com.townmc.boot.utils.annotations;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 访问token
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AccessToken {
    @AliasFor("handler")
    String value() default "";

    /**
     * Token的Handler名称
     * @return
     */
    @AliasFor("value")
    String handler() default "";

    /**
     * 通过tag来传递一些信息用于自己实现特殊权限控制逻辑
     * @return
     */
    String[] tags() default "";
}
