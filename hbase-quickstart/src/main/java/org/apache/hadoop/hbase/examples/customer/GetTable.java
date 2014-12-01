package org.apache.hadoop.hbase.examples.customer;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;

public class GetTable {

	public static void main(String[] args) throws IOException {
		Configuration conf = HBaseConfiguration.create();
		HTable table = new HTable(conf, "Customer");
		Get get = new Get("101".getBytes());
		get.addFamily("customer".getBytes());
		Result result = table.get(get);
		for(Cell cell : result.rawCells()) {
			System.out.println(new String(CellUtil.cloneValue(cell)) + " - " + cell.getTimestamp());
		}
		table.close();
	}

}
