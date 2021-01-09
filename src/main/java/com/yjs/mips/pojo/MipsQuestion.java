package com.yjs.mips.pojo;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class MipsQuestion {
    private String questionId;
    private List<Map<String, String>> memsInit;
    private List<Map<String, String>> regsInit;
    private List<Map<String, String>> memsRes;
    private List<Map<String, String>> regsRes;
    private Map<String, Object> stack;
}
