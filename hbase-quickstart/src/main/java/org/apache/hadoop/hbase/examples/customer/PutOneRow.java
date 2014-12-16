package org.apache.hadoop.hbase.examples.customer;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;

public class PutOneRow {

	public static void main(String[] args) throws IOException {

		Configuration conf = HBaseConfiguration.create();
		HTable table = new HTable(conf, "Customer");
		Put put = new Put(Bytes.toBytes("105"));
		put.add(Bytes.toBytes("customer"), Bytes.toBytes("name"), Bytes.toBytes("John White"));
		put.add(Bytes.toBytes("customer"), Bytes.toBytes("city"), Bytes.toBytes("Los Angeles, CA"));
		put.add(Bytes.toBytes("sales"), Bytes.toBytes("product"), Bytes.toBytes("Chairs"));
		put.add(Bytes.toBytes("sales"), Bytes.toBytes("amount"), Bytes.toBytes("$400.00"));
		table.put(put);
		table.flushCommits();
		table.close();
	}

}
