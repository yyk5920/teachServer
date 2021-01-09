package com.yjs.mips.util.verilog;

import com.yjs.mips.constants.Code;
import com.yjs.mips.exception.ExeException;
import com.yjs.mips.exception.FileForException;
import com.yjs.mips.pojo.exeVerilog.Port;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.*;

public class CreateTbFile {
    public static String codeName;
    public static String tbName;
    public String str;
    public CreateTbFile(String str, String codeName, String tbName){
        this.str = str;
        this.codeName = codeName;
        this.tbName = tbName;
    }

    public static String getTbFile(String str, String []subPorts, String tbName) throws ExeException {
        String handleDisplay = "\\$fdisplay(handle,\"time ";
        String signalHandleDisplay = "\\$fdisplay(signalHandle,\"time ";
        HashMap<String, Object> ports = getPorts(str, subPorts);
        Set<String> set = ports.keySet();
        Iterator<String> it = set.iterator();
        int portNum = 0;
        String portStr = "";
        while (it.hasNext()){
            String p = it.next();
            handleDisplay = handleDisplay + p + " ";
            signalHandleDisplay = signalHandleDisplay + p + " ";
            portNum++;
            portStr = portStr+p+",";
        }
        portStr = portStr.substring(0,portStr.length()-1);
        handleDisplay = handleDisplay.trim() + "\");\n";
        signalHandleDisplay = signalHandleDisplay.trim() + "\");\n";
        String dumpvar = "\\$dumpvars(0, " + tbName +");\nend";
        String placeHolder = "integer handle;\n" +
                "integer signalHandle;\n" +
                "initial begin\n" +
                "handle = \\$fopen(\"./result.txt\");\n" +
                "signalHandle = \\$fopen(\"./signal.txt\");\n" +
                handleDisplay +
                signalHandleDisplay +
                "end\n" +
                "\n" +
                "initial\n" +
                "begin            \n" +
                "    \\$dumpfile(\"wave.vcd\");        //生成的vcd文件名称\n" +
                dumpvar;
        String h = "\"";
        for(int i=0; i<=portNum; i++){
            h = h+"%h ";
        }
        h = h.trim()+"\"";
        String sampHandle = "\\$fdisplay(handle,\"" + h + ",\\$time," + portStr +");\n";
        String sampSignalHandle = "\\$fdisplay(signalHandle,\"" + h + ",\\$time," + portStr +");\n";
        String sample = sampHandle+sampSignalHandle;

        // 将片段插入激励代码
        Pattern p = Pattern.compile("//\\s*sampling here");
        Matcher m = p.matcher(str);
        StringBuffer sb = new StringBuffer();
        System.out.println(placeHolder);
        while (m.find()){
            m.appendReplacement(sb, placeHolder);
        }
        m.appendTail(sb);
        System.out.println(sb.toString());
        String newStr = sb.toString();

        // 将片段插入激励代码
        Pattern pp = Pattern.compile("#[0-9]{1,}");
        Matcher mm = pp.matcher(newStr);
        StringBuffer sbb = new StringBuffer();
        System.out.println(sample);
        while (mm.find()){
            String num = mm.group(0)+";\n" + sample;
            mm.appendReplacement(sbb, num);
        }
        mm.appendTail(sbb);
        System.out.println(sbb.toString());


        //将提交的代码写入文件
        String tbPath = Code.ROOT_VERILOG+"/sim/" + tbName+".v";
        System.out.print("代码写入文件路径：");
        System.out.println(tbPath);
        File codeFile = new File(tbPath);
        if(codeFile.exists()){
            codeFile.delete();
        }
        if (!codeFile.getParentFile().exists()) {
            boolean mkdir = codeFile.getParentFile().mkdirs();
            if (!mkdir) {
                throw new RuntimeException("创建目标文件所在目录失败！");
            }
        }

        FileWriter codeWriter;
        try {
            codeWriter = new FileWriter(codeFile);
            codeWriter.write(sbb.toString());
            codeWriter.flush();
            codeWriter.close();
        } catch (IOException e){
            e.printStackTrace();
        }
        return tbPath;
    }
    public String addPort(String str){
        String str1 = "integer handle;\n" +
                "integer signalHandle;\n" +
                "initial begin\n" +
                "handle = $fopen(\"./result.txt\");\n" +
                "signalHandle = $fopen(\"./signal.txt\");\n" +
                "$display($time, \" << Coming out of reset >>\");";
        String str2 = "`timescale 1ns / 1ps\n" +
                "module alu_sim();\n" +
                "    reg [31:0] In1,In2;\n" +
                "    reg [3:0] ALUCtr;\n" +
                "    wire [31:0] Res;\n" +
                "    wire Zero;\n" +
                "    ALU u0(In1, In2, ALUCtr, Res, Zero);\n" +
                "    initial\n" +
                "    begin\n" +
                "    In1=4;\n" +
                "    In2=8;\n" +
                "    ALUCtr=4'b0000;\n" +
                "    #10;\n" +
                "    ALUCtr=4'b0001;\n" +
                "    #10;\n" +
                "    ALUCtr=4'b0010;\n" +
                "    #10;\n" +
                "    ALUCtr=4'b0110;\n" +
                "    #10;\n" +
                "    ALUCtr=4'b0111;\n" +
                "    #10;\n" +
                "    In1=32'h44444444;\n" +
                "    In2=32'h88888888;\n" +
                "    ALUCtr=4'b0000;\n" +
                "    #10;\n" +
                "    ALUCtr=4'b0001;\n" +
                "    #10;\n" +
                "    ALUCtr=4'b0010;\n" +
                "    #10;\n" +
                "    ALUCtr=4'b0110;\n" +
                "    #10;\n" +
                "    ALUCtr=4'b0111;\n" +
                "    #10;\n" +
                "    In1=32'h99999999;\n" +
                "    In2=32'h88888888;\n" +
                "    ALUCtr=4'b0000;\n" +
                "    #10;\n" +
                "    ALUCtr=4'b0001;\n" +
                "    #10;\n" +
                "    ALUCtr=4'b0010;\n" +
                "    #10;\n" +
                "    ALUCtr=4'b0110;\n" +
                "    #10;\n" +
                "    ALUCtr=4'b0111;\n" +
                "    #10;\n" +
                "    $stop;\n" +
                "    end\n" +
                "endmodule";
        return "";
    }
    public static HashMap<String, Object> getPorts(String str, String[] subPorts) throws ExeException {
        HashMap<String, Object> map = new HashMap<String, Object>();
       
        // 获取调用的子模块端口
        // 拼接正则表达式
        String strPorts = ""; 
        for(int i=0; i<subPorts.length; i++){
            if(i==subPorts.length -1) strPorts = strPorts + subPorts[i];
            else strPorts = strPorts +subPorts[i] + "|";
        }
        strPorts = strPorts + "\\s+\\w*\\(((\\w+\\s*,\\s*)*(\\w+\\s*))\\)";

        
        Pattern subP = Pattern.compile(strPorts);
        System.out.println("子模块端口正则: " +  subP);
        Matcher subM = subP.matcher(str);
        while(subM.find()){
            String res = subM.group(1);
            String []result = res.split(",");
            System.out.println("Found 调用子模块端口: " +  res);
            for(int i=0; i<result.length; i++){
                Port port = new Port();
                port.setName(result[i].trim());
                map.put(result[i].trim(), port);
            }
        }
        
        // 获取激励文件端口
        //激励文件端口正则表达式
        Pattern r = Pattern.compile("(reg|wire)\\s*(\\[.*\\])\\s*((\\w+\\s*,\\s*)*(\\w+\\s*));");
        // 端口位宽正则表达式
        Pattern p = Pattern.compile("(\\d{1,2})\\s*:\\s*0");
        Matcher m = r.matcher(str);
        while (m.find()) {
            int width;
            String res = m.group(0);
            String type = m.group(1);
            String widths = m.group(2);
            String names = m.group(3);
            String[] namesArr = names.split(",");
            System.out.println("Found 全部: " +  res);
            System.out.println("Found 类型: " +  type);
            System.out.println("Found 位宽: " +  widths);
            System.out.println("Found 端口名: " +  names);
            if(widths != null){
                Matcher result = p.matcher(widths);
                if(result.find()) width = Integer.parseInt(result.group(1))+1;
                else throw new ExeException(Code.PORT_FORMAT_WRONG, "端口位宽格式错误");
            }else width = 1;
            for (int i=0; i<namesArr.length; i++){
                Port port = new Port();
                port.setName(namesArr[i].trim());
                port.setWidth(width);
                port.setType(type);
                map.put(namesArr[i], port);
            }
        }
        System.out.println("MATCH OVER");
        if(map.size() == 0) throw new ExeException(Code.PORT_FORMAT_WRONG, "端口位宽格式错误");
        return map;
    }
    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public String getTbName() {
        return tbName;
    }

    public void setTbName(String tbName) {
        this.tbName = tbName;
    }
}
