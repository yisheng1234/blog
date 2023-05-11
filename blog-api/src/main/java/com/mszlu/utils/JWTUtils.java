package com.mszlu.utils;

import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @param null:
 * @return null
 * @author yisheng
 * @description 这个只是用来加密用户id，判断用户是否登陆过期的
 * @date 2021-11-26 10:44
 */
public class JWTUtils {
    //jwtToken代表密钥，A,B部分加上密钥形成c部分。jwt介绍在笔记中
    private static final String jwtToken = "123456Mszlu!@#$$";

    public static String createToken(Long userId){
        Map<String,Object> claims = new HashMap<>();
        claims.put("userId",userId);
        JwtBuilder jwtBuilder = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, jwtToken) // 签发算法，秘钥为jwtToken，A部分
                .setClaims(claims) // body数据，要唯一，自行设置，B部分
                .setIssuedAt(new Date()) // 设置签发时间    24 * 60 * 60 * 1000
                .setExpiration(new Date(System.currentTimeMillis() +24 * 60 * 60 * 1000));// 一天的有效时间，1000毫秒（1秒）
        String token = jwtBuilder.compact();
        return token;
    }
//检查token是否合法
    public static Map<String, Object> checkToken(String token){
        try {
            Jwt parse = Jwts.parser().setSigningKey(jwtToken).parse(token);
            return (Map<String, Object>) parse.getBody();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;

    }
//    public static void main(String[] args){
//        String token=JWTUtils.createToken(1l);
//        System.out.println(token);
//        Map<String,Object> map=JWTUtils.checkToken(token);
//        System.out.println(map.get("userId"));
//    }

}