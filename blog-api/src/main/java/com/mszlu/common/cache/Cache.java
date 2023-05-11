package com.mszlu.common.cache;


import java.lang.annotation.*;
/*统一缓存处理
*
* */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Cache {
    //缓存过期时间
    long expire() default 1 * 60 * 1000;
    //缓存标识 key
    String name() default "";

}