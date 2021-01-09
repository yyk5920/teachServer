package com.yjs.mips.dao.mips_cpu_design;

import com.yjs.mips.pojo.mips_cpu_design.MipsExperiment;
import com.yjs.mips.pojo.mips_cpu_design.StuAssScore;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface MipsExperimentMapper {
    List<Map<String, Object>> getExperimentList();
    int getExeScore(String stu_id,long ass_id, Long module_id);
    MipsExperiment getExperiment(Long module_id);
    String getRelativeModuleNames(Long module_id);
    void setStuAssScore(StuAssScore stuAssScore);
    List<Map<String, Object>> getStuAssScoreById(String stu_id);
}
