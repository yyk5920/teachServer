package com.yjs.mips.dao.studentAssStatus;

import com.yjs.mips.pojo.mips_cpu_design.StuAssExeCompileInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface StuAssExeCompileInfoMapper {
    public void setStuAssExeCompileInfo(StuAssExeCompileInfo stuAssExeCompileInfo);
    public void setStuAssExeCommitCount(StuAssExeCompileInfo stuAssExeCommitCount);
    public int getStuAssExeCommitCount(StuAssExeCompileInfo stuAssExeCommitCount);
//    public int getExeCommitCount(Long stu_id, Long ass_id, Long exe_id);
}
