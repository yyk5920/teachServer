package com.yjs.mips.util;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
public class Result implements Serializable {
    private int code;
    private String msg;
    private Object data;
    private Map<String, Object> map;
    public static Result succ(int code, String msg, Object data, Map<String, Object> map){
        Result r = new Result();
        r.setCode(code);
        r.setMsg(msg);
        r.setData(data);
        r.setMap(map);
        return r;
    }
}
