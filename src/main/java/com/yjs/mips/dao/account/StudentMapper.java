package com.yjs.mips.dao.account;

import com.yjs.mips.pojo.account.Student;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@Mapper
public interface StudentMapper {
    public Student getStudent(String stu_id);
    public void setStudentToken(Student student);
    public List<String> getStudentAll();
//    public MipsExperiment getExperiment(Long id);
}
