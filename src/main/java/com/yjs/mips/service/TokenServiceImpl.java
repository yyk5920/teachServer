package com.yjs.mips.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.yjs.mips.pojo.account.Student;
import com.yjs.mips.service.impl.TokenService;
import org.springframework.stereotype.Service;


@Service
public class TokenServiceImpl implements TokenService {
    public String getToken(Student student) {
        String token="";
        token= JWT.create()
                .withAudience(student.getStu_id())// 将 user id 保存到 token 里面
                .withClaim("stu_id",student.getStu_id())
                .sign(Algorithm.HMAC256(student.getStu_password()));// 以 password 作为 token 的密钥
        return token;
    }
}
