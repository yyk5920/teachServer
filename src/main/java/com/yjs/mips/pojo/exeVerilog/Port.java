package com.yjs.mips.pojo.exeVerilog;

import lombok.Data;

@Data
public class Port {
    private String name;
    private int width;
    private String type;
    private String in_out;
}
