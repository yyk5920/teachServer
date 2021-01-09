package com.yjs.mips.pojo.account;

import lombok.Data;
@Data
public class Student {
    private String stu_id;
    private String stu_name;
    private String stu_class;
    private String stu_email;
    private int role;
    private String stu_password;
    private String token;
}