package com.yjs.mips.controller;

import com.yjs.mips.pojo.*;
import com.yjs.mips.pojo.mips_cpu_design.MipsExperiment;
import com.yjs.mips.pojo.mips_cpu_design.StuAssExeStatus;
import com.yjs.mips.pojo.mips_cpu_design.StuAssStatus;
import com.yjs.mips.service.IQuestionService;
import com.yjs.mips.service.IStudentService;
import com.yjs.mips.service.MipsExperimentService;
import com.yjs.mips.service.VStudentService;
import com.yjs.mips.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api")
public class MipsQuestionController {

    @Autowired
    IQuestionService questionService;
    @Autowired
    IStudentService studentService;
    @Autowired
    VStudentService vStudentService;
    @Autowired
    MipsExperimentService mipsExperimentService;

    @RequestMapping("/mips-add")
    @ResponseBody
    public AddStatus addQuestion(@RequestBody Map<String, Object> map) {
        AddStatus addStatus = new AddStatus();
        MipsQuestion question = new MipsQuestion();

        String questionId = (String) map.get("questionId");

        List<Map<String, String>> regsInit = (List<Map<String, String>>) ((Map<String, Object>) map.get("init")).get("reg");
        List<Map<String, String>> memsInit = (List<Map<String, String>>) ((Map<String, Object>) map.get("init")).get("mem");
        Map<String, Object> stackInit = (Map<String, Object>) map.get("stack");
        List<Map<String, String>> regsRes = (List<Map<String, String>>) ((Map<String, Object>) map.get("result")).get("reg");
        List<Map<String, String>> memsRes = (List<Map<String, String>>) ((Map<String, Object>) map.get("result")).get("mem");

        question.setQuestionId(questionId);
        question.setRegsInit(regsInit);
        question.setMemsInit(memsInit);
        question.setStack(stackInit);
        question.setRegsRes(regsRes);
        question.setMemsRes(memsRes);

        questionService.addQuestion(question, addStatus);

        return addStatus;
    }

    @RequestMapping("/mips-file")
    @ResponseBody
    public AnswerStatus answerQuestion(@RequestBody Map<String, Object> map) {
        Long studentId = Long.parseLong((String) map.get("studentId"));
        Long questionId = Long.parseLong((String) map.get("questionId"));
        String filePath = (String) map.get("path");

        AnswerStatus status = studentService.assemble(studentId, questionId, filePath);

        return status;
    }

    @RequestMapping("/mips-text")
    @ResponseBody
    public AnswerStatus answerQuestionText(@RequestBody Map<String, Object> map) {
        Long studentId = Long.parseLong((String) map.get("studentId"));
        Long questionId = Long.parseLong((String) map.get("questionId"));
        String text = (String) map.get("content");
        AnswerStatus status = studentService.assembleText(studentId, questionId, text);
        return status;
    }

}
