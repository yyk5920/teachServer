package com.yjs.mips.dao.studentAssStatus;

import com.yjs.mips.pojo.mips_cpu_design.StuAssExeStatus;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface StuAssExeStatusMapper {
    void updateStuAssExeStatus(StuAssExeStatus stuAssExeStatus);
    int getExeCommitCount(String stu_id, Long ass_id, Long exe_id);
    void setStuAssExeStatus(StuAssExeStatus stuAssExeStatus);
}
