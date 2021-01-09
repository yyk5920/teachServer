package com.yjs.mips.pojo.exeVerilog;

import lombok.Data;

@Data
public class ExeVerilogInfo {
    // 题目header
    private String exe_id;
    private String exe_title;
    private String exe_type;
    private int exe_cited_times;
    private String exe_created_member;
    private Long exe_created_timestamp;
    private int exe_hard_easy;
    private String exe_knowlesge_points;
    private String exe_chapter;

    // 题目内容
    private String exe_info;
    private String exe_input;
    private String exe_output;
    private String exe_code_head;
    private int exe_is_single;
    private String exe_cited_modules;
    private String exe_module_name;
    private String exe_tb_path;
    private String exe_result_path;
    private String exe_tb_code;
    private String exe_result_code;
    private int exe_has_coe;
    private String exe_coe_path;
}