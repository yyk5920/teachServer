package com.yjs.mips.util;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CompareResult {
    public static Wave compare(String correctPath, String stuPath,String stuRightPath ) {
        Boolean correct = true;
        System.out.println("======Start Search!=======");
        File file = new File(correctPath);
        File stuFile = new File(stuPath);
        File sturFile = new File(stuRightPath);
//        System.out.println(correctPath);
//        System.out.println(stuPath);
        BufferedReader br = null;
        BufferedReader stubr = null;
        BufferedWriter sturbw = null;
        List<String> corResult = new ArrayList<>();
        List<String> stuResult = new ArrayList<>();
        List<String> sturResult = new ArrayList<>();
//        List<String> stuWarnResult = new ArrayList<>();
        try {
            br = new BufferedReader(new FileReader(file));
            stubr = new BufferedReader(new FileReader(stuFile));
            sturbw = new BufferedWriter(new FileWriter(sturFile));
//            stubr.mark(90000000);
            System.out.println("======Start Search!3333333");
            String corText = null;
            String stuText = null;
            String sturText = null;
            int lineNum = 0;
            System.out.println("======Start Search!1111111");
            while ((corText = br.readLine()) != null && (stuText = stubr.readLine()) != null) {
                if(!corText.equals(stuText)){
                    correct = false;
                }
//                System.out.println("======Start Search!1iiiiiii");
//                System.out.print(corText);
//                System.out.print(stuText);
                lineNum++;
//                String searchCorText = corText.trim();
//                String searchStuText = stuText.trim();
                String [] ss = corText.split("");
                String [] ss1 = stuText.split("");
//                String [] ss2 = new String[100];
                String w =null;
                String q =null;
                String e = "=";
                String s =null;
                String a ="0";
                String b ="1";
                corResult.add(corText);
                int j=0;
//                stuResult.add(stuText);
//                stuWarnResult.add(stuText);
//                List<String> list = new ArrayList<String>();
                for(int i=0;i<ss.length;i++) {
//                    if(ss[i].equals(e)&&ss1[i].equals(e)){
//                        s=ss[i];
//                        if(!ss[i].equals(ss1[i])&&ss[i].equals(e)){
//                            ss1[i]="9";
//                        }
//                        else if(!ss[i].equals(ss1[i])&&ss1[i].equals(e)) {
//                            ss1[i] = ".";
//                        }
                    if (!ss[i].equals(ss1[i]) && (Character.isDigit(ss1[i].charAt(0)))) {
                        for (int jj = 0; jj < ss.length; jj++) {
                            if (ss[jj].equals(e) && ss1[jj].equals(e)) {
                                ss1[jj] = "9";
                            }
                        }
                    }
                }
                for(int i=0;i<ss.length;i++) {
                    if(!ss[i].equals(ss1[i])&&w==null&&(ss[i].equals(a)||ss[i].equals(b)||ss1[i].equals(a)||ss1[i].equals(b))){
                        w= ss[i];
                        q=ss1[i];
                        ss1[i] = "9";
                        sturbw.write(ss1[i]);
                    }
                    else if (!ss[i].equals(ss1[i])&&(ss[i].equals(a)||ss[i].equals(b)||ss1[i].equals(a)||ss1[i].equals(b))) {
//                    System.out.println(searchCorText);
//                    for(int i=0;i<stuText.length();i++){
//                        if(Character.isDigit(stuText.charAt(i)))
                        if (q.equals(ss1[i])) {
                            ss1[i] = q;
                        }
                        else if(w.equals(ss[i])){
                            ss1[i] = w;
                        }
                        else {
                            ss1[i] = "9";
                        }
                        sturbw.write(ss1[i]);
                    }
                    else if((Character.isDigit(ss1[i].charAt(0)))) {
                        q= w= ss[i];
                        sturbw.write(ss1[i]);
                    }
                    else{
                        sturbw.write(ss1[i]);
                    }
                }



                sturbw.write("\r\n");
//                            System.out.println(stuText);
//                        }
//                    }
//                    System.out.println("第" + (lineNum-1) + "个周期不相等\n");
//                    errorText = ""+(lineNum-1) + "," + searchCorText + "," + searchStuText;
//                   stuWarnResult.add()
                stuResult.add(stuText);
//                        correct = false;
            }
//                    else{
////                    sturbw.write("aaa");
//                        sturbw.write(stuText+"\r\n");
//                        System.out.println(sturbw);
//                    }
//                }



//            }
//        {
//                corResult.add(corText);
//                stuResult.add(stuText);
////                System.out.println(stuText);
////                stuWarnResult.add(stuText);
//                correct = false;
//
//            }

//            if ((corText = br.readLine()) != null && (stuText = stubr.readLine()) == null) {
//                String searchCorText = corText.trim();
//                String errorText = "";
//                corResult.add(corText);
//                errorText = ""+(lineNum-1) + "," + searchCorText + "," + "NaN";
//                errorData.add(errorText);
//                System.out.println("第" + (lineNum-1) + "个周期不相等\n");
//                correct = false;
//            } else if ((corText = br.readLine()) == null && (stuText = stubr.readLine()) != null) {
//                String searchStuText = stuText.trim();
//                String errorText = "";
//                errorText = ""+(lineNum-1) + "," + "NaN" + "," + "searchStuText";
//                errorData.add(errorText);
//                stuResult.add(stuText);
//                System.out.println("第" + (lineNum-1) + "个周期不相等\n");
//                correct = false;
//            }
//            System.out.println(stuWarnResult);
            sturbw.close();
            System.out.println("======Process Over!=======");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (stubr != null ) {
                        try {
                            stubr.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

//        waveData.add(corResult);
//        waveData.add(stuResult);
//        System.out.println(stuResult);
        return Wave.setWaveData(correct,corResult,stuResult);
    }
}
