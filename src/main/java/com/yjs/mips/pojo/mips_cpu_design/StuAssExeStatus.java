package com.yjs.mips.pojo.mips_cpu_design;

import lombok.Data;
import org.hibernate.validator.constraints.URL;

import java.util.List;

@Data
public class StuAssExeStatus {
    private String stu_id;
    private Long ass_id;
    private Long exe_id;
    private int exe_score;
    private Long commit_count;
    private int commit_status;
    private int copy_rate;
    private String copy_origin;
    private String copy_content;

    public String compile_info;// 编译信息，与数据库无关
    public String compare_error; // 编译通过，学生结果与标准结果的对比信息，与数据库无关
    //    private List<String> stuWarnWaveData; // 波形数据，与数据库无关
    private List<String> stuWaveData; // 学生作业全部波形数据，与数据库无关
    private List<String> corWaveData;// 全部正确波形数据，与数据库无关//wavedrom的json存放
//    private List<String> errorData; // 波形对比错误信息 ，与数据库无关


}
