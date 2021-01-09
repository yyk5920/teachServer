package com.yjs.mips.pojo.mips_cpu_design;

import lombok.Data;

@Data
public class StuAssExeCompileInfo {
    private String stu_id;
    private Long ass_id;
    private Long exe_id;
    private Long commit_code;
    private Long compile_count;
    private String compile_info;
}
