package com.yjs.mips.service;

import com.yjs.mips.pojo.AddStatus;
import com.yjs.mips.pojo.MipsQuestion;

public interface IQuestionService {
    void addQuestion(MipsQuestion question, AddStatus addStatus);
}
