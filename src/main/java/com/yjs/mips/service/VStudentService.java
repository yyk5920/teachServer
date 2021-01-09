package com.yjs.mips.service;

import com.yjs.mips.pojo.mips_cpu_design.StuAssExeStatus;

public interface VStudentService {
    StuAssExeStatus run(String student_id, Long assignment_id, Long question_id, String filePath) throws Exception;
}
