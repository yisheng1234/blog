package com.mszlu.service.impl;

import com.alibaba.fastjson.JSON;
import com.mszlu.entity.SysUser;
import com.mszlu.service.RegisterService;
import com.mszlu.service.SysUserService;
import com.mszlu.utils.JWTUtils;
import com.mszlu.vo.ErrorCode;
import com.mszlu.vo.Result;
import com.mszlu.vo.params.RegisterParam;
import com.qiniu.util.StringUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Service
@Transactional  //事务功能，
public class RegisterServiceImpl implements RegisterService {
    @Autowired    //这是springboot和redis整合的内容
    private RedisTemplate<String,String> redisTemplate;
    //加密盐.登录和注册必须用相同的加密盐
    private static final String slat="yisheng!@#";
    @Autowired
    SysUserService sysUserService;
    @Override
    public Result register(RegisterParam registerParam) {
        /*
        * 1.判断参数是否合法
        * 2.判断账户是否已经存在
        * 3.如果不存在，注册
        * 4.生成token，存入redis，并返回token
        * 5.加上事务，一旦中间的任何过程出现问题，注册的用户 需要回滚
        * */
        String account=registerParam.getAccount();
        String nickname = registerParam.getNickname();
        String password = registerParam.getPassword();
        if(StringUtils.isNullOrEmpty(account) || StringUtils.isNullOrEmpty(nickname)
        || StringUtils.isNullOrEmpty(password)){
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());
        }
        SysUser sysUser=sysUserService.findSysUserByAccount(account);
        if(sysUser!=null){
            return Result.fail(ErrorCode.ACCOUNT_EXIST.getCode(), ErrorCode.ACCOUNT_EXIST.getMsg());
        }
        sysUser = new SysUser();
        sysUser.setNickname(nickname);
        sysUser.setAccount(account);
        sysUser.setPassword(DigestUtils.md5Hex(password+slat));
        sysUser.setCreateDate(System.currentTimeMillis());
        sysUser.setLastLogin(System.currentTimeMillis());
        sysUser.setAvatar("/static/img/logo.b3a48c0.png");
        sysUser.setAdmin(1);
        sysUser.setDeleted(0);
        sysUser.setSalt("");
        sysUser.setStatus("");
        sysUser.setEmail("");
        sysUserService.save(sysUser);
    //存入数据库后，为什么可以直接获得id
        System.out.println(sysUser.getId());

        String token = JWTUtils.createToken(sysUser.getId());
        //将用户信息存在redis中，key是token，value是用户信息 ，因为token是独一无二的，所以token独一无二的                                           //将对象转为string类型.       过期时间一天
        redisTemplate.opsForValue().set("TOKEN_"+token, JSON.toJSONString(sysUser),1, TimeUnit.DAYS);
        return Result.success(token);

    }
}
