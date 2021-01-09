package com.yjs.mips.service.impl.account;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.yjs.mips.dao.account.StudentMapper;
import com.yjs.mips.pojo.account.Student;
import com.yjs.mips.service.account.StuAccountService;
import com.yjs.mips.service.impl.TokenService;
import com.yjs.mips.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static com.yjs.mips.util.RSAUtils.*;

@Service
public class StuAccountServiceImpl implements StuAccountService {
    @Autowired
    StudentMapper studentMapper;
    @Autowired
    TokenService tokenService;

//    @Autowired
//    private JwtProperties jwtProperties;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Student getStudentById(String stu_id){
        Student student = studentMapper.getStudent(stu_id);
        return student;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Student checkLogin(Map<String, Object> map){

//        KeyPair keypair = null;
//        try {
//            keypair = genKeyPair(1024);// 公钥私钥对
//        } catch (Exception e) {
////            LogUtils.error("生成公钥私钥发生异常：" + e.toString());
//        }
//        PublicKey publickey = keypair.getPublic();// 公钥
//        PrivateKey privatekey = keypair.getPrivate();// 私钥
//
//        // base64编码后的公钥（一般从文件id_ras.pub中读取）
//        String base64PublickeyStr = Base64.encodeBase64String(publickey.getEncoded());
//        // base64编码后的私钥（一般从文件id_rsa中读取）
//        String base64PrivatekeyStr = Base64.encodeBase64String(privatekey.getEncoded());
//        System.out.print("base64 pub：");
//        System.out.println(base64PublickeyStr);
//        System.out.print("base64 pri：");
//        System.out.println(base64PrivatekeyStr);
        String stu_id = (String)map.get("username");//学号
        String password = (String)map.get("password");//密码

        String priKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKzg4y6ClhCg0DWnqM213pm2lkQdN188sgIvT1zU+SM4yAykcJ4u11+Q5ycw0EFcW8fYHZspOtdIpEBPawb/N1J1Kj9fhudccf0NtjVu28kbI/yToRgzmQFzkB7fyMsp2LRZAT50I60cj9sIDTZxpMNXLXAf2m5GSvZ/hZ0s5y65AgMBAAECgYAxDHUoYwB0o0uvjWwFQU5wyi8EEE3/hVbz67xWmE4/QgLwI0+Yca3FfXU310+e27dmkdiEh0QchI2leWLiT1LAZ21MWATO43IuVfYyEPhOyIJ8Upf+nPFvNDTMoobkQHEMvdBcuwP6+YczccILrS/cvhd1SHN26cGHiWJ3Y9WUaQJBAPd9pR36tIp3g4NByU3vYsWyG0sZgLR9kU9AxaShMd0KVas/9ZxMfl+v28w+/BL2BT+JaQBBT0qUiNTVsG+MLDcCQQCy0oXAVX//3aXYCxH14UBLrgG0oXVzC72a2fO3StyGJSx/Jjkg9zv9QE3AnhpwXe0Rqada9HdGDZa8zkIt02SPAkEAqGM2atVe7S8vraie/IW5oGT0KP6rESK2MUQStVZvhMM5UpDr3XI2MYiHdcoM3CHDpF0rsd69wjKZfqX9q4eltQJAH6YQ1ASN3ewNoRn8eMcACSTn2l9FGTJ3wUNX1hXix78mi+5o6AO5002hNTkGNuL4xUqAeyGPRo+7IPlkTxX90wJAKm/ZQicStFBYYJ9pLENRfTAceO6J4/nGYxNvq5u3b2BRTy/76Yc1oKzdjYgX5tD8MhVriAoihtI/wobkh0Hdbw==";

//        String content = "{'name':'mmmirana','age':'27','phone':'17112345678'}";
        // RSA加密
//        String encryptedContent = rsaEncrypt(content, base64PublickeyStr);
//        String encryptedContent = rsaEncrypt(content, pubKey);
//        System.out.print("content encode:");
//        System.out.println(encryptedContent);
        // RSA解密
//        String msg = rsaDecrypt(encryptedContent, base64PrivatekeyStr);
//        String msg = rsaDecrypt(encryptedContent, priKey);
//        System.out.print("content decode:");
//        System.out.println(msg);
//        System.out.print("password encode:");
//        System.out.println(str);

        String pswDecode = rsaDecrypt(password, priKey);
//        System.out.print("password decode:");
//        System.out.println(pswDecode);
        Student student = studentMapper.getStudent(stu_id);
        if(student == null) return null;
        String passwordDatabase = student.getStu_password();
//        System.out.print("password database:");
//        System.out.println(passwordDatabase);
        if(pswDecode.equals(passwordDatabase)){
            String token = tokenService.getToken(student);
            student.setToken(token);
            studentMapper.setStudentToken(student);
            return student;
        }
        else{
            return null;
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Student getStudentInfo(Map<String, Object> map){
        String token = (String)map.get("token");
        DecodedJWT jwt = JWT.decode(token);
        String stu_id = jwt.getClaim("stu_id").asString();
        Student student = studentMapper.getStudent(stu_id);
        return student;
    }
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public List<String> getStudentAll(){
        List<String> students = studentMapper.getStudentAll();
        return students;
    }
}
