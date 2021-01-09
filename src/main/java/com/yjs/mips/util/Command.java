package com.yjs.mips.util;

/*
api原文：ProcessBuilder.start() 和 Runtime.exec 方法创建一个本机进程，并返回 Process 子类的一个实例，该实例可用来控制进程并获得相关信息。Process 类提供了执行从进程输入、执行输出到进程、等待进程完成、检查进程的退出状态以及销毁（杀掉）进程的方法。

创建进程的方法可能无法针对某些本机平台上的特定进程很好地工作，比如，本机窗口进程，守护进程，Microsoft Windows 上的 Win16/DOS 进程，或者 shell 脚本。
创建的子进程没有自己的终端或控制台。它的所有标准 io（即 stdin、stdout 和 stderr）操作都将通过三个流 (getOutputStream()、getInputStream() 和 getErrorStream()) 重定向到父进程。父进程使用这些流来提供到子进程的输入和获得从子进程的输出。
因为有些本机平台仅针对标准输入和输出流提供有限的缓冲区大小，如果读写子进程的输出流或输入流迅速出现失败，则可能导致子进程阻塞，甚至产生死锁。

也就是说：如果程序不断在向标准输出流和标准错误流写数据，而JVM不读取的话，当缓冲区满之后将无法继续写入数据，最终造成阻塞在waitFor()这里。
因此，需要下面这个类来创建两个线程在waitFor()命令之前读出窗口的标准输出缓冲区和标准错误流的内容。
 */
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Command {
    // 保存进程的输出流信息
    private List<String> stdoutList = new ArrayList<String>();
    // 保存进程的错误流信息
    private List<String> erroroutList = new ArrayList<String>();

    public void executeCommand(String command, String path ,String sys) {
        // 先清空输出流和错误流
        stdoutList.clear();
        erroroutList.clear();
        System.out.print("进入执行脚本文件方法");
        Process p = null;

        try {
            if(sys.equals("WINDOWS")){
                System.out.println("脚本内容：");
                System.out.println(command);
                p = Runtime.getRuntime().exec(command);
            }
            else{
                System.out.print("  Linux 脚本执行路径：");
                System.out.println(path);
                File file = new File(path);
                p = Runtime.getRuntime().exec(command, null, file);
            }
            // 创建2个线程，分别读取输出流缓冲区和错误流缓冲区
            ThreadUtil stdoutUtil = new ThreadUtil(p.getInputStream(), stdoutList);
            ThreadUtil erroroutUtil = new ThreadUtil(p.getErrorStream(), erroroutList);
            //启动线程读取缓冲区数据
            stdoutUtil.start();
            erroroutUtil.start();
            int result = p.waitFor();

            System.out.print("  Linux 脚本执行结果：");
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public List<String> getStdoutList() {
        return stdoutList;
    }

    public List<String> getErroroutList() {
        return erroroutList;
    }

}

class ThreadUtil implements Runnable {
    // 设置读取的字符编码
    private String character = "GB2312";
    private List<String> list;
    private InputStream inputStream;

    public ThreadUtil(InputStream inputStream, List<String> list) {
        this.inputStream = inputStream;
        this.list = list;
    }

    public void start() {
        Thread thread = new Thread(this);
        thread.setDaemon(true);//将其设置为守护线程
        thread.start();
    }

    public void run() {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(inputStream, character));
            String line = null;
            while ((line = br.readLine()) != null) {
                if (line != null) {
                    list.add(line + System.lineSeparator());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                //释放资源
                inputStream.close();
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
