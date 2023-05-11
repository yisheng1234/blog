package com.mszlu.service;

import com.mszlu.entity.SysUser;
import com.mszlu.vo.Result;
import com.mszlu.vo.UserVo;

public interface SysUserService {
    SysUser findUser(String account, String password);

    SysUser findSysUserById(Long id);
    /*
    * 根据token查询用户信息
    * */
    Result findUserByToken(String token);

    SysUser findSysUserByAccount(String account);

    void save(SysUser sysUser);

    UserVo findUserVoById(Long id);
}
