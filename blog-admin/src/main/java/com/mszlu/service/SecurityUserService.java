//package com.mszlu.service;
//
//import com.mszlu.entity.Admin;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Component;
//
//import java.util.ArrayList;
//
///*
//* 登录认证
//* */
//@Component
//                               //继承一个spring security 提供的接口
//public class SecurityUserService implements UserDetailsService {
//    @Autowired
//    private AdminService adminService;
//    @Override
//    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
//        //当你登录的时候，会把username传递到这里(s)
//        //通过username查询用户表，如果用户存在，将用户密码告诉spring 的security
//        //如果不存在，那么返回null，代表认证失败
//        Admin admin=adminService.findUserByName(name);
//        if(admin==null) {
//            //登陆失败
//            return null;
//        }
//        //UserDetails 是一个接口，所以要new一个实现类，  User是spring security提供的，不要选错了
//        UserDetails userDetails=new User(name, admin.getPassword(), new ArrayList<>());
//        return  userDetails;
//    }
//}
