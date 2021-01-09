package com.yjs.mips.service.impl;

import com.yjs.mips.dao.AnswerMapper;
import com.yjs.mips.dao.QuestionMapper;
import com.yjs.mips.pojo.*;
import com.yjs.mips.service.IStudentService;
import mars.MyMars;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.io.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class StudentServiceImpl implements IStudentService {

    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    AnswerMapper answerMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public AnswerStatus assemble(Long student_id, Long questionId, String filePath) {
        String tempPath = init(questionId, filePath);

        // 初始化完成后执行
        MipsAnswer answer = new MipsAnswer();

        // 获取正确答案
        List<MipsMemory> targetMems = questionMapper.findResMemories(questionId);
        List<MipsRegister> targetRegs = questionMapper.findResRegisters(questionId);

        MyMars mars = new MyMars(); // 运行结束后得到运行结果寄存器和内存
        AnswerStatus status = mars.compile(tempPath, targetRegs, targetMems);
        boolean runError = false;

        if (status.getInfo() != null)
            runError = true;

        // 如果运行没有出错，则判断是否结果正确
        if (!runError) {
            Map<String, Integer> memMap = new HashMap<>();
            Map<String, Integer> regMap = new HashMap<>();
            for (MipsMemory targetMem : targetMems)
                memMap.put(targetMem.getAddress(), targetMem.getValue());
            for (MipsRegister targetReg : targetRegs)
                regMap.put(targetReg.getName(), targetReg.getValue());
            StringBuilder errorSb = new StringBuilder();
            for (Map<String, Integer> mem : status.getMem()) { // 获取内存运行结果
                Iterator it = mem.keySet().iterator();
                String address = (String) it.next();
                if (!mem.get(address).equals(memMap.get(address))) {
                    errorSb.append("Your result in address " + address +
                            " is not equal to the true answer: " + memMap.get(address));
                    errorSb.append("\n");
                }
            }
            for (Map<String, Integer> reg : status.getReg()) { // 获取寄存器运行结果
                Iterator it = reg.keySet().iterator();
                String name = (String) it.next();
                if (!reg.get(name).equals(regMap.get(name))) {
                    errorSb.append("Your result in register " + name +
                            " is not equal to the true answer: " + regMap.get(name));
                    errorSb.append("\n");
                }
            }

            if (errorSb.length() == 0) { // 如果没有出错
                status.setCorrect(true);
                status.setInfo("Your answer is true!");
            } else {
                status.setCorrect(false);
                status.setInfo(errorSb.toString());
            }
        }

        // 设置入库answer
        answer.setStudentId(student_id);
        answer.setQuestionId(questionId);
        answer.setDatetime(new Timestamp(new Date().getTime()));
        answer.setCorrect(status.isCorrect());
        answer.setInfo(status.getInfo());
        // 入库
        answerMapper.createAnswer(answer);
        Long id = answer.getId();
        for (Map<String, Integer> reg : status.getReg()) { // 获取寄存器运行结果
            MipsRegister register = new MipsRegister();
            register.setId(id);
            Iterator it = reg.keySet().iterator();
            String name = (String) it.next();
            register.setName(name);
            register.setValue(reg.get(name));
            answerMapper.createReg(register);
        }

        for (Map<String, Integer> reg : status.getMem()) { // 获取寄存器运行结果
            MipsMemory memory = new MipsMemory();
            memory.setId(id);
            Iterator it = reg.keySet().iterator();
            String address = (String) it.next();
            memory.setAddress(address);
            memory.setValue(reg.get(address));
            answerMapper.createMem(memory);
        }

        // 用于restful接口
        List<Map<String, Integer>> statusMems = new LinkedList<>();
        List<Map<String, Integer>> statusRegs = new LinkedList<>();

        for (MipsMemory targetMem : targetMems) {
            Map<String, Integer> map = new HashMap<>();
            map.put(targetMem.getAddress(), targetMem.getValue());
            statusMems.add(map);
        }

        for (MipsRegister targetReg : targetRegs) {
            Map<String, Integer> map = new HashMap<>();
            map.put(targetReg.getName(), targetReg.getValue());
            statusRegs.add(map);
        }

        status.setTargetMem(statusMems);
        status.setTargetReg(statusRegs);

        return status;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public AnswerStatus assembleText(Long student_id, Long questionId, String text) {
        String tempPath = initText(questionId, text);

        // 初始化完成后执行
        MipsAnswer answer = new MipsAnswer();

        // 获取正确答案
        List<MipsMemory> targetMems = questionMapper.findResMemories(questionId);
        List<MipsRegister> targetRegs = questionMapper.findResRegisters(questionId);

        MyMars mars = new MyMars(); // 运行结束后得到运行结果寄存器和内存
        AnswerStatus status = mars.compile(tempPath, targetRegs, targetMems);
        boolean runError = false;

        if (status.getInfo() != null)
            runError = true;

        // 如果运行没有出错，则判断是否结果正确
        if (!runError) {
            Map<String, Integer> memMap = new HashMap<>();
            Map<String, Integer> regMap = new HashMap<>();
            for (MipsMemory targetMem : targetMems)
                memMap.put(targetMem.getAddress(), targetMem.getValue());
            for (MipsRegister targetReg : targetRegs)
                regMap.put(targetReg.getName(), targetReg.getValue());
            StringBuilder errorSb = new StringBuilder();
            for (Map<String, Integer> mem : status.getMem()) { // 获取内存运行结果
                Iterator it = mem.keySet().iterator();
                String address = (String) it.next();
                if (!mem.get(address).equals(memMap.get(address))) {
                    errorSb.append("Your result in address " + address +
                            " is not equal to the true answer: " + memMap.get(address));
                    errorSb.append("\n");
                }
            }
            for (Map<String, Integer> reg : status.getReg()) { // 获取寄存器运行结果
                Iterator it = reg.keySet().iterator();
                String name = (String) it.next();
                if (!reg.get(name).equals(regMap.get(name))) {
                    errorSb.append("Your result in register " + name +
                            " is not equal to the true answer: " + regMap.get(name));
                    errorSb.append("\n");
                }
            }

            if (errorSb.length() == 0) { // 如果没有出错
                status.setCorrect(true);
                status.setInfo("Your answer is true!");
            } else {
                status.setCorrect(false);
                status.setInfo(errorSb.toString());
            }
        }

        // 设置入库answer
        answer.setStudentId(student_id);
        answer.setQuestionId(questionId);
        answer.setDatetime(new Timestamp(new Date().getTime()));
        answer.setCorrect(status.isCorrect());
        answer.setInfo(status.getInfo());
        // 入库
        answerMapper.createAnswer(answer);
        Long id = answer.getId();
        for (Map<String, Integer> reg : status.getReg()) { // 获取寄存器运行结果
            MipsRegister register = new MipsRegister();
            register.setId(id);
            Iterator it = reg.keySet().iterator();
            String name = (String) it.next();
            register.setName(name);
            register.setValue(reg.get(name));
            answerMapper.createReg(register);
        }

        for (Map<String, Integer> reg : status.getMem()) { // 获取寄存器运行结果
            MipsMemory memory = new MipsMemory();
            memory.setId(id);
            Iterator it = reg.keySet().iterator();
            String address = (String) it.next();
            memory.setAddress(address);
            memory.setValue(reg.get(address));
            answerMapper.createMem(memory);
        }

        // 用于restful接口
        List<Map<String, Integer>> statusMems = new LinkedList<>();
        List<Map<String, Integer>> statusRegs = new LinkedList<>();

        for (MipsMemory targetMem : targetMems) {
            Map<String, Integer> map = new HashMap<>();
            map.put(targetMem.getAddress(), targetMem.getValue());
            statusMems.add(map);
        }

        for (MipsRegister targetReg : targetRegs) {
            Map<String, Integer> map = new HashMap<>();
            map.put(targetReg.getName(), targetReg.getValue());
            statusRegs.add(map);
        }

        status.setTargetMem(statusMems);
        status.setTargetReg(statusRegs);

        return status;
    }

    private String init(Long questionId, String filePath) {

        List<MipsMemory> memories = questionMapper.findInitMemories(questionId);
        List<MipsRegister> registers = questionMapper.findInitRegisters(questionId);
        MipsStack stack = questionMapper.findStack(questionId);
        String[] initStrs = initInstruction(memories, registers, stack);

        File file = new File("tempcode");
        if(!file.exists()){//如果文件夹不存在
            file.mkdir();//创建文件夹
        }

        // 答题时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        Timestamp timestamp = new Timestamp(new Date().getTime());
        String datetime = sdf.format(timestamp);

        try {
            // 最终要执行的文件路径
            String tempPath = "tempcode/temp" + datetime + ".asm";
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tempPath)));

            for (String str : initStrs) {
                bufferedWriter.write(str, 0, str.length());
                bufferedWriter.newLine();
            }
            bufferedWriter.write("# initial operation completed");
            bufferedWriter.newLine();

            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                bufferedWriter.write(line, 0, line.length());
                bufferedWriter.newLine();
            }

            bufferedReader.close();
            bufferedWriter.close();
            return tempPath;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    private String initText(Long questionId, String text) {

        List<MipsMemory> memories = questionMapper.findInitMemories(questionId);
        List<MipsRegister> registers = questionMapper.findInitRegisters(questionId);
        MipsStack stack = questionMapper.findStack(questionId);
        String[] initStrs = initInstruction(memories, registers, stack);

        File file = new File("tempcode");
        if(!file.exists()){//如果文件夹不存在
            file.mkdir();//创建文件夹
        }

        // 答题时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        Timestamp timestamp = new Timestamp(new Date().getTime());
        String datetime = sdf.format(timestamp);

        try {
            // 最终要执行的文件路径
            String tempPath = "tempcode/temp" + datetime + ".asm";
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tempPath)));

            for (String str : initStrs) {
                bufferedWriter.write(str, 0, str.length());
                bufferedWriter.newLine();
            }
            bufferedWriter.write("# initial operation completed");
            bufferedWriter.newLine();

            String[] lines = text.split("\r\n");

            for (String line : lines) {
                bufferedWriter.write(line, 0, line.length());
                bufferedWriter.newLine();
            }

            bufferedWriter.close();
            return tempPath;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    // 生成初始化命令
    private String[] initInstruction(List<MipsMemory> memories, List<MipsRegister> registers, MipsStack stack) {
        List<String> strs = new LinkedList<>();
        if (!memories.isEmpty()) { // 如果要初始化内存，则进行初始化
            strs.add("# init memory");
            for (MipsMemory memory : memories) {
                String address = memory.getAddress();
                strs.addAll(setMem(address, memory.getValue()));
            }
            // 复原
            strs.add("addi $t0, $zero, 0");
            strs.add("addi $t1, $zero, 0");
        }

        if (!registers.isEmpty()) {
            strs.add("# init register");
            for (MipsRegister register : registers) {
                String name = register.getName();
                strs.addAll(setReg(name, register.getValue()));
            }
        }

        if (stack != null) {
            // content的顺序为入栈顺序
            strs.add("# init stack");
            strs.addAll(setStack(stack.getSize(), stack.getContent()));
        }
        String[] instructions = new String[strs.size()];
        strs.toArray(instructions);
        return instructions;
    }

    // 生成初始化内存的命令
    private List<String> setMem(String address, int value) {
        List<String> result = new LinkedList<>();

        address = address.substring(2);
        // 高16位
        String hex_num = address.substring(0, 4);
        int dec_num = Integer.parseInt(hex_num, 16);
        result.add("lui $t0, " + dec_num);
        // 低16位
        hex_num = address.substring(4);
        dec_num = Integer.parseInt(hex_num, 16);
        result.add("ori $t0, $t0, " + dec_num);
        // 赋值到寄存器
        result.add("lui $t1, " + (value >>> 16));
        result.add("ori $t1, $t1, " + (value % 65536));
        // 存储到内存
        result.add("sw $t1, 0($t0)");

        return result;
    }

    // 生成初始化寄存器的命令
    private List<String> setReg(String name, int value) {
        List<String> result = new LinkedList<>();
        result.add("lui " + name + ", " + (value >>> 16));
        result.add("ori " + name + ", " + name + ", " + (value % 65536));
        return result;
    }

    // 生成初始化栈命令
    private List<String> setStack(int size, String content) {
        List<String> result = new LinkedList<>();
        String[] values = content.substring(1, content.length() - 1).split(", ");
        result.add("add $sp, $sp, -" + (4 * size));
        int offset = (size - 1) * 4;
        for (int i = 0; i < size; i++) {
            result.add("lui $t0, " + (Integer.parseInt(values[i]) >>> 16));
            result.add("ori $t0, $t0, " + (Integer.parseInt(values[i]) % 65536));
//            result.add("addi $t0, $zero, " + Integer.parseInt(values[i]));
            result.add("sw $t0, " + offset + "($sp)");
            offset -= 4;
        }
        result.add("addi $t0, $zero, 0");
        return result;
    }
}
