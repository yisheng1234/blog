package com.mszlu.utils;

import com.mszlu.entity.SysUser;

public class UserThreadLocal {

    private static final ThreadLocal<SysUser> LOCAL=new ThreadLocal<>();
    public static void put(SysUser sysUser){
        LOCAL.set(sysUser);
    }
    public static SysUser get(){
        return LOCAL.get();
    }
    public static void remove(){
        LOCAL.remove();
    }
}
