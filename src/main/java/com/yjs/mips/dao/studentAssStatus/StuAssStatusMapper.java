package com.yjs.mips.dao.studentAssStatus;

import com.yjs.mips.pojo.mips_cpu_design.StuAssExeStatus;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface StuAssStatusMapper {
//    public List<String> getExperimentList();
//    public MipsExperiment getExperiment(Long id);
    public void setStuAssStatus(StuAssExeStatus stuAssExeStatus);
    public int getCommitCount(Long stu_id, Long ass_id, Long exe_id);

}
