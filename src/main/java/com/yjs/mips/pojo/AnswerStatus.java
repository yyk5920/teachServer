package com.yjs.mips.pojo;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class AnswerStatus {
    private boolean correct;
    private String info;
    private List<Map<String, Integer>> reg;
    private List<Map<String, Integer>> mem;
    private List<Map<String, Integer>> targetReg;
    private List<Map<String, Integer>> targetMem;
}
