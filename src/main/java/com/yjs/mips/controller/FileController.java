package com.yjs.mips.controller;

import com.yjs.mips.pojo.account.Student;
import com.yjs.mips.pojo.requestInfo.RequestInfo;
import com.yjs.mips.util.Result;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class FileController {
    @RequestMapping("/verilog/uploadImage")
    @ResponseBody
    public Result uploadImage(@RequestParam(value = "file") MultipartFile file, Model model, HttpServletRequest request){
        System.out.println("verilog/uploadImage");
        if (file.isEmpty()) {
            System.out.println("文件为空空");
        }
        String fileName = file.getOriginalFilename();  // 文件名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
        String filePath = "D://temp-rainy//"; // 上传后的路径
        fileName = UUID.randomUUID() + suffixName; // 新文件名
        File dest = new File(filePath + fileName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String filename = "/temp-rainy/" + fileName;
        model.addAttribute("filename", filename);

        return Result.succ(20000, "file", null, null);
    }


//    @RequestMapping("/verilog/downloadImage")
//    @ResponseBody
//    public Result downloadImage(@RequestBody Map<String, Object> map){
//        System.out.println("user/login");
//
//        return Result.succ(20000, "登录成功", null, data);
//    }

}
