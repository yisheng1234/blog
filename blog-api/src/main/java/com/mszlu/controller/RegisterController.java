package com.mszlu.controller;

import com.mszlu.service.RegisterService;
import com.mszlu.vo.Result;
import com.mszlu.vo.params.RegisterParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Api(tags="登录")
@RestController
@RequestMapping("register")
public class RegisterController {
    @Autowired
    RegisterService registerService;
    @PostMapping
    @ApiOperation(value = "注册")
    public Result register(@RequestBody RegisterParam registerParam){
        return registerService.register(registerParam);
    }
}
