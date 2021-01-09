package com.yjs.mips.service.impl;

import com.yjs.mips.dao.VAnswerMapper;
import com.yjs.mips.dao.VQuestionMapper;
import com.yjs.mips.dao.mips_cpu_design.MipsExperimentMapper;
import com.yjs.mips.dao.studentAssStatus.StuAssExeCompileInfoMapper;
import com.yjs.mips.dao.studentAssStatus.StuAssExeStatusInfoMapper;
import com.yjs.mips.dao.studentAssStatus.StuAssExeStatusMapper;
import com.yjs.mips.dao.studentAssStatus.StuAssStatusMapper;
import com.yjs.mips.pojo.mips_cpu_design.*;
import com.yjs.mips.service.VStudentService;
import com.yjs.mips.util.*;
//import jdk.swing.interop.SwingInterOpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.io.*;
import java.util.List;

@Service
public class VstudentServiceImpl implements VStudentService {
    @Autowired
    VQuestionMapper vQuestionMapper;
    @Autowired
    VAnswerMapper vAnswerMapper;
    @Autowired
    StuAssStatusMapper stuAssStatusMapper;
    @Autowired
    StuAssExeStatusMapper stuAssExeStatusMapper;
    @Autowired
    StuAssExeCompileInfoMapper stuAssExeCompileInfoMapper;
    @Autowired
    MipsExperimentMapper mipsExperimentMapper;
    @Autowired
    StuAssExeStatusInfoMapper stuAssExeStatusInfoMapper;
//    @Value("${web.upload-path}")
//    private String pathRoot;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public StuAssExeStatus run(String stu_id,Long ass_id, Long exe_id, String text) throws Exception {
        String content = text; // 代码
        // 设置路径
        String pathRoot = ""; //根路径
        String stuRoot = ""; //学生根路径
        String tvRoot = ""; //仿真文件路径
        String stuPath =  "";//学生mips处理器实验路径
        String tvPath = ""; //mips实验仿真路径
        String cmdStuPath = ""; //学生命令行路径
        String cmdTvPath = ""; //仿真文件命令行路径

        String os = System.getProperty("os.name");
        String SYS = "";
        if (os.toLowerCase().startsWith("win")) {  //如果是Windows系统
            SYS = "WINDOWS";
//            pathRoot = "D:/idea/verilog";
//            stuRoot = pathRoot + "/student/mips_cpu_design/";
//            tvRoot = pathRoot + "/sim/mips_cpu/";
//            stuPath =  stuRoot + exe_id +"/"+ stu_id;
//            tvPath = tvRoot + exe_id;
//            cmdStuPath = "D:\\idea\\verilog\\student\\mips_cpu_design\\" + exe_id + "\\" + stu_id;
//            cmdTvPath =  "D:\\idea\\verilog\\sim\\mips_cpu\\" + exe_id + "\\testbench";
            pathRoot = "D:/iverilog/result";
            stuRoot = pathRoot + "/student/";
            tvRoot = pathRoot + "/verilog/";
            stuPath =  stuRoot + exe_id +"/"+ stu_id;
            tvPath = tvRoot + exe_id;
            cmdStuPath = "D:\\iverilog\\result\\student\\" + exe_id + "\\" + stu_id;
            cmdTvPath =  "D:\\iverilog\\result\\verilog\\" + exe_id + "\\right";
        }
        else {  //linux 和mac
            System.out.print("操作系统是linux");
            SYS = "LINUX";
//            pathRoot = "/usr/data/verilog";
//            stuRoot = pathRoot + "/student/mips_cpu_design/";
//            tvRoot = pathRoot + "/sim/mips_cpu/";
//            stuPath =  stuRoot + exe_id +"/"+ stu_id;
//            tvPath = tvRoot + exe_id;
//            cmdStuPath = stuPath;
//            cmdTvPath = tvPath + "/testbench";
            pathRoot = "/opt/yyk/result";
            stuRoot = pathRoot + "/student/";
            tvRoot = pathRoot + "/verilog/";
            stuPath =  stuRoot + exe_id +"/"+ stu_id;
            tvPath = tvRoot + exe_id;
            cmdStuPath = stuPath;
            cmdTvPath = tvPath + "/right";
        }

        //获取模块相关信息
        MipsExperiment mipsExperiment = mipsExperimentMapper.getExperiment(exe_id);
        String SIM = mipsExperiment.getTb_path(); // 仿真文件名
        String CODE = mipsExperiment.getResult_path(); // 源代码文件名
        int MODE = mipsExperiment.getIs_single(); // MODE =1单模块，=2多模块

        // 将代码提交时间、提交内容保存至数据库
        Long timeStamp = System.currentTimeMillis();  // 获取当前时间戳
        System.out.println("代码提交timeStamp：" + timeStamp);
        StuAssExeStatusInfo stuAssExeStatusInfo = new StuAssExeStatusInfo();
        stuAssExeStatusInfo.setStu_id(stu_id);
        stuAssExeStatusInfo.setAss_id(ass_id);
        stuAssExeStatusInfo.setExe_id(exe_id);
        stuAssExeStatusInfo.setCommit_time(timeStamp);
        stuAssExeStatusInfo.setCommit_content(content);
        stuAssExeStatusInfoMapper.setAtuAssExeStatusInfo(stuAssExeStatusInfo);

        //将提交的代码写入文件
        String codePath = stuPath + "/" + CODE;
        System.out.print("代码写入文件路径：");
        System.out.println(codePath);
        File codeFile = new File(codePath);
        if(codeFile.exists()) //判断文件是否存在
        {
            codeFile.delete();
        }
        if (!codeFile.getParentFile().exists()) //先调用getParentFile()获得父目录，用.mkdirs()生成父目录文件夹，最后把你想要的文件生成到这个文件夹下面
        {
            boolean mkdir = codeFile.getParentFile().mkdirs();
            if (!mkdir) {
                throw new RuntimeException("创建目标文件所在目录失败！");
            }
        }

        FileWriter codeWriter;
        try {
            codeWriter = new FileWriter(codeFile);
            codeWriter.write(content);
            codeWriter.flush();//中途调用close()方法，输出区也还是有数据的
            codeWriter.close();//先调用flush()方法，就会强制把数据输出，缓存区就清空了，最后再关闭读写流调用close()就完成了。
        } catch (IOException e){
            e.printStackTrace();//printStackTrace()方法的意思是：在命令行打印异常信息在程序中出错的位置及原因
        }
/* try catch：自己处理异常
    try {
  *可能出现异常的代码
                    *} catch（异常类名A e）{
  *如果出现了异常类A类型的异常，那么执行该代码
                    *} ...（catch可以有多个）
  * finally {
  *最终肯定必须要执行的代码（例如释放资源的代码）
  *}
*/
        // 获取提交次数
        int count = stuAssExeStatusMapper.getExeCommitCount(stu_id, ass_id, exe_id);
        System.out.println("count：" + count);
        if(count == -1){
            StuAssExeStatus stuAssExeStatus = new StuAssExeStatus();
            stuAssExeStatus.setStu_id(stu_id);
            stuAssExeStatus.setAss_id(ass_id);
            stuAssExeStatus.setExe_id(exe_id);
            stuAssExeStatus.setExe_score(0);
            stuAssExeStatus.setCommit_count((long)1);
            stuAssExeStatus.setCommit_status(0);
            stuAssExeStatus.setCopy_rate(0);
            stuAssExeStatus.setCopy_origin("");
            stuAssExeStatus.setCopy_content("");
            stuAssExeStatusMapper.setStuAssExeStatus(stuAssExeStatus);
        }
        count++;

        // 题目信息记录
        StuAssExeStatus stuAssExeStatus = new StuAssExeStatus();
        stuAssExeStatus.setStu_id(stu_id);
        stuAssExeStatus.setAss_id(ass_id);
        stuAssExeStatus.setExe_id(exe_id);
        stuAssExeStatus.setCommit_count(Long.valueOf(count)); // 题目提交次数加一

