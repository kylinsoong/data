package org.apache.hadoop.examples;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Reducer;

public class SortReduce extends Reducer<IntWritable, IntWritable, IntWritable, IntWritable>{

    private IntWritable linenum = new IntWritable(1);
    
    @Override
    protected void reduce(
            IntWritable key,
            Iterable<IntWritable> values,
            Reducer<IntWritable, IntWritable, IntWritable, IntWritable>.Context context) throws IOException, InterruptedException {
        
        for (IntWritable val : values){
            context.write(linenum, key);
            linenum = new IntWritable(linenum.get() + 1);
        }
    }

}
