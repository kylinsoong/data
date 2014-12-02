package org.apache.hadoop.hbase.examples.customer;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

public class GetTable {
	
	static {
//        Logger.getLogger("org.apache.hadoop.hbase.client").setLevel(Level.INFO);
        String pattern = "[%d{ABSOLUTE}] [%t] %5p (%F:%L) - %m%n";
        PatternLayout layout = new PatternLayout(pattern);
        ConsoleAppender consoleAppender = new ConsoleAppender(layout);
        Logger.getRootLogger().setLevel(Level.DEBUG);
        Logger.getRootLogger().addAppender(consoleAppender);  
	}

	public static void main(String[] args) throws IOException {
		Configuration conf = HBaseConfiguration.create();
		conf.set("hbase.client.operation.timeout", "2000");
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
