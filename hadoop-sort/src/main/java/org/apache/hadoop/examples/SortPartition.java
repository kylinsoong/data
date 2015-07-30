package org.apache.hadoop.examples;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Partitioner;

public class SortPartition extends Partitioner<IntWritable, IntWritable>{

    @Override
    public int getPartition(IntWritable key, IntWritable value, int numPartitions) {
        int maxNumber = 65223;
        int bound = maxNumber / numPartitions + 1;
        int keynumber = key.get();
        for (int i = 0; i < numPartitions; i++){
            if (keynumber < bound * i && keynumber >= bound * (i - 1)){
                return i - 1;
            }
        }
        return 0;
    }

}
