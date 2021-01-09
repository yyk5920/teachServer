package com.yjs.mips.util;


import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.jsonwebtoken.lang.Collections.size;
@Data
public class WaveDrom {
    /**
     * 获取日志文件中的时间
     *
     * @throws FileNotFoundException
     * @return
     */
//    public static void main(String[] args) throws Exception {
//
//        loadVar("D:\\iverilog\\result\\verilog\\12\\result\\wave.txt", "D:\\iverilog\\result\\verilog\\12\\result\\signal.json");
//
//    }

    public static String loadVar(String infilePath, String outfilePath) throws Exception {
        //定义set集合,实现去重效果
        List<String> result = new ArrayList<>();
        //定义正则表达式
        String regex = "wire(.*?)end|reg(.*?)end";
        Pattern pattern = Pattern.compile(regex);

        //读取文件
        File file = new File(infilePath);
        BufferedReader br = new BufferedReader(new FileReader(file));
        Long fileLengthLong = file.length();
        byte[] fileContent = new byte[fileLengthLong.intValue()];
        try {
            FileInputStream inputStream = new FileInputStream(file);
            inputStream.read(fileContent);
            inputStream.close();
        } catch (Exception e) {
            // TODO: handle exception
        }
        String a = "1";
        String b = "0";
        String c = "";
        String d = " ";
        String string = new String(fileContent);
        String ss[] = string.split("#", 0);
        String sb[] = string.split("#");
        List<String> list = new ArrayList<String>();//存所有非0时间
        List<String> list1 = new ArrayList<String>();//单独存时间为0
        List<String> list2 = new ArrayList<String>();//单独存时间为0
        List<String> list3 = new ArrayList<String>();//单独存时间为0
        List<String> list4 = new ArrayList<String>();//单独存时间为0
        for (int i = 4; i < ss.length; i++) {

            if (ss[i].startsWith("0", 3)) {
                list.add(ss[i]);
            }
            if (ss[i].startsWith("\r\n")) {
                list.add(ss[i]);
            }
        }
        for (int i = 0; i < 4; i++) {
            if (sb[i].startsWith("0")) {
                list1.add(sb[i]);
            }
            if (sb[i].startsWith("\r\n")) {
                list1.add(sb[i]);
            }
        }
        String stringg = String.join(",", list);
        String stringg1 = String.join("", list1);
        //    System.out.println(stringg);
        String sss[] = stringg.split(",");
        String ssb[] = stringg1.split("\r\n");
        for (int i = 1; i < ssb.length; i++) {
            if (ssb[i] != null && ssb[i].equals(a)) {
                ssb[i] = ssb[i] + "#";
            }
            if (ssb[i] != null && ssb[i].equals(b)) {
                ssb[i] = ssb[i] + "#";
            }
            if (ssb[i] != null && ssb[i].startsWith("b") && (ssb[i].endsWith(a) || ssb[i].endsWith(b))) {
                ssb[i] = ssb[i] + "#";
            }
        }
        for (int j = 1; j < ssb.length; j++) {
            if (ssb[j].startsWith("0") || ssb[j].startsWith("1") || ssb[j].startsWith("b")) {
                list2.add(ssb[j]);
            }
        }
        String stringg2 = String.join("", list2);
        String[] lisaq = stringg2.split("", 0);
        String lisa[] = new String[100];
        int u = 0;
        int o = 0;
        for (int i = 0; i < lisaq.length - 2; i++) {
            if ((lisaq[i].equals(d) && lisaq[i + 2].equals("b")) || (lisaq[i].equals(d) && lisaq[i + 2].equals(a)) || (lisaq[i].equals(d) && lisaq[i + 2].equals(b))) {
                lisa[o] = "";
                lisa[o + 1] = "";
                for (int j = u; j < i; j++) {
                    lisa[o] += lisaq[j];
                }
                lisa[o + 1] = lisaq[i + 1];
                o += 2;
                u = i + 2;
            } else if ((lisaq[i].equals(a) && lisaq[i + 2].equals("b")) || (lisaq[i].equals(b) && lisaq[i + 2].equals("b"))) {
                lisa[o] = "";
                for (int j = u; j < i + 2; j++) {
                    lisa[o] = lisaq[j];
                    o++;
                }
                u = i + 2;
            } else if ((lisaq[i + 1].equals(d) && (i + 1) == lisaq.length - 2)) {
                lisa[o] = "";
                lisa[o + 1] = "";
                for (int j = u; j < i + 1; j++) {
                    lisa[o] += lisaq[j];
                }
                lisa[o + 1] = lisaq[i + 2];
            } else if (((lisaq[i + 1].equals(a) && (i + 1) == lisaq.length - 2)) || ((lisaq[i + 1].equals(b) && (i + 1) == lisaq.length - 2))) {
                lisa[o] = "";
                for (int j = u; j < i + 3; j++) {
                    lisa[o] = lisaq[j];
                    o++;
                }
                u = i + 2;
            }
        }
        String[] lisa1 = new String[20];
        String[] lisa2 = new String[20];
        for (int i = 0; i < lisa.length; i++) {
            if (lisa[i] != null) {
                if ((lisa[i].equals(a) || lisa[i].equals(b)) || lisa[i].startsWith("b")) {
                    lisa1[i] = lisa[i];
                }
                if (!lisa[i].equals(a) && !lisa[i].equals(b) && !lisa[i].startsWith("b")) {
                    lisa2[i] = lisa[i];
                }
                if (lisa1[i] != null) {
                    list3.add(lisa1[i]);
                }
                if (lisa2[i] != null) {
                    list4.add(lisa2[i]);
                }
            }

        }
        String stringg3 = String.join(",", list3);
        String stringg4 = String.join(",", list4);
        String[] lisa3 = stringg3.split(",");//0时间电平
        String[] lisa4 = stringg4.split(",");//0时间电平对应的接口符号
        String[][] sssss = new String[200][20];
        String yyk1 = new String();
        String yyk2 = new String();
        List<String> listt = new ArrayList<String>();
        for (int j = 0; j < sss.length; j++) {
            listt.add(sss[j]);
            String stringgg = String.join(",", listt);
            listt.clear();
            String ssss[] = stringgg.split("\r\n");
            for (int k = 0; k < ssss.length; k++) {
                sssss[j][k] = ssss[k];
            }
        }

        for (int i = 0; i < 200; i++) {
            for (int j = 0; j < 20; j++) {
                String m = sssss[i][j];
                if (m != null && m.equals(a)) {
                    m = m + "#";
                    sssss[i][j] = m;
                }
                if (m != null && m.equals(b)) {
                    m = m + "#";
                    sssss[i][j] = m;
                }
                if (m != null && m.startsWith("b") && (m.endsWith(a) || m.endsWith(b))) {
                    m = m + "#";
                    sssss[i][j] = m;
                }
                if (m != null && m.equals(c)) {
                    for (int k = 0; k < 20; k++) {
                        if ((sssss[i - 1][k] != null)) {
                            yyk1 = String.join(",", sssss[i - 1][k]);
                            yyk2 = yyk2 + yyk1 + ",";
                        }
                    }
                    for (int q = 0; q < 20; q++) {
                        if (sssss[i][q] != null && sssss[i][q] != "") {
                            yyk2 = yyk2 + sssss[i][q] + ",";
                        }

                    }
                    sssss[i - 1] = yyk2.split(",");
                    yyk2 = "";
                }
            }
        }
        String[][] s1 = new String[200][20];
        String[] ss1 = new String[200];
        int w = 0;
        int r = -1;
        for (int i = 0; i < 200; i++) {
            r++;
            int e = 0;
            for (int j = 0; j < sssss[i].length; j++) {
                String n = sssss[i][j];
                if (n != null && n != "") {
                    if (n.matches("[0-9]+")) {
                        ss1[w] = n;
                        w++;
                    } else if (n.charAt(0) == '1' || n.charAt(0) == '0' || n.charAt(0) == 'b') {
                        s1[r][e] = n;
                        e++;
                    }

                }
                if (sssss[i][0] == "" || sssss[i][0] == null) {
                    r--;
                    break;
                }
            }
        }
        List<String>[] listt1 = new List[200];
        for (int i = 0; i < 200; i++) {
            listt1[i] = new ArrayList<String>();
            for (int j = 0; j < 20; j++) {
                if (s1[i][j] != null) {
                    listt1[i].add(s1[i][j]);
                }
            }
        }
        String stringgg1[] = new String[200];
        String[][] lisaa1 = new String[200][20];
        for (int i = 0; i < 200; i++) {
            stringgg1[i] = String.join("", listt1[i]);
            String[] lisaa = stringgg1[i].split("");
            String[] lissaa = new String[200];
            int uu = 0;
            int oo = 0;
            int jj;
            int ii;
            if (lisaa.length > 2) {
                for (ii = 0; ii < lisaa.length - 2; ii++) {
                    if ((lisaa[ii].equals(d) && lisaa[ii + 2].equals("b")) || (lisaa[ii].equals(d) && lisaa[ii + 2].equals(a)) || (lisaa[ii].equals(d) && lisaa[ii + 2].equals(b))) {
                        lissaa[oo] = "";
                        lissaa[oo + 1] = "";
                        for (jj = uu; jj < ii; jj++) {
                            lissaa[oo] += lisaa[jj];
                        }
                        lissaa[oo + 1] = lisaa[ii + 1];
                        oo += 2;
                        uu = ii + 2;
                    } else if ((lisaa[ii].equals(a) && lisaa[ii + 2].equals("b")) || (lisaa[ii].equals(b) && lisaa[ii + 2].equals("b"))) {
                        lissaa[oo] = "";
                        for (jj = uu; jj < ii + 2; jj++) {
                            lissaa[oo] = lisaa[jj];
                            oo++;
                        }
                        uu = ii + 2;
                    } else if ((lisaa[ii + 1].equals(d) && (ii + 1) == lisaa.length - 2)) {
                        lissaa[oo] = "";
                        lissaa[oo + 1] = "";
                        for (jj = uu; jj < ii + 1; jj++) {
                            lissaa[oo] += lisaa[jj];
                        }
                        lissaa[oo + 1] = lisaa[ii + 2];
                    } else if (((lisaa[ii + 1].equals(a) && (ii + 1) == lisaa.length - 2)) || ((lisaa[ii + 1].equals(b) && (ii + 1) == lisaa.length - 2))) {
                        lissaa[oo] = "";
                        for (jj = uu; jj < ii + 3; jj++) {
                            lissaa[oo] = lisaa[jj];
                            oo++;
                        }
                        uu = ii + 3;
                    }

                }
            } else if (lisaa.length == 2) {
                lissaa[oo] = "";
                ii = 0;
                uu = 0;
                for (jj = uu; jj < ii + 2; jj++) {
                    lissaa[oo] = lisaa[jj];
                    oo++;
                }
            }

            for (int j = 0; j < lissaa.length; j++) {
                if (lissaa[j] != null) {
                    lisaa1[i][j] = lissaa[j];
                }
            }
        }
        String line;
        while ((line = br.readLine()) != null) {
            Matcher matcher = pattern.matcher(line);
            while (matcher.find()) {
                result.add(matcher.group());
            }
        }
        Object[] array = result.toArray();
        String first = StringUtils.join(array, ",");
        String[] second = first.split("\\,");
        String[] chin =new String[second.length];//存放输入输出
        String[] zifu = new String[second.length];//zifu存放字符
        String[] jiekou = new String[second.length];//jiekou存放接口
        for (int i = 0; i < second.length; i++) {
            List<String> third = Arrays.asList(second[i]);
            String forth = String.join("", third);
            String[] fifth = forth.split("\\ ");
            chin[i]= fifth[0];
            if(chin[i].equals("wire"))
            {
                chin[i]="输出:";
            }
            else if(chin[i].equals("reg"))
            {
                chin[i]="输入:";
            }
            zifu[i] = fifth[2];
            jiekou[i] = fifth[3];
        }
        int t = 1000;
        String doc = ".";
        String docc = "=";
        int[] tt = new int[200];
        String yyk = new String();
        String yykk = new String();
        String[] jie = new String[zifu.length];
        String[] jiee = new String[zifu.length];
        int ee = 0;
        String[] llisa3 = new String[zifu.length];
        for (int qq = 0; qq < lisa4.length; qq++) {
            for (int pp = 0; pp < zifu.length; pp++) {
                if (lisa4[qq].equals(zifu[pp])) {
                    llisa3[ee] = lisa3[qq];
                    ee++;
                }
            }

        }
        int n = 0;
        for (int l = 0; l < 200; l++) {
            if (ss1[l] != null) {
                int m = Integer.parseInt(ss1[l]);
                tt[l] = (m - n) / t;//存放时间间隔
                n = m;
            }
        }
        for (int q = 0; q < zifu.length; q++) {
            int p = 0;
            yyk = llisa3[(zifu.length) - q - 1];
            yykk = llisa3[(zifu.length) - q - 1];
            if (llisa3[(zifu.length) - q - 1].startsWith("b")) {
                Matcher mmm = Pattern.compile("(?<=b).*").matcher(llisa3[(zifu.length) - q - 1]);
                if (mmm.find()) {
                    jiee[q] = "'" + Integer.parseInt(mmm.group(0), 2) + "'";
                }
                jie[q] = docc;
                for (int i = 0; i < 200; i++) {
                    for (int j = 0; j < 20; j++) {
                        if (tt[i] == 0 && tt[i - 1] != 0) {
                            for (int m = p; m < i; m++) {
                                for (int l = 0; l < tt[m]; l++) {
                                    jie[q] = jie[q] + doc;
                                }
                            }
                            break;
                        } else if (lisaa1[i][j] != null) {
                            if (lisaa1[i][j].equals(zifu[q])) {
                                if (lisaa1[i][j - 1].equals(yykk)) {
                                    for (int m = p; m < i + 1; m++) {
                                        for (int l = 0; l < tt[m]; l++) {
                                            jie[q] = jie[q] + doc;
                                        }

//                                        else if(tt[m]==2){
//                                            for(int l=0;l<tt[m]-1;l++){
//                                                jie[q] = jie[q]+doc;
//                                            }
//                                        }
//                                        else {
//                                            jie[q] = jie[q];
//                                        }

//                                        jie[q] = jie[q] + doc;
                                    }
                                    p=i+1;
//                                    for(int iq=0;iq<i-p;iq++){
//                                        jie[q]=jie[q]+doc;//补充由于前后相等造成的少取点
//                                    }
//                                    jie[q]=jie[q]+doc;
                                } else {
                                    for (int m = p; m < i + 1; m++) {
                                        if (tt[m] > 1) {
                                            for (int l = 0; l < tt[m] - 1; l++) {
                                                jie[q] = jie[q] + doc;
                                            }
                                            if (m != i) {
                                                jie[q] = jie[q] + doc;
                                            } else {
                                                if (lisaa1[i][j - 1].startsWith("b")) {
                                                    Matcher mm = Pattern.compile("(?<=b).*").matcher(lisaa1[i][j - 1]);
                                                    if (mm.find()) {
                                                        jiee[q] = jiee[q] + "," + "'" + Integer.parseInt(mm.group(0), 2) + "'";
                                                        jie[q] = jie[q] + docc;
                                                    }
                                                }
                                            }
                                        } else {
                                            if (m != i) {
                                                jie[q] = jie[q] + doc;
                                            } else {
                                                if (lisaa1[i][j - 1].startsWith("b")) {
                                                    Matcher mm = Pattern.compile("(?<=b).*").matcher(lisaa1[i][j - 1]);
                                                    if (mm.find()) {
                                                        jiee[q] = jiee[q] + "," + "'" + Integer.parseInt(mm.group(0), 2) + "'";
                                                        jie[q] = jie[q] + docc;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    yykk = lisaa1[i][j - 1];
                                    p = i + 1;
                                }
                            }
                        }
                    }
                }
            } else {
                jie[q] = llisa3[(zifu.length) - q - 1];
                for (int i = 0; i < 200; i++) {
                    for (int j = 0; j < 20; j++) {
                        if (tt[i] == 0 && tt[i - 1] != 0) {
                            for (int m = p; m < i; m++) {
                                for (int l = 0; l < tt[m]; l++) {
                                    jie[q] = jie[q] + doc;
                                }
                            }
                            break;
                        }
                        else if (lisaa1[i][j] != null) {
                            if (lisaa1[i][j].equals(zifu[q])) {
                                if (lisaa1[i][j - 1].equals(yyk)) {
                                    for (int m = p; m < i + 1; m++) {
                                        for (int l = 0; l < tt[m] ; l++) {
                                            jie[q] = jie[q] + doc;
                                        }
                                    }
                                    p=i+1;
                                } else {
                                    for (int m = p; m < i + 1; m++) {
                                        if (tt[m] > 1) {
                                            for (int l = 0; l < tt[m] - 1; l++) {
                                                jie[q] = jie[q] + doc;
                                            }
                                            if (m != i) {
                                                jie[q] = jie[q] + doc;
                                            } else {
                                                jie[q] = jie[q] + lisaa1[i][j - 1];
                                            }
                                        } else {
                                            if (m != i) {
                                                jie[q] = jie[q] + doc;
                                            } else {
                                                jie[q] = jie[q] + lisaa1[i][j - 1];
                                            }
                                        }
                                    }
                                    yyk = lisaa1[i][j - 1];
                                    p = i + 1;
                                }
                            }

                        }

                    }
                }
            }
        }
        File outfile = new File(outfilePath);
        BufferedWriter bw = new BufferedWriter(new FileWriter(outfile));//写成一个数据流格式的数组
        bw.write("{"+"signal"+":[");
        for (int q = 0; q < zifu.length; q++) {
            if(q!=zifu.length-1){
                if (llisa3[(zifu.length) - q - 1].startsWith("b")) {
                    bw.write("{"+"name"+":" + '"' + chin[q]+jiekou[q] + '"' + "," +"wave"+":" + '"' + jie[q] + '"' + "," +"data"+":[" + jiee[q] + "]}" + "\r\n");
                } else {
                    bw.write("{"+"name"+":" + '"' +chin[q]+ jiekou[q] + '"' + "," +"wave"+":" + '"' + jie[q] + '"' + "}" + "\r\n");
                }
            }else{
                if (llisa3[(zifu.length) - q - 1].startsWith("b")) {
                    bw.write("{"+"name"+":" + '"' + chin[q]+jiekou[q] + '"' + "," + "wave"+":" + '"' + jie[q] + '"' + "," +"data"+":[" + jiee[q] + "]}]"+ "\r\n");
                } else {
                    bw.write("{"+"name"+":" + '"' + chin[q]+jiekou[q] + '"' + "," + "wave"+":" + '"' + jie[q] + '"' + "}]"+ "\r\n");
                }
            }
        }
        bw.write("config:{ skin:'narrow'}"+"\r\n");
        bw.write("head:{tick:0}");
        bw.write("}");
        br.close();
        bw.close();
        File file1 = new File(outfilePath);
        BufferedReader br1 = new BufferedReader(new FileReader(file1));
        Long fileLengthLong1 = file1.length();
        byte[] fileContent1 = new byte[fileLengthLong1.intValue()];
        try {
            FileInputStream inputStream1 = new FileInputStream(file1);
            inputStream1.read(fileContent1);
            inputStream1.close();
        } catch (Exception e) {
            // TODO: handle exception
        }
        String string1 = new String(fileContent1);
        System.out.println("读取完毕!");
//        System.out.println(string1);
        return string1;
        //返回一个json值，讲txt文件写入其中
    }
}