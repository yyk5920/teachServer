package com.yjs.mips.service.verilog.impl;

import com.yjs.mips.constants.Code;
import com.yjs.mips.dao.verilog.VerilogMapper;
import com.yjs.mips.exception.ExeException;
import com.yjs.mips.pojo.exeVerilog.ExeVerilogInfo;
import com.yjs.mips.pojo.exeVerilog.Port;
import com.yjs.mips.service.verilog.VerilogService;
import com.yjs.mips.util.verilog.CreateTbFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.yjs.mips.util.verilog.CreateTbFile.getTbFile;

@Service
public class VerilogServiceImpl implements VerilogService {
    @Autowired
    VerilogMapper verilogMapper;

    // 获取verilog题目header列表
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public List<Map<String, Object>> getVerilogExeList() throws ExeException{
        List<Map<String, Object>> lists = verilogMapper.getVerilogExeList();
        if(lists.size() == 0) throw new ExeException(Code.GET_EXE_LIST_EMPTY, "查询结果为空");
        return lists;
    }

    // 按条件获取verilog题目header列表
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public List<Map<String, Object>> getVerilogExeListByStr( Map<String, Object> map) throws ExeException {
        List<Map<String, Object>> lists = verilogMapper.getVerilogExeListByStr(map);
        if(lists.size() == 0) throw new ExeException(Code.QUERY_EXE_LIST_EMPTY, "查询结果为空");
        return lists;
    }

    // 添加一道verilog题目(header和content)到数据库
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void addVerilogExe(Map<String, Object> map) throws ExeException {
        Long timeStamp = System.currentTimeMillis();  // 获取当前时间戳
        String exe_id = timeStamp.toString();
        // 向题库exe_verilog表中添加一条题目记录
        ExeVerilogInfo exeVerilogInfo = new ExeVerilogInfo();
        // 题目header
        exeVerilogInfo.setExe_id(exe_id);
        exeVerilogInfo.setExe_title((String)map.get("exe_title"));
        exeVerilogInfo.setExe_type((String)map.get("exe_type"));
        exeVerilogInfo.setExe_cited_times(0);
        exeVerilogInfo.setExe_created_member((String)map.get("exe_created_member"));
        exeVerilogInfo.setExe_hard_easy((int)map.get("exe_hard_easy"));
        exeVerilogInfo.setExe_knowlesge_points((String)map.get("exe_knowlesge_points"));
        exeVerilogInfo.setExe_chapter((String)map.get("exe_chapter"));
        exeVerilogInfo.setExe_created_timestamp(timeStamp);
        // 题目content
        exeVerilogInfo.setExe_info((String)map.get("exe_info"));
        exeVerilogInfo.setExe_input((String)map.get("exe_input"));
        exeVerilogInfo.setExe_output((String)map.get("exe_output"));
        exeVerilogInfo.setExe_code_head((String)map.get("exe_code_head"));
        exeVerilogInfo.setExe_is_single((int)map.get("exe_is_single"));
        exeVerilogInfo.setExe_cited_modules((String)map.get("exe_cited_modules"));
        exeVerilogInfo.setExe_module_name((String)map.get("exe_module_name"));
        exeVerilogInfo.setExe_tb_path((String)map.get("exe_tb_path"));
        exeVerilogInfo.setExe_result_path((String)map.get("exe_result_path"));
        exeVerilogInfo.setExe_tb_code((String)map.get("exe_tb_code"));
        exeVerilogInfo.setExe_result_code((String)map.get("exe_result_code"));
        exeVerilogInfo.setExe_has_coe((int)map.get("exe_has_coe"));
        exeVerilogInfo.setExe_coe_path((String)map.get("exe_coe_path"));
        verilogMapper.addVerilogExe(exeVerilogInfo);

        // 题目的文件操作
        String exe_type = (String)map.get("exe_type");
        Boolean is_verilog = exe_type.charAt(0) == '0';
        if(is_verilog){
            if(exe_type.length()<2) new ExeException(Code.EXE_TYPE_WRONG, "题目类型参数错误");
            Boolean is_single = exe_type.charAt(1) == '1';
            // 处理题目逻辑
            String[] subPorts = new String[]{"ALU"};
            String tbNameP = (String)map.get("exe_tb_path");
            String tbName = tbNameP.substring(0,tbNameP.length()-2);
            String tbPath = getTbFile((String)map.get("exe_tb_code"), subPorts, tbName);
            System.out.println(tbPath);
            // 单模块题目 教师提供源文件、激励文件
            if(is_single){


            }else{ // 多模块题目
                if(exe_type.length()<3) throw new ExeException(Code.EXE_TYPE_WRONG, "题目类型参数错误");
                int mul_type = (int)exe_type.charAt(2);
                // 类型一
                if(mul_type == 1){
                    try {
//                        CreateTbFile.getTbFile((String)map.get("exe_tb_code"));
                    }catch (Exception e){
                        throw new ExeException(Code.TB_FORMAT_WRONG, "激励文件格式有误");

                    }


                }
                // 类型二
                else if(mul_type == 2){

                }
                // 类型三
                else if(mul_type == 3){

                }
                else throw new ExeException(Code.EXE_TYPE_WRONG, "题目类型参数错误");
            }

        }
        else {
            throw new ExeException(Code.EXE_TYPE_WRONG, "题目类型参数错误");
        }


    }

