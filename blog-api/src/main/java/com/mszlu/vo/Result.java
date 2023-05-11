package com.mszlu.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
/*
* 自定义一个返回类型，用于给前台返回信息
* */
public class Result {
    private boolean success;
    //http状态码
    /*
    * 2开头的http状态码表示请求成功    4开头的http状态码表示请求出错
    * */
    private  int code;
    private String msg;
    private Object data;
//静态方法，无需实例化对象就可以直接调用
    public static Result success(Object data){
        //生成一个请求成功的Result，既然成功了，肯定会带着数据回前端
        //
        return new Result(true,200,"success",data);
    }
    public static Result fail(int code, String msg){
        //生成一个请求失败的Result，失败的话  返回http错误状态码code，错误提示信息，
        //既然请求失败，是不会带数据的
        return new Result(false,code,msg,null);
    }
}
