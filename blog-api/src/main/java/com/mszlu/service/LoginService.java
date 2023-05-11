package com.mszlu.service;

import com.mszlu.entity.SysUser;
import com.mszlu.vo.Result;
import com.mszlu.vo.params.LoginParam;

public interface LoginService {
    /*
    * 登录功能
    * */
    Result login(LoginParam loginParams);

    SysUser checkToken(String token);
    /*
    * 退出登录
    * */
    Result logout(String token);
}
