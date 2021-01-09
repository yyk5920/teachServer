package com.yjs.mips.pojo;

import lombok.Data;

@Data
public class HomeworkUnfinished {
    private int total;
    private Homework[] items;
}
