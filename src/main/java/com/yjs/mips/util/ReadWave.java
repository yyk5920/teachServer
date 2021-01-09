package com.yjs.mips.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ReadWave {
    public static List<String> getWave(String path){
        List<String> list = new ArrayList<>();
        File file = new File(path);
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String text = null;
            while ((text = br.readLine()) != null ){
                list.add(text);
            }
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
                }
            }
        }
        return list;
    }
}
