package com.yjs.mips.service.impl;

import com.yjs.mips.dao.account.StudentMapper;
import com.yjs.mips.dao.mips_cpu_design.MipsExperimentMapper;
import com.yjs.mips.dao.studentAssStatus.StuAssExeStatusMapper;
import com.yjs.mips.pojo.account.Student;
import com.yjs.mips.pojo.mips_cpu_design.MipsExperiment;
import com.yjs.mips.pojo.mips_cpu_design.StuAssExeStatus;
import com.yjs.mips.pojo.mips_cpu_design.StuAssScore;
import com.yjs.mips.service.MipsExperimentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class MipsExperimentServiceImpl implements MipsExperimentService {
    @Autowired
    MipsExperimentMapper mipsExperimentMapper;
    @Autowired
    StuAssExeStatusMapper stuAssExeStatusMapper;
    @Autowired
    StudentMapper studentMapper;


    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public List<Map<String, Object>> getExperimentListStatus(Map<String, Object> map) {
        String stu_id = (String)map.get("username");
        try {
            List<Map<String, Object>> titles = mipsExperimentMapper.getExperimentList();
            for(int i=0; i<titles.size();i++){
                Map<String, Object>  title = titles.get(i);
                int module_id = (int)title.get("module_id");
                long ass_id = 1;////////////////////////////////////////////////////////////////////////////TODO
                int score = mipsExperimentMapper.getExeScore(stu_id, ass_id,(long)module_id);
                title.put("exe_score",score);
            }
            return titles;
        } catch (Exception e) {
            System.out.println("bug");
            throw e;
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public List<Map<String, Object>>  getExperimentList() {
        try {
            List<Map<String, Object>>  titles = mipsExperimentMapper.getExperimentList();
            System.out.println("getExperimentList");
            System.out.println(titles);
            return titles;
        } catch (Exception e) {
            System.out.println("bug");
            throw e;
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public MipsExperiment getExperiment(Long module_id) {
        try {
            MipsExperiment mipsExperiment =  mipsExperimentMapper.getExperiment(module_id);
            return mipsExperiment;
        } catch (Exception e) {
            System.out.println("获取MipsExperiment bug");
            throw e;
        }


    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public List<Map<String, Object>> getStuAssScoreById(String stu_id) {
        try {
            List<Map<String, Object>> lists = mipsExperimentMapper.getStuAssScoreById(stu_id);
            return lists;
        } catch (Exception e) {
            System.out.println("getStuAssScoreById bug");
            throw e;
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public void setStuAssScore(){
        try {
            List<String> students = studentMapper.getStudentAll();
            for(int i=0; i<students.size();i++){
                String stu_id = students.get(i);
                if(stu_id == null) break;
                List<Map<String, Object>> lists = mipsExperimentMapper.getStuAssScoreById(stu_id);
                StuAssScore stuAssScore = new StuAssScore();
                int score_total = 0;
                int count_total = 0;
                System.out.println(stu_id);
                if(lists.size() == 0){
                    stuAssScore.setStu_id(stu_id);
                    stuAssScore.setAss_id((long)1);
                    stuAssScore.setCount1(0);
                    stuAssScore.setScore1(0);
                    stuAssScore.setCount2(0);
                    stuAssScore.setScore2(0);
                    stuAssScore.setCount3(0);
                    stuAssScore.setScore3(0);
                    stuAssScore.setCount4(0);
                    stuAssScore.setScore4(0);
                    stuAssScore.setCount5(0);
                    stuAssScore.setScore5(0);
                    stuAssScore.setCount6(0);
                    stuAssScore.setScore6(0);
                    stuAssScore.setCount7(0);
                    stuAssScore.setScore7(0);
                    stuAssScore.setCount8(0);
                    stuAssScore.setScore8(0);
                    stuAssScore.setCount9(0);
                    stuAssScore.setScore9(0);
                    stuAssScore.setCount10(0);
                    stuAssScore.setScore10(0);
                    stuAssScore.setCount11(0);
                    stuAssScore.setScore11(0);
                    stuAssScore.setCount12(0);
                    stuAssScore.setScore12(0);
                    stuAssScore.setCount13(0);
                    stuAssScore.setScore13(0);
                    stuAssScore.setCount14(0);
                    stuAssScore.setScore14(0);
                    stuAssScore.setCount15(0);
                    stuAssScore.setScore15(0);
                    stuAssScore.setCount16(0);
                    stuAssScore.setScore16(0);
                    stuAssScore.setCount17(0);
                    stuAssScore.setScore17(0);
                    stuAssScore.setCount18(0);
                    stuAssScore.setScore18(0);
                    stuAssScore.setCount19(0);
                    stuAssScore.setScore19(0);
                    stuAssScore.setCount20(0);
                    stuAssScore.setScore20(0);
                    stuAssScore.setCount21(0);
                    stuAssScore.setScore21(0);
                }
           for(int j=0; j<lists.size(); j++){
                    stuAssScore.setStu_id(stu_id);
                    stuAssScore.setAss_id((long)1);
                    Map<String, Object> map = lists.get(j);
                    int exe_id = (int)map.get("exe_id");
                    int score = (int)map.get("exe_score");
                    int count = (int)map.get("commit_count");
                    if(score != 0){
                        if(count == 0) count = 1;
                    }
                    score_total += score;
                    count_total += count;
                    if(exe_id == 1){
                        stuAssScore.setCount1(count);
                        stuAssScore.setScore1(score);
                    }else if(exe_id == 2){
                        stuAssScore.setCount2(count);
                        stuAssScore.setScore2(score);
                    }else if(exe_id == 3){
                        stuAssScore.setCount3(count);
                        stuAssScore.setScore3(score);
                    }else if(exe_id == 4){
                        stuAssScore.setCount4(count);
                        stuAssScore.setScore4(score);
                    }else if(exe_id == 5){
                        stuAssScore.setCount5(count);
                        stuAssScore.setScore5(score);
                    }else if(exe_id == 6){
                        stuAssScore.setCount6(count);
                        stuAssScore.setScore6(score);
                    }else if(exe_id == 7){
                        stuAssScore.setCount7(count);
                        stuAssScore.setScore7(score);
                    }else if(exe_id == 8){
                        stuAssScore.setCount8(count);
                        stuAssScore.setScore8(score);
                    }else if(exe_id == 9){
                        stuAssScore.setCount9(count);
                        stuAssScore.setScore9(score);
                    }else if(exe_id == 10){
                        stuAssScore.setCount10(count);
                        stuAssScore.setScore10(score);
                    }else if(exe_id == 11){
                        stuAssScore.setCount11(count);
                        stuAssScore.setScore11(score);
                    }else if(exe_id == 12){
                        stuAssScore.setCount12(count);
                        stuAssScore.setScore12(score);
                    }else if(exe_id == 13){
                        stuAssScore.setCount13(count);
                        stuAssScore.setScore13(score);
                    }else if(exe_id == 14){
                        stuAssScore.setCount14(count);
                        stuAssScore.setScore14(score);
                    }else if(exe_id == 15){
                        stuAssScore.setCount15(count);
                        stuAssScore.setScore15(score);
                    }else if(exe_id == 16 ){
                        stuAssScore.setCount16(count);
                        stuAssScore.setScore16(score);
                    }else if(exe_id ==17){
                        stuAssScore.setCount17(count);
                        stuAssScore.setScore17(score);
                    }else if(exe_id ==18){
                        stuAssScore.setCount18(count);
                        stuAssScore.setScore18(score);
                    }else if(exe_id == 19){
                        stuAssScore.setCount19(count);
                        stuAssScore.setScore19(score);
                    }else if(exe_id == 20){
                        stuAssScore.setCount20(count);
                        stuAssScore.setScore20(score);
                    }else{
                        stuAssScore.setCount21(count);
                        stuAssScore.setScore21(score);
                    }
                }
                stuAssScore.setCount_total(count_total);
                stuAssScore.setScore_total(score_total);
                mipsExperimentMapper.setStuAssScore(stuAssScore);
            }

        } catch (Exception e) {
            System.out.println("setStuAssScorebug");
            throw e;
        }

    }
}
