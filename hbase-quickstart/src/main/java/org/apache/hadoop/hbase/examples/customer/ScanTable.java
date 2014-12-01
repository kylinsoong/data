package org.apache.hadoop.hbase.examples.customer;

import java.io.IOException;
import java.util.NavigableMap;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;

public class ScanTable {

	public static void main(String[] args) throws IOException {

		Configuration conf = HBaseConfiguration.create();
		HTable table = new HTable(conf, "Customer");
		Scan scan = new Scan();
		ResultScanner rs = table.getScanner(scan);
		try {
			for (Result r = rs.next(); r != null; r = rs.next()){
				printResult(r);
			}
		} finally {
			  rs.close();
		}
		table.close();
	}

	private static void printResult(Result result) {

		String row = Bytes.toString(result.getRow());
		System.out.println(row);
		for(byte[] key : result.getMap().keySet()) {
			String family = Bytes.toString(key);
			System.out.println("\t" + family);
			NavigableMap<byte[], NavigableMap<Long, byte[]>> value = result.getMap().get(key);
			for(byte[] qualifier : value.keySet()) {
				System.out.println("\t\t" + Bytes.toString(qualifier));
				NavigableMap<Long, byte[]> cell = value.get(qualifier);
				for(Long timestamp : cell.keySet()) {
					String cellValue = Bytes.toString(cell.get(timestamp));
					System.out.printf("\t\t\t%s, %d\n", cellValue, timestamp);
				}
			}
		}
	}

}
