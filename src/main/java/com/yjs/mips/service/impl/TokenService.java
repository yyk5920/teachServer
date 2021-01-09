package com.yjs.mips.service.impl;

import com.yjs.mips.pojo.account.Student;

public interface TokenService {
    String getToken(Student student);
}
