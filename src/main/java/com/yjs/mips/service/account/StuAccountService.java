package com.yjs.mips.service.account;

import com.yjs.mips.pojo.account.Student;

import java.util.List;
import java.util.Map;

public interface StuAccountService {
    Student getStudentById(String stu_id);
    Student checkLogin(Map<String, Object> map);
//    String getToken(Student student);
    Student getStudentInfo(Map<String, Object> map);
    public List<String> getStudentAll();

}
