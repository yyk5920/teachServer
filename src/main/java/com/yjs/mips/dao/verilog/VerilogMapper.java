package com.yjs.mips.dao.verilog;

import com.yjs.mips.pojo.exeVerilog.ExeVerilogInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface VerilogMapper {
    List<Map<String, Object>> getVerilogExeList();
    List<Map<String, Object>> getVerilogExeListByStr( Map<String, Object> map);
    void addVerilogExe(ExeVerilogInfo exeVerilogInfo);
    Map<String, Object> getVerilogExeContent(String exe_id);
    void modifyVerilogExe(ExeVerilogInfo exeVerilogInfo);
    void deleteVerilogExeContent(String exe_id);
}
