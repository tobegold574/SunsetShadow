package com.demo.xiying.common;


import lombok.Data;

//标准response类


@Data
public class R {
    //状态码
    private int code;
    //类型
    private String type;
    //消息
    private String message;
    //是否成功
    private boolean success;
    //数据
    private Object data;

    //成功
    public static R success(String message){
        R r= new R();
        r.setCode(200);
        r.setSuccess(true);
        r.setType("success");
        r.setMessage(message);
        r.setData(null);
        return r;
    }

    //成功并且返回数据
    public static R success(String message, Object data){
        R r=success(message);
        r.setData(data);
        return r;
    }

    //警告
    public static R warning(String message){
        R r=error(message);
        r.setType("warning");
        r.setCode(500);
        return r;
    }

    //错误
    public static R error(String message){
        R r=success(message);
        r.setSuccess(false);
        r.setType("error");
        r.setCode(400);
        return r;
    }

    //致命错误
    public static R fatal(String message){
        R r =error(message);
        r.setCode(500);
        return r;
    }

}
