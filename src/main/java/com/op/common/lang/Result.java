package com.op.common.lang;

import com.baomidou.mybatisplus.extension.api.R;
import lombok.Data;

import java.io.Serializable;

/**
 * - 是否成功，可用code表示（如200表示成功，400表示异常）
 * - 结果消息
 * - 结果数据
 * */
@Data
public class Result<T> implements Serializable {
    private int code;      //200表示正常，非200表示异常
    private String msg;
    private T data;

    public static<T> Result succ() {
        return succ(200,"操作成功",null);
    }
    public static<T> Result succ(T data) {
        return succ(200,"操作成功",data);
    }
    public static<T> Result succ(String msg, T data) {
        return succ(200,msg,data);
    }
    public static<T> Result succ(int code,String msg){
        return succ(200,msg,null);
    }

    public static<T> Result succ(int code,String msg, T data) {
        Result m = new Result();
        m.setCode(200);
        m.setData(data);
        m.setMsg(msg);
        return m;
    }

    public static<T> Result fail(String msg){
        return fail(400,msg,null);

    }
    public static<T> Result fail(String msg,T data) {
        return fail(400,msg,data);
    }
    public static<T> Result fail(int code,String msg, T data) {
        Result m = new Result();
        m.setCode(400);
        m.setData(data);
        m.setMsg(msg);
        return m;
    }
}

//import lombok.Data;
//
//import java.io.Serializable;
//
//@Data
//public class Result implements Serializable {
//    private String code;
//    private String msg;
//    private Object data;
//    public static Result succ(Object data) {
//        Result m = new Result();
//        m.setCode("0");
//        m.setData(data);
//        m.setMsg("操作成功");
//        return m;
//    }
//    public static Result succ(String mess, Object data) {
//        Result m = new Result();
//        m.setCode("0");
//        m.setData(data);
//        m.setMsg(mess);
//        return m;
//    }
//    public static Result fail(String mess) {
//        Result m = new Result();
//        m.setCode("-1");
//        m.setData(null);
//        m.setMsg(mess);
//        return m;
//    }
//    public static Result fail(String Code, String mess, Object data) {
//        Result m = new Result();
//        m.setCode("-1");
//        m.setData(data);
//        m.setMsg(mess);
//        return m;
//    }
//}
//
