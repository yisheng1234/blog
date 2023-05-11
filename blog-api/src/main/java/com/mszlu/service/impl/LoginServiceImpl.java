package com.mszlu.service.impl;

import com.alibaba.fastjson.JSON;
import com.mszlu.entity.SysUser;
import com.mszlu.service.LoginService;
import com.mszlu.service.SysUserService;
import com.mszlu.utils.JWTUtils;
import com.mszlu.vo.ErrorCode;
import com.mszlu.vo.Result;
import com.mszlu.vo.params.LoginParam;
import com.qiniu.util.StringUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private SysUserService sysUserService;
    @Autowired    //这是springboot和redis整合的内容
    private RedisTemplate<String,String> redisTemplate;
    //加密盐（对密码进行加密）
    private static final String slat="yisheng!@#";
    @Override
    public Result login(LoginParam loginParam) {
        /*
        *1.检查参数是否合法
        * 2.根据用户名密码去数据库查询
        * 3.如果不存在，失败
        * 4。如果存在，使用jwt生成token 返回给前端
        * 5.token放入redis中，redis， token：user信息 设置过期时间
        * （登录认证的时候，先认证token字符串是否合法，然后去redis认证是否存在）
        * */
        String account=loginParam.getAccount();
        String password=loginParam.getPassword();
        //对密码进行加密,只用MD5加密可能会被破解，我们自己在加上一个加密盐
        password = DigestUtils.md5Hex(password+slat);

        if(StringUtils.isNullOrEmpty(account) || StringUtils.isNullOrEmpty(password)){
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());
        }
        SysUser user = sysUserService.findUser(account,password );
        if(user==null){
        return Result.fail(ErrorCode.ACCOUNT_PWD_NOT_EXIST.getCode(), ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());
        }
        //因为userid是唯一的，所以生成的tokrn也是唯一的
        String token = JWTUtils.createToken(user.getId());
            //将用户信息存在redis中，key是token，value是用户信息 ，因为token是独一无二的，所以key独一无二的     ,一天过期时间，生成token时已经设置了一天国企时间                                        //将对象转为string类型.       过期时间一天
        redisTemplate.opsForValue().set("TOKEN_"+token, JSON.toJSONString(user),1, TimeUnit.DAYS);

        return Result.success(token);
    }
/*检验token是否合法，以及redis中是否存储此token信息 ，并根据token返回user*/
    @Override
    public SysUser checkToken(String token) {
        if(StringUtils.isNullOrEmpty(token)){
            return null;
        }
        Map<String, Object> stringObjectMap = JWTUtils.checkToken(token);
        if(stringObjectMap==null){
            return null;
        }
        String userJson=redisTemplate.opsForValue().get("TOKEN_"+token);
        if(StringUtils.isNullOrEmpty(userJson)){
            return null;
        }
                        //之前往redis中存储时，转换为了JsonString，现在再转换成SysUser类型
        SysUser sysUser=JSON.parseObject(userJson,SysUser.class);
        return sysUser;
    }

    @Override
    public Result logout(String token) {
        redisTemplate.delete("TOKEN_"+token);
        return Result.success(null);
    }

    /*测试一下 密码admin加密后的密码*/
    public static void main(String[] args){
        System.out.println(DigestUtils.md5Hex("admin"+slat));
    }
}
