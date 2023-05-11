package com.mszlu.controller;

import com.mszlu.service.SysUserService;
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
@RequestMapping("users")
public class  UserController {
    @Autowired
    SysUserService sysUserService;
    @GetMapping("currentUser")
    //token在请求头中的Authorization中
    //根据token查询用户信息
    @ApiOperation(value = "根据Token获取用户信息")
    public Result currentUser(@RequestHeader("Authorization") String token){

        return sysUserService.findUserByToken(token);
    }
}
