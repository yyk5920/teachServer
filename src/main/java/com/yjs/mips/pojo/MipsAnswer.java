package com.yjs.mips.pojo;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class MipsAnswer {
    private Long id;
    private Long studentId;
    private Long questionId;
    private Timestamp datetime;
    private boolean correct;
    private String info;
}
