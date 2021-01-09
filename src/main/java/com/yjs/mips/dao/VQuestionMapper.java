package com.yjs.mips.dao;

import com.yjs.mips.pojo.VQuestion;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface VQuestionMapper {
    public void createQuestion(VQuestion question);
    public List<VQuestion> findQuestion(Long id);
}
