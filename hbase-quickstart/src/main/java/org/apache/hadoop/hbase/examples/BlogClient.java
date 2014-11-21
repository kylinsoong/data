package org.apache.hadoop.hbase.examples;

import java.io.IOException;
import java.util.Map;
import java.util.NavigableMap;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;

public class BlogClient {

	public static void main(String[] args) throws IOException {
		
		Configuration conf = HBaseConfiguration.create();
		HTable table = new HTable(conf, "blog");
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
		NavigableMap<byte[], NavigableMap<byte[],NavigableMap<Long,byte[]>>> map = result.getMap();
		for (Map.Entry<byte[], NavigableMap<byte[], NavigableMap<Long, byte[]>>> navigableMapEntry : map.entrySet()) {
			String family = Bytes.toString(navigableMapEntry.getKey());
			System.out.println("\t" + family);
			NavigableMap<byte[], NavigableMap<Long, byte[]>> familyContents = navigableMapEntry.getValue();
			for (Map.Entry<byte[], NavigableMap<Long, byte[]>> mapEntry : familyContents.entrySet()) {
				String qualifier = Bytes.toString(mapEntry.getKey());
				System.out.println("\t\t" + qualifier);
				NavigableMap<Long, byte[]> qualifierContents = mapEntry.getValue();
				for (Map.Entry<Long, byte[]> entry : qualifierContents.entrySet()) {
					Long timestamp = entry.getKey();
					String value = Bytes.toString(entry.getValue());
					System.out.printf("\t\t\t%s, %d\n", value, timestamp);
				}
			}
		}
	}

}
