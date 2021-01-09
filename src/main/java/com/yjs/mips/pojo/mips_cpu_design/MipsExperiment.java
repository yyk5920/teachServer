package com.yjs.mips.pojo.mips_cpu_design;

import lombok.Data;

@Data
public class MipsExperiment {
    private Long id;
    private Long module_id;
    private String title;
    private String info;
    private String tb_path;
    private String result_path;
    private int is_single;
    private String module_name;
    private String input;
    private String output;
    private String io;
    private String compare_wave;
    private String bat;
}
