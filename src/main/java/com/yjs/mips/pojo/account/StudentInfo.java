package com.yjs.mips.pojo.account;

import lombok.Data;

@Data
public class StudentInfo {
    private String stu_id;
    private String stu_name;
    public StudentInfo(String stu_id, String name){
        this.stu_id = stu_id;
        this.stu_name = name;
    }
}