    // 通过id获取verilog题目的content
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Map<String, Object> getVerilogExeContent(String exe_id){
        Map<String, Object> map = verilogMapper.getVerilogExeContent(exe_id);
        return map;
    }

    // 通过id修改verilog题目的header和content
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void modifyVerilogExe(Map<String, Object> map) throws ExeException {
        Long timeStamp = System.currentTimeMillis();  // 获取当前时间戳
        String exe_id = (String)map.get("exe_id"); // 要修改的题目id
        if(exe_id == null || exe_id == "") throw new ExeException(Code.CONTENT_MISS, "缺少题目id参数");
        int exe_cited_times = (int)map.get("exe_cited_times"); // 该题目的引用次数
        ExeVerilogInfo exeVerilogInfo = new ExeVerilogInfo();
        // 题目header
        exeVerilogInfo.setExe_id(exe_id);
        exeVerilogInfo.setExe_title((String)map.get("exe_title"));
        exeVerilogInfo.setExe_type((String)map.get("exe_type"));
        exeVerilogInfo.setExe_cited_times(exe_cited_times);
        exeVerilogInfo.setExe_created_member((String)map.get("exe_created_member"));
        exeVerilogInfo.setExe_hard_easy((int)map.get("exe_hard_easy"));
        exeVerilogInfo.setExe_knowlesge_points((String)map.get("exe_knowlesge_points"));
        exeVerilogInfo.setExe_chapter((String)map.get("exe_chapter"));
        exeVerilogInfo.setExe_created_timestamp(timeStamp);

        // 题目content
        exeVerilogInfo.setExe_info((String)map.get("exe_info"));
        exeVerilogInfo.setExe_input((String)map.get("exe_input"));
        exeVerilogInfo.setExe_output((String)map.get("exe_output"));
        exeVerilogInfo.setExe_code_head((String)map.get("exe_code_head"));
        exeVerilogInfo.setExe_is_single((int)map.get("exe_is_single"));
        exeVerilogInfo.setExe_cited_modules((String)map.get("exe_cited_modules"));
        exeVerilogInfo.setExe_module_name((String)map.get("exe_module_name"));
        exeVerilogInfo.setExe_tb_path((String)map.get("exe_tb_path"));
        exeVerilogInfo.setExe_result_path((String)map.get("exe_result_path"));
        exeVerilogInfo.setExe_tb_code((String)map.get("exe_tb_code"));
        exeVerilogInfo.setExe_result_code((String)map.get("exe_result_code"));
        exeVerilogInfo.setExe_has_coe((int)map.get("exe_has_coe"));
        exeVerilogInfo.setExe_coe_path((String)map.get("exe_coe_path"));

        verilogMapper.modifyVerilogExe(exeVerilogInfo);
    }


    // 通过id删除verilog题目的header和content
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void deleteVerilogExeContent(String exe_id){
        verilogMapper.deleteVerilogExeContent(exe_id);
    }

}
