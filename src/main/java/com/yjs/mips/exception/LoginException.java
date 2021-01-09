package com.yjs.mips.exception;

/**
 * FileName:LoginException
 * Author:MJL
 * Date:2019/12/10 15:22
 */
public class LoginException extends Exception{

    private int code;

    private String msg;

    public LoginException(int code, String msg){
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
