package org.apache.hadoop.hbase.examples.customer;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;

public class PutTable {

	public static void main(String[] args) throws IOException {
		
		Configuration conf = HBaseConfiguration.create();
		HTable table = new HTable(conf, "Customer");
		Put put = new Put(Bytes.toBytes("101"));
		put.add(Bytes.toBytes("customer"), Bytes.toBytes("name"), Bytes.toBytes("John White"));
		put.add(Bytes.toBytes("customer"), Bytes.toBytes("city"), Bytes.toBytes("Los Angeles, CA"));
		put.add(Bytes.toBytes("sales"), Bytes.toBytes("product"), Bytes.toBytes("Chairs"));
		put.add(Bytes.toBytes("sales"), Bytes.toBytes("amount"), Bytes.toBytes("$400.00"));
		table.put(put);
		table.flushCommits();
		
		put = new Put(Bytes.toBytes("102"));
		put.add(Bytes.toBytes("customer"), Bytes.toBytes("name"), Bytes.toBytes("Jane Brown"));
		put.add(Bytes.toBytes("customer"), Bytes.toBytes("city"), Bytes.toBytes("Atlanta, GA"));
		put.add(Bytes.toBytes("sales"), Bytes.toBytes("product"), Bytes.toBytes("Lamps"));
		put.add(Bytes.toBytes("sales"), Bytes.toBytes("amount"), Bytes.toBytes("$200.00"));
		table.put(put);
		table.flushCommits();
		
		put = new Put(Bytes.toBytes("103"));
		put.add(Bytes.toBytes("customer"), Bytes.toBytes("name"), Bytes.toBytes("Bill Green"));
		put.add(Bytes.toBytes("customer"), Bytes.toBytes("city"), Bytes.toBytes("Pittsburgh, PA"));
		put.add(Bytes.toBytes("sales"), Bytes.toBytes("product"), Bytes.toBytes("Desk"));
		put.add(Bytes.toBytes("sales"), Bytes.toBytes("amount"), Bytes.toBytes("$500.00"));
		table.put(put);
		table.flushCommits();
		
		put = new Put(Bytes.toBytes("104"));
		put.add(Bytes.toBytes("customer"), Bytes.toBytes("name"), Bytes.toBytes("Jack Black"));
		put.add(Bytes.toBytes("customer"), Bytes.toBytes("city"), Bytes.toBytes("St. Louis, MO"));
		put.add(Bytes.toBytes("sales"), Bytes.toBytes("product"), Bytes.toBytes("Bed"));
		put.add(Bytes.toBytes("sales"), Bytes.toBytes("amount"), Bytes.toBytes("$1600.00"));
		table.put(put);
		table.flushCommits();
		
		table.close();
	}

}
