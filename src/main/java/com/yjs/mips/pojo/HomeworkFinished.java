package com.yjs.mips.pojo;

import lombok.Data;

@Data
public class HomeworkFinished {
    private int total;
    private Homework[] items;
}
