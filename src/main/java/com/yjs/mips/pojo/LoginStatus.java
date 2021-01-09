package com.yjs.mips.pojo;

import lombok.Data;

@Data
public class LoginStatus {
    private boolean isLoginSuccess;
    private int code;
    private String data;

}