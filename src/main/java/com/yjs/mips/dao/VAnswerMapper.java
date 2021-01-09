package com.yjs.mips.dao;

import com.yjs.mips.pojo.VAnswer;
import com.yjs.mips.pojo.VAnswerStatus;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface VAnswerMapper {
    public void createAnswer(VAnswer answer);
    public List<VAnswer> findAnswer(Long id);
    public void createAnswerStatus(VAnswerStatus answerStatus);
}
