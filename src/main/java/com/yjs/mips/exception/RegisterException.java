package com.yjs.mips.exception;

/**
 * FileName:RegisterException
 * Author:MJL
 * Date:2020/3/11 17:27
 */
public class RegisterException extends Exception{

    private int code;

    private String msg;

    public RegisterException(int code, String msg){
        this.code = code;
        this.msg = msg;
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
}
