package com.yjs.mips;

import cn.shuibo.annotation.EnableSecurity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
//@EnableSecurity
public class MipsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MipsApplication.class, args);
    }

}
