package com.yjs.mips.dao.Compile;

import com.yjs.mips.pojo.compileInfo.Compile;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CompileMapper {
    public void setCompileInfo(Compile compile);
    public  Long getCompileId(Long code);
}
