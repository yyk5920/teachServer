package com.yjs.mips.dao;

import com.yjs.mips.pojo.MipsAnswer;
import com.yjs.mips.pojo.MipsMemory;
import com.yjs.mips.pojo.MipsRegister;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface AnswerMapper {
    void createAnswer(MipsAnswer answer);
    void createReg(MipsRegister reg);
    void createMem(MipsMemory mem);
    List<MipsRegister> findRegs(Long id);
    List<MipsMemory> findMems(Long id);
}