        String bat = "";
        String [] linuxShell;
        String path = "";
        if(MODE == 1){
            if(SYS.equals("WINDOWS")){
                bat = "cmd /c D: && cd " + cmdStuPath + " && iverilog -o out " + cmdTvPath + "\\" + SIM + " " + cmdStuPath + "\\" + CODE +"&& del wave.txt"+ " && vvp -n out" + "&& ren wave.vcd wave.txt";
            }
            else{
                path = cmdStuPath;
                String shContent = "iverilog -o out "+cmdTvPath+"/"+SIM+" "+cmdStuPath+"/"+CODE+"\n";
                shContent += "vvp -n out";
                String shPath = stuPath + "/"+"bat.sh";
                bat = "sh "+shPath;
                System.out.print("sh文件路径：");
                System.out.println(shPath);
                File shFile = new File(shPath);
                FileWriter shWriter;
                try {
                    shWriter = new FileWriter(shFile);
                    shWriter.write(shContent);
                    shWriter.flush();
                    shWriter.close();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        }else if(MODE == 2){
            String relative_modules = mipsExperimentMapper.getRelativeModuleNames(exe_id);
            String[] relative_modules_arr = relative_modules.split(",");
            String relative_path = "";
            if(SYS.equals("WINDOWS")){
                for(int i=0; i<relative_modules_arr.length; i++){
                    String p =  stuRoot + relative_modules_arr[i] + "/" + stu_id;
                    relative_path = relative_path + " -y " + p;
                }
                bat = "cmd /c D: && cd " + cmdStuPath + " && iverilog -o out"  + relative_path + " " + cmdTvPath + "\\"+ SIM + " "+cmdStuPath + "\\" +CODE +"&& del wave.txt"+ " && vvp -n out" + "&& ren wave.vcd wave.txt";
                System.out.print("windows下多模块：");
                System.out.println(bat);
            }
            else{
                path = cmdStuPath;
                String shContent = "iverilog -o out";
//                +cmdTvPath+"/"+SIM+" "+cmdStuPath+"/"+CODE+"\n";
                for(int i=0; i<relative_modules_arr.length; i++){
                    shContent+=" -y "+ stuRoot + relative_modules_arr[i]+"/"+stu_id + " ";
                }
                shContent += cmdTvPath+"/"+SIM+" "+cmdStuPath+"/"+CODE+"\n";
                shContent += "vvp -n out";
                String shPath = stuPath + "/"+"bat.sh";
                bat = "sh "+shPath;
                System.out.print("sh文件路径：");
                System.out.println(shPath);
                File shFile = new File(shPath);
                FileWriter shWriter;
                try {
                    shWriter = new FileWriter(shFile);
                    shWriter.write(shContent);
                    shWriter.flush();
                    shWriter.close();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        else{
            System.out.println("是否为单模块数据出错");
        }

        // 题目编译信息记录
        StuAssExeCompileInfo stuAssExeCompileInfo = new StuAssExeCompileInfo();
        stuAssExeCompileInfo.setStu_id(stu_id);
        stuAssExeCompileInfo.setExe_id(exe_id);
        stuAssExeCompileInfo.setAss_id(ass_id);

        Command util = new Command();
        util.executeCommand(bat,path,SYS);//bat是执行命令，path是linux下cmdStuPath保存路径,SYS是判断windows还是linux
        System.out.println("  Linux 脚本执行结束");
//        System.out.println("util.getErroroutList()");
//        System.out.println(util.getErroroutList());
        StringBuffer sb= new StringBuffer();
        for(String l : util.getErroroutList()) {
            sb.append(l + System.lineSeparator());
        }
//        System.out.println("////////////////////");
//        System.out.println("kkkk"+sb+"sddddddd");
//        System.out.println("sssssss");
        String stuWaveResultPath = stuPath + "/wave.txt";
        String corWaveResultPath = tvPath + "/result/wave.txt";
//        String stuWarnResultPath = stuPath + "/signalwarn.json";
//        String corWarnResultPath = tvPath + "/signalwarn.json";
        String stuWaveSignalPath = stuPath + "/signal.json";
        String corWaveSignalPath = tvPath + "/result/signal.json";
        String stuWaveRightPath = stuPath + "/signalright.json";

//        String wavejson=new String();
//        WaveDrom wavedrom=new WaveDrom();
//        wavedrom.loadVar(corWaveResultPath,corWaveSignalPath);
        WaveDrom.loadVar(stuWaveResultPath,stuWaveSignalPath);//存放学生正确json格式
        WaveDrom.loadVar(corWaveResultPath,corWaveSignalPath);//存放正确对比用json格式
//        WaveDrom.loadVar(stuWaveResultPath,stuWarnResultPath);//存放学生错误波形json格式
        // 判断有无报错
        if(sb.length()==0){ // 没有报错，说明成功运行，判断结果是否正确
            Wave wave = CompareResult.compare(corWaveSignalPath, stuWaveSignalPath,stuWaveRightPath);
//            Wave wave1 =waveDrom.loadVar(corWaveResultPath, corAllResultPath);
            List<String> stuAllResultWave = ReadWave.getWave(stuWaveRightPath);
            List<String> corAllResultWave = ReadWave.getWave(corWaveSignalPath);
//             List<String> stuWarnResultWave = ReadWave.getWave(stuWarnResultPath);
//            WaveDrom wavedrom=new WaveDrom();

//            System.out.println(wavedromresult);
            if(wave.isTrue()){// 结果正确
                System.out.println("sb.length()==0");
                stuAssExeStatus.setExe_score(10);// 题目完全正确分数，应该从数据库中查找得到
                stuAssExeStatus.setCommit_status(1);// 成功提交题目
                stuAssExeStatusMapper.updateStuAssExeStatus(stuAssExeStatus);
            }else{ // 结果错误
                stuAssExeStatus.setExe_score(5);// 题目编译通过但是结果错误分数，应该从数据库中查找得到
                stuAssExeStatus.setCommit_status(0);// 题目提交状态：失败
//                stuAssExeStatus.setWaveData(wave.getWaveData());
                stuAssExeStatusMapper.updateStuAssExeStatus(stuAssExeStatus);
            }
            stuAssExeStatus.setStuWaveData(stuAllResultWave);
            stuAssExeStatus.setCorWaveData(corAllResultWave);
//            stuAssExeStatus.setStuWarnWaveData(stuWarnResultWave);
//            stuAssExeStatus.wavedrom(wavedromresult);
//            System.out.println(stuAssExeStatus.wa);
        }
        else{ // 编译有报错
            System.out.println("sb.length()!=0");
            stuAssExeStatus.setExe_score(0);// 题目编译通过但是结果错误分数，应该从数据库中查找得到
            stuAssExeStatus.setCommit_status(0);// 成功提交题目为1
            stuAssExeStatusMapper.updateStuAssExeStatus(stuAssExeStatus);

            //编译报错信息存入数据库
            stuAssExeCompileInfo.setCompile_info(sb.toString());
            stuAssExeCompileInfoMapper.setStuAssExeCompileInfo(stuAssExeCompileInfo);
            stuAssExeStatus.setCompile_info(sb.toString());
        }
        System.out.println(stuAssExeStatus);
        return stuAssExeStatus;
    }

}
