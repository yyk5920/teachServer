package com.yjs.mips.service.verilog;


import com.yjs.mips.exception.ExeException;

import java.util.List;
import java.util.Map;

public interface VerilogService {
    List<Map<String, Object>> getVerilogExeList() throws ExeException;
    List<Map<String, Object>>  getVerilogExeListByStr( Map<String, Object> map) throws ExeException;
    void addVerilogExe(Map<String, Object> map) throws ExeException;
    Map<String, Object> getVerilogExeContent(String str) throws ExeException;
    void modifyVerilogExe(Map<String, Object> map) throws ExeException;
    void deleteVerilogExeContent(String exe_id) throws ExeException;
}

