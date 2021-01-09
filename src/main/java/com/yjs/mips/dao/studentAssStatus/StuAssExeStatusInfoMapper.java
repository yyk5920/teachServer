package com.yjs.mips.dao.studentAssStatus;

import com.yjs.mips.pojo.mips_cpu_design.StuAssExeStatusInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface StuAssExeStatusInfoMapper {
    public void setAtuAssExeStatusInfo(StuAssExeStatusInfo stuAssExeStatusInfo);
}
