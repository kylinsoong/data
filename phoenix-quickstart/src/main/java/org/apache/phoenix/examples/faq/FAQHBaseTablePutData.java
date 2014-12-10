package org.apache.phoenix.examples.faq;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;

public class FAQHBaseTablePutData {

	public static void main(String[] args) throws IOException {

		Configuration conf = HBaseConfiguration.create();
		HTable table = new HTable(conf, "t1");
		
		Put put = new Put(Bytes.toBytes("1000001"));
		put.add(Bytes.toBytes("f1"), Bytes.toBytes("qualifier1"), Bytes.toBytes("Apache Phoenix"));
		put.add(Bytes.toBytes("f1"), Bytes.toBytes("qualifier2"), Bytes.toBytes("Apache Phoenix"));
		put.add(Bytes.toBytes("f1"), Bytes.toBytes("qualifier3"), Bytes.toBytes("Apache Phoenix"));
		table.put(put);
		table.flushCommits();
		
		put = new Put(Bytes.toBytes("1000002"));
		put.add(Bytes.toBytes("f1"), Bytes.toBytes("qualifier1"), Bytes.toBytes("Apache Phoenix"));
		put.add(Bytes.toBytes("f1"), Bytes.toBytes("qualifier2"), Bytes.toBytes("Apache Phoenix"));
		put.add(Bytes.toBytes("f1"), Bytes.toBytes("qualifier3"), Bytes.toBytes("Apache Phoenix"));
		table.put(put);
		table.flushCommits();
		
		put = new Put(Bytes.toBytes("1000003"));
		put.add(Bytes.toBytes("f1"), Bytes.toBytes("qualifier1"), Bytes.toBytes("Apache Phoenix"));
		put.add(Bytes.toBytes("f1"), Bytes.toBytes("qualifier2"), Bytes.toBytes("Apache Phoenix"));
		put.add(Bytes.toBytes("f1"), Bytes.toBytes("qualifier3"), Bytes.toBytes("Apache Phoenix"));
		table.put(put);
		table.flushCommits();
		
		put = new Put(Bytes.toBytes("1000004"));
		put.add(Bytes.toBytes("f1"), Bytes.toBytes("qualifier1"), Bytes.toBytes("Apache Phoenix"));
		put.add(Bytes.toBytes("f1"), Bytes.toBytes("qualifier2"), Bytes.toBytes("Apache Phoenix"));
		put.add(Bytes.toBytes("f1"), Bytes.toBytes("qualifier3"), Bytes.toBytes("Apache Phoenix"));
		table.put(put);
		table.flushCommits();
		
		put = new Put(Bytes.toBytes("1000005"));
		put.add(Bytes.toBytes("f1"), Bytes.toBytes("qualifier1"), Bytes.toBytes("Apache Phoenix"));
		put.add(Bytes.toBytes("f1"), Bytes.toBytes("qualifier2"), Bytes.toBytes("Apache Phoenix"));
		put.add(Bytes.toBytes("f1"), Bytes.toBytes("qualifier3"), Bytes.toBytes("Apache Phoenix"));
		table.put(put);
		table.flushCommits();
		
	
		table.close();
	}

}
