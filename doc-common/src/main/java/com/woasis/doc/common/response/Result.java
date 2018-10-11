package com.woasis.doc.common.response;

public class Result<T> {
    private int code;
    private String msg;
    private T data;

    public Result() {
        this.code = 1000;
    }

    public Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static Result OK() {
        return new Result();
    }

    public static Result OK(Object data) {
        Result result = new Result();
        result.setData(data);
        return result;
    }

    public static Result ERROR(Integer code, String msg){
        return new Result(code, msg);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
