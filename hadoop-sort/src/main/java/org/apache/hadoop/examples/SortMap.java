package org.apache.hadoop.examples;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class SortMap extends Mapper<Object, Text, IntWritable, IntWritable>  {
    
    private IntWritable data = new IntWritable();

    @Override
    protected void map(
            Object key, 
            Text value, 
            Mapper<Object, Text, IntWritable, IntWritable>.Context context) throws IOException, InterruptedException {
        
        String line = value.toString();
        data.set(Integer.parseInt(line));
        context.write(data, new IntWritable(1));
    }
    

}
