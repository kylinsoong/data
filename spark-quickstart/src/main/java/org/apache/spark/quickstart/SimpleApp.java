package org.apache.spark.quickstart;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;

public class SimpleApp {

    @SuppressWarnings("serial")
    public static void main(String[] args) {

        String logFile = "/home/kylin/server/spark-1.6.0-bin-hadoop2.6/README.md";
        
        SparkConf conf = new SparkConf().setAppName("Simple Application").setMaster("master");
        JavaSparkContext sc = new JavaSparkContext(conf);
        JavaRDD<String> logData = sc.textFile(logFile).cache();
        
        long numAs = logData.filter(new Function<String, Boolean>() {
            public Boolean call(String s) { return s.contains("a"); }
          }).count();

        long numBs = logData.filter(new Function<String, Boolean>() {
            public Boolean call(String s) { return s.contains("b"); }
          }).count();

        System.out.println("Lines with a: " + numAs + ", lines with b: " + numBs);
        
        sc.close();
    }

}
