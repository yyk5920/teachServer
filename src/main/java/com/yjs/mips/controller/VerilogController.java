package com.yjs.mips.controller;

import com.yjs.mips.constants.Code;
import com.yjs.mips.exception.ExeException;
import com.yjs.mips.pojo.mips_cpu_design.MipsExperiment;
import com.yjs.mips.pojo.mips_cpu_design.StuAssExeStatus;
import com.yjs.mips.service.MipsExperimentService;
import com.yjs.mips.service.VStudentService;
import com.yjs.mips.service.verilog.VerilogService;
import com.yjs.mips.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class VerilogController {
    @Autowired
    VStudentService vStudentService;
    @Autowired
    MipsExperimentService mipsExperimentService;
    @Autowired
    VerilogService verilogService;

    @RequestMapping("/user/experiment/vlg-run")
    @ResponseBody
    public Result answerStatusGet(@RequestBody Map<String, Object> map) throws Exception {
        System.out.println("进入verilog");
        String studentId = (String) map.get("studentId");
        Long assignmentId = Long.parseLong((String) map.get("assignment_id"));
        Long questionId = Long.parseLong((String) map.get("module_id"));
        String text = (String) map.get("content");
        StuAssExeStatus status = vStudentService.run(studentId, assignmentId, questionId, text);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("compile_info",status.getCompile_info());
//        resultMap.put("errorData",status.getErrorData());
        resultMap.put("score",status.getExe_score());
        resultMap.put("commit_status",status.getCommit_status());
        resultMap.put("corWaveData",status.getCorWaveData());
        resultMap.put("stuWaveData",status.getStuWaveData());
        Object ResultStatus = new Object();
        return Result.succ(20000, "请求成功",resultMap,null);
    }

    @RequestMapping("/user/experiment/lists")
    @ResponseBody
    public Result getExperimentList(@RequestBody(required = false) Map<String, Object> map){
        List<Map<String, Object>>items = mipsExperimentService.getExperimentListStatus(map);
        return  Result.succ(20000, "登录成功", items,null);
    }

    @RequestMapping("/user/experiment/module")
    @ResponseBody
    public Result getExperiment(@RequestBody Map<String, Object> map){
        Long id = Long.valueOf((int)map.get("module_id"));
        MipsExperiment mipsExperiment = mipsExperimentService.getExperiment(id);
        return  Result.succ(20000, "登录成功", mipsExperiment, null);
    }

    @RequestMapping("/user/experiment/score")
    @ResponseBody
    public Result setMipsExeperimentScore(@RequestBody Map<String, Object> map){
        System.out.println("user/score");
        mipsExperimentService.setStuAssScore();
        return Result.succ(20000, "设置成绩成功",null, null );
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 获取题目列表
     * @return
     */
    @RequestMapping("/user/verilog/lists")
    @ResponseBody
    public Result getVerilogExeHeaderList(){
        System.out.println("user/verilog/lists");
        try {
            List<Map<String, Object>> lists = verilogService.getVerilogExeList();
            return Result.succ(Code.SUCCESS, "获取题目列表成功",lists, null );
        }  catch (ExeException e){
            return Result.succ(e.getCode(), e.getMsg(),null, null );
        }catch (Exception e) {
            System.out.println("getVerilogExeList bug");
            return Result.succ(Code.GET_EXE_LIST_FAILED, "获取题目列表失败",null, null );
        }
    }

    /**
     * 按条件获取题目列表
     * @param map
     * @return
     */
    @RequestMapping("/user/verilog/lists_query")
    @ResponseBody
    public Result getVerilogExeHeaderListByStr(@RequestBody Map<String, Object> map){
        System.out.println("user/verilog/lists按条件查询");
        try {
            List<Map<String, Object>> lists = verilogService.getVerilogExeListByStr(map);
            return Result.succ(Code.SUCCESS, "查询成功",lists, null );
        } catch (ExeException e){
            return Result.succ(e.getCode(), e.getMsg(),null, null );
        } catch (Exception e) {
            System.out.println("getVerilogExeListByStr bug");
            return Result.succ(Code.QUERY_EXE_LIST_FAILED, "查询失败",null, null );
        }
    }

    /**
     * 添加一道verilog题目信息（包括header和content）
     * @param map
     * @return
     */
    @RequestMapping("/user/verilog/addExeInfo")
    @ResponseBody
    public Result addVerilogExeInfo(@RequestBody Map<String, Object> map){
        System.out.println("user/verilog/addExe添加verilog题目");
        try {
            verilogService.addVerilogExe(map);
        } catch (ExeException e){
            System.out.println("addVerilogExe ExeException bug");
            e.printStackTrace();
            return Result.succ(Code.ADD_EXE_INFO_FAILED, e.getMsg(),null, null );
        }
        catch (Exception e) {
            System.out.println("addVerilogExe Exception bug");
//            e.printStackTrace();
            return Result.succ(Code.ADD_EXE_INFO_FAILED, e.toString(),null, null );
        }
        return Result.succ(Code.SUCCESS, "添加verilog题目成功",null, null );
    }

    /**
     * 获取一道verilog题目的content
     * @param map
     * @return
     */
    @RequestMapping("/user/verilog/exeContent")
    @ResponseBody
    public Result getVerilogExeContent(@RequestBody Map<String, Object> map){
        System.out.println("user/verilog/获取一道verilog题目的content");
        try {
            Map<String, Object> list = verilogService.getVerilogExeContent((String)map.get("exe_id"));
            return Result.succ(20000, "获取verilog题目content",list, null );
        } catch (Exception e) {
            System.out.println("getVerilogExeContent bug");
            return Result.succ(Code.GET_EXE_CONTENT_FAILED, "获取题目失败",null, null );
        }
    }

    /**
     * 修改一道verilog题目
     * @param map
     * @return
     */
    @RequestMapping("/user/verilog/modifyExe")
    @ResponseBody
    public Result modifyVerilogExeContent(@RequestBody Map<String, Object> map){
        System.out.println("user/verilog/修改一道verilog题目的content");
        try {
            verilogService.modifyVerilogExe(map);
            return Result.succ(Code.SUCCESS, "修改verilog题目content",null, null );
        } catch (ExeException e) {
            e.printStackTrace();
            return Result.succ(e.getCode(), e.getMsg(),null, null );
        } catch (Exception e){
            e.printStackTrace();
            return Result.succ(Code.MODIFY_EXE_FAILED, "修改题目失败",null, null );
        }

    }

    /**
     * 删除一道verilog题目
     * @param map
     * @return
     */
    @RequestMapping("/user/verilog/deleteExe")
    @ResponseBody
    public Result deleteVerilogExeContent(@RequestBody Map<String, Object> map){
        System.out.println("user/verilog/修改一道verilog题目的content");
        try {
            verilogService.deleteVerilogExeContent((String)map.get("exe_id"));
        } catch (Exception e) {
            System.out.println("deleteVerilogExeContent bug");
            return Result.succ(Code.DELETE_EXE_FAILED, "删除题目失败",null, null );
        }
        return Result.succ(Code.SUCCESS, "删除题目成功",null, null );
    }
}