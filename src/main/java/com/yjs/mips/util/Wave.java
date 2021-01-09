package com.yjs.mips.util;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Wave {
    private boolean isTrue;
    private List<String> corResult;
    private List<String> stuResult;
    public static Wave setWaveData(boolean isTrue,  List<String> corResult , List<String> stuResult){
        Wave wave = new Wave();
        wave.isTrue = isTrue;
        wave.corResult = corResult;
        wave.stuResult = stuResult;
        return wave;
    }
}