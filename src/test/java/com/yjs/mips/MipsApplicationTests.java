package com.yjs.mips;

import com.yjs.mips.pojo.AnswerStatus;
import com.yjs.mips.service.IStudentService;
import com.yjs.mips.service.IQuestionService;
import mars.MarsLaunch;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MipsApplication.class)
public class MipsApplicationTests {
    @Autowired
    IQuestionService teacherService;
    @Autowired
    IStudentService studentService;

    @Test
    public void run2() {
        String content = "[1, 2, 3]";
        String[] values = content.substring(1, content.length() - 1).split(", ");
        System.out.println(Arrays.toString(values));
    }

    @Test
    public void run3() {
        String fileName = "F:\\graduation_project\\MIPS_programs\\mips1.asm";
        String[] regs = {"$t1", "$s4"};
        String[] mems = {"0X10010000"};
        AnswerStatus status = new AnswerStatus();
    }

    @Test
    public void run4() {
        String command = "t1 s4 0x10010000-0x10010010 F:\\graduation_project\\MIPS_programs\\mips1.asm";
        new MarsLaunch(command.split(" "));
    }
}
