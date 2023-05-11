package com.mszlu.controller;

import com.mszlu.entity.SysUser;
import com.mszlu.utils.UserThreadLocal;
import com.mszlu.vo.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class TestController {
/*
*
* 用于测试
* */
    @RequestMapping
    public Result test(){
        SysUser sysUser= UserThreadLocal.get();
        System.out.println(sysUser);
        return Result.success(null);
    }
}