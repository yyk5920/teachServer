package com.yjs.mips.pojo.mips_cpu_design;

import lombok.Data;
//学生题目完成信息
@Data
public class StuAssStatus {
    private String stu_id;
    private Long ass_id;
    private int ass_score;
    private Long commit_count;
    private int commit_status;
}
