package com.yjs.mips.pojo.mips_cpu_design;

import lombok.Data;

@Data
public class StuAssExeStatusInfo {
    private String stu_id;
    private Long ass_id;
    private Long exe_id;
    private Long commit_time;
    private String commit_content;
}
