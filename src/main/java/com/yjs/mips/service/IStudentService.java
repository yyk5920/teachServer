package com.yjs.mips.service;

import com.yjs.mips.pojo.AnswerStatus;

public interface IStudentService {
    AnswerStatus assemble(Long student_id, Long questionId, String filePath);
    AnswerStatus assembleText(Long student_id, Long questionId, String text);
}
