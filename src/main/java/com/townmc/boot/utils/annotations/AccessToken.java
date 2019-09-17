package com.townmc.boot.utils.annotations;

import com.townmc.boot.enums.Handler;
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
    Handler value() default Handler.DEFAULT;

    /**
     * Token的Handler名称
     * @return
     */
    @AliasFor("value")
    Handler handler() default Handler.DEFAULT;

    /**
     * 通过tag来传递一些信息用于自己实现特殊权限控制逻辑
     * @return
     */
    String[] tags() default "";
}
