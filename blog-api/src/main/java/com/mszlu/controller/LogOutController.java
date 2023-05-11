package com.mszlu.controller;

import com.mszlu.service.LoginService;
import com.mszlu.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Api(tags="登录")
@RestController
@RequestMapping("logout")
public class LogOutController {
    @Autowired
    LoginService loginService;
    @GetMapping
    @ApiOperation(value = "退出登录")
    public Result logout(@RequestHeader("Authorization") String token){
        /*
        * 退出登录，token信息是没办法清除的，只能在前台清除，但是可以清除redis中的信息
        * */
        return loginService.logout(token);
    }
}
