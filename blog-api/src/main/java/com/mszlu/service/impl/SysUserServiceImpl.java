package com.mszlu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mszlu.dao.mapper.SysUserMapper;
import com.mszlu.entity.SysUser;
import com.mszlu.service.LoginService;
import com.mszlu.service.SysUserService;
import com.mszlu.vo.ErrorCode;
import com.mszlu.vo.LoginUserVo;
import com.mszlu.vo.Result;
import com.mszlu.vo.UserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    SysUserMapper sysUserMapper;
    @Autowired
    LoginService loginService;

    @Override
    public SysUser findSysUserById(Long id) {
        SysUser sysUser = sysUserMapper.selectById(id);

        return sysUser;
    }



    @Override
    public SysUser findUser(String account, String password) {
        LambdaQueryWrapper<SysUser> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SysUser::getAccount,account);
        lambdaQueryWrapper.eq(SysUser::getPassword,password);
        //账号，id，头像，名称
        lambdaQueryWrapper.select(SysUser::getAccount,SysUser::getId,SysUser::getAvatar,SysUser::getNickname);
        lambdaQueryWrapper.last("limit 1");

        return sysUserMapper.selectOne(lambdaQueryWrapper);
    }

    @Override
    public Result findUserByToken(String token) {
        /*
        *1.检验token是否合法
        *   是否为空，解析是否成功 redis是否存在
        * 2.校验失败，返回错误
        * 3.如果成功，返回loginUserVo
        * */
        SysUser sysUser=loginService.checkToken(token);
        if(sysUser==null){
            return Result.fail(ErrorCode.TOKEN_ERROR.getCode(), ErrorCode.TOKEN_ERROR.getMsg());
        }
        LoginUserVo loginUserVo = new LoginUserVo();
        loginUserVo.setId(String.valueOf(sysUser.getId()));
        loginUserVo.setAccount(sysUser.getAccount());
        loginUserVo.setAvatar(sysUser.getAvatar());
        loginUserVo.setNickname(sysUser.getNickname());
        return Result.success(loginUserVo);
    }

    @Override
    public SysUser findSysUserByAccount(String account) {
        LambdaQueryWrapper<SysUser> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SysUser::getAccount,account);
        lambdaQueryWrapper.last("limit 1");
        SysUser sysUser = sysUserMapper.selectOne(lambdaQueryWrapper);
        return sysUser;
    }

    @Override
    public void save(SysUser sysUser) {
        /*mybatisplus提供的insert 生成的id是分布式id，雪花算法
        * 我们就用这种
        * */
       sysUserMapper.insert(sysUser);
    }

    @Override
    public UserVo findUserVoById(Long id) {
        SysUser sysUser = sysUserMapper.selectById(id);

        UserVo userVo=new UserVo();
        BeanUtils.copyProperties(sysUser,userVo);
        userVo.setId(String.valueOf(sysUser.getId()));
        return userVo;
    }

}
