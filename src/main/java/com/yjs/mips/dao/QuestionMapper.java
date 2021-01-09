package com.yjs.mips.dao;

import com.yjs.mips.pojo.MipsMemory;
import com.yjs.mips.pojo.MipsRegister;
import com.yjs.mips.pojo.MipsStack;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface QuestionMapper {
    public void createInitMemory(MipsMemory memory);
    public void createInitRegister(MipsRegister register);
    public List<MipsRegister> findInitRegisters(Long id);
    public List<MipsMemory> findInitMemories(Long id);
    public void createResMemory(MipsMemory memory);
    public void createResRegister(MipsRegister register);
    public List<MipsRegister> findResRegisters(Long id);
    public List<MipsMemory> findResMemories(Long id);
    public void createStack(MipsStack stack);
    public MipsStack findStack(Long id);
}
