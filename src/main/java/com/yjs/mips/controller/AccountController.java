package com.yjs.mips.controller;

import cn.shuibo.annotation.Decrypt;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.yjs.mips.pojo.Homework;
import com.yjs.mips.pojo.HomeworkFinished;
import com.yjs.mips.pojo.HomeworkUnfinished;
import com.yjs.mips.pojo.Token;
import com.yjs.mips.pojo.account.Student;
import com.yjs.mips.pojo.requestInfo.RequestInfo;
import com.yjs.mips.service.RequestInfoService;
import com.yjs.mips.service.account.StuAccountService;
import com.yjs.mips.service.impl.TokenService;
import com.yjs.mips.util.JWTUtil;
import com.yjs.mips.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AccountController {
    @Autowired
    StuAccountService studentService;
    @Autowired
    TokenService tokenService;
    @Autowired
    RequestInfoService requestInfoService;

//    @Decrypt
    @RequestMapping("/user/login")
    @ResponseBody
    public Result login(@RequestBody Map<String, Object> map){
        System.out.println("user/login");
        Student student = studentService.checkLogin(map);
        if (student == null){
            System.out.println("用户为空");
            return Result.succ(20001, "账号或密码错误", "", null);
        }

        //将登录请求添加进数据库
        RequestInfo requestInfo = new RequestInfo();
        requestInfo.setStu_id(student.getStu_id());
        Long timeStamp = System.currentTimeMillis();  // 获取当前时间戳
        System.out.println("代码提交timeStamp：" + timeStamp);
        requestInfo.setRequest_time(timeStamp);
        requestInfo.setRequest_header("login");
        requestInfoService.setRequestInfo(requestInfo);
//        String token = "admin-token";
        Map<String, Object> data = new HashMap<>();
        data.put("stu_id",student.getStu_id());
        data.put("name",student.getStu_name());
        String token = tokenService.getToken(student);
        data.put("token", token);
        return Result.succ(20000, "登录成功", null, data);
    }

    @RequestMapping("/user/logout")
    @ResponseBody
    public Result logout(@RequestBody Map<String, Object> map){
        String stu_id = (String)map.get("username");
        RequestInfo requestInfo = new RequestInfo();
        requestInfo.setStu_id(stu_id);
        Long timeStamp = System.currentTimeMillis();  // 获取当前时间戳
        System.out.println("代码提交timeStamp：" + timeStamp);
        requestInfo.setRequest_time(timeStamp);
        requestInfo.setRequest_header("logout");
        requestInfoService.setRequestInfo(requestInfo);

        System.out.print("user/logout");
        return Result.succ(20000, "退出成功", "admin-token", null);
    }

    @RequestMapping("/user/info")
    @ResponseBody
    public Result getInfo(@RequestBody Map<String, Object> map){
        System.out.println("user/info");
        Map<String, Object> data = new HashMap<>();
        Student student = studentService.getStudentInfo(map);
        String[] role0 = new String []{"editor"};//学生
        String[] role1 = new String []{"admin"};//教师
        int role = student.getRole();
        String [] roles;
        if(role == 0){
            roles = role0;
        }
        else {
            roles = role1;
        }
        data.put("roles",roles);
        data.put("name",student.getStu_name());
        data.put("id",student.getStu_id());
        return Result.succ(20000, "登录成功",null, data );
    }
    @RequestMapping("/user/homework/unfinished")
    @ResponseBody
    public Result getUnfinishedHomework(){
        Homework h = new Homework();
        h.setId("0");
        h.setTitle("Mips微处理器设计");
        HomeworkUnfinished hs = new HomeworkUnfinished();
        hs.setItems(new Homework[]{h});
        hs.setTotal(1);
        return Result.succ(20000, "登录成功",hs,null );
    }
    @RequestMapping("/user/homework/finished")
    @ResponseBody
    public Result getFinishedHomework(){
        HomeworkFinished hs = new HomeworkFinished();
        hs.setItems(new Homework[]{});
        hs.setTotal(0);
        return Result.succ(20000, "登录成功",hs, null );
    }

}

