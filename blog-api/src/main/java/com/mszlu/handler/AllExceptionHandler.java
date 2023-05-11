package com.mszlu.handler;

import com.mszlu.vo.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


//对加了@Controller注解的方法进行拦截处理， 属于aop的实现,
// 如果是意料之内的异常，可以直接捕获处理，
// 如果出了意料之外的异常（代码中并没有处理），这个拦截器可以拦截，并返回给用户相对友好的信息
@ControllerAdvice
public class

AllExceptionHandler {
    /*
     * 统一异常处理
     * */
    @ExceptionHandler(Exception.class)
    @ResponseBody

    public Result doException(Exception ex){
        ex.printStackTrace();
        return Result.fail(-999,"系统异常，正在加紧维修");
    }
}
