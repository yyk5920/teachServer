package com.yjs.mips.constants;

/**
 * FileName:Code
 * Author:MJL
 * Date:2019/12/1015:35
 */
public class Code {

    public static final int SUCCESS = 2000;

    /**
     * 与注册相关的状态码
     */
    public static final int USER_EXISETED = 6001;

    public static final int USER_NOT_EXIST = 6002;

    public static final int MODIFY_USERINFO_FAIL = 6003;

    /**
     * 与登录相关的状态码
     */
    public static final int USERINFO_EMPTY = 4001;

    public static final int USERNAME_NOT_EXIST = 4002;

    public static final int PASSWORD_ERROR = 4003;

    public static final int SERVER_ERROR = 5001;

    /**
     * 与token相关的状态码
     */
    public static final int TOKEN_NOT_EXIST = 4011;

    public static final int TOKEN_EXPIRES = 4012;

    /**
     * 与题目相关的通用状态码
     */
    public static final int CONTENT_MISS = 7001;
    public static final int MODIFY_EXE_FAILED = 7002;
    public static final int DELETE_EXE_FAILED = 7003;
    public static final int GET_EXE_CONTENT_FAILED= 7004;
    public static final int ADD_EXE_INFO_FAILED= 7005;
    public static final int QUERY_EXE_LIST_FAILED= 7006;
    public static final int GET_EXE_LIST_FAILED= 7007;
    public static final int QUERY_EXE_LIST_EMPTY= 7008;
    public static final int GET_EXE_LIST_EMPTY= 7009;
    public static final int EXE_TYPE_WRONG= 7010;

    /**
     * 与verilog题目相关的状态码
     */
    public static final int TB_ADD_FAILED= 7101;
    public static final int TB_FORMAT_WRONG= 7102;
    public static final int PORT_FORMAT_WRONG = 7103;


    /**
     * 与路径相关的常量
     */
    public static final String ROOT_VERILOG = "G:idea/data/verilog";
    public static final String ROOT_MIPS = "/usr/data/mips";
    public static final String ROOT_C = "/usr/data/c";

}
