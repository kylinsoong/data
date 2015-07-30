package org.apache.hadoop.examples;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class Util {
    
    static final String HADOOP_HOME = "/home/kylin/server/hadoop-1.2.1";
    static final String INPUT = "input";
    static final int MAX = 1000000;

    public static void main(String[] args) throws IOException {

        String path = HADOOP_HOME + File.separator + INPUT;
        
        for(int i = 0 ; i < 1000 ; i ++){
            writeFile(new File(path));
        }
        
    }
    
    static Random r = new Random();

    private static void writeFile(File dir) throws IOException {
        
        File file = new File(dir, "sort" + System.nanoTime());        
        if(file.createNewFile()){
            PrintWriter writer = new PrintWriter(file);
            for(int i = 0 ; i < 10000 ; i ++) {
                writer.println(r.nextInt(MAX));
            }
            writer.flush();
            writer.close();
        }
        
    }

}
