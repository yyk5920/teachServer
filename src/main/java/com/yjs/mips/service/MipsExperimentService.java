package com.yjs.mips.service;

import com.yjs.mips.pojo.mips_cpu_design.MipsExperiment;
import com.yjs.mips.pojo.mips_cpu_design.StuAssScore;

import java.util.List;
import java.util.Map;

public interface MipsExperimentService {
    List<Map<String, Object>>  getExperimentList();
    List<Map<String, Object>>  getExperimentListStatus(Map<String, Object> map);
    MipsExperiment getExperiment(Long id);
    void setStuAssScore();
    List<Map<String, Object>> getStuAssScoreById(String stu_id);

}
