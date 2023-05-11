package com.mszlu.controller;

import com.mszlu.service.LoginService;
import com.mszlu.vo.Result;
import com.mszlu.vo.params.LoginParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Api(tags="登录")
@RestController
@RequestMapping("login")
public class LoginController {
    @Autowired
    private LoginService loginService;
    @PostMapping
    @ApiOperation(value = "登录")
    public Result login(@RequestBody LoginParam loginParams){
        return loginService.login(loginParams);
    }
}
