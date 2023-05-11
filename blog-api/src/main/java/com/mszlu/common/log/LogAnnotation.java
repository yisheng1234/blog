package com.mszlu.common.log;

import java.lang.annotation.*;

//Type  代表此注解可以放在类上面 METHOD 代表此注解可以放在方法上。,也可以同时加
@Target({ElementType.METHOD})
//RetentionPolicy.RUNTIME：注解不仅被保存到class文件中，jvm加载class文件之后，仍然存在；
@Retention(RetentionPolicy.RUNTIME)

@Documented
public @interface LogAnnotation {
    /*
    * 注解开发时上面三个注解都要加上。
    * 定义一个 输出日志注解
    * */

    //定义参数
    String module() default "";
    String operator() default "";
}
