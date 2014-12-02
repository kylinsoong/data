package org.apache.hadoop.hbase.examples;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HConstants;
import org.apache.hadoop.hbase.HRegionInfo;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.HConnectionManager;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

public class ConnectionRefuseReproduce {
	
	static {
      String pattern = "[%d{ABSOLUTE}] [%t] %5p (%F:%L) - %m%n";
      PatternLayout layout = new PatternLayout(pattern);
      ConsoleAppender consoleAppender = new ConsoleAppender(layout);
      Logger.getRootLogger().setLevel(Level.DEBUG);
      Logger.getRootLogger().addAppender(consoleAppender);  
	}

	public static void main(String[] args) throws IOException {

		Configuration conf = HBaseConfiguration.create();
		HConnection connection = HConnectionManager.getConnection(conf);
		HTable metaTable = new HTable(TableName.META_TABLE_NAME, connection, null);
		TableName tableName = TableName.valueOf("Customer");
		byte[] row = "101".getBytes();
		byte[] searchRow = HRegionInfo.createRegionName(tableName, row, HConstants.NINES, false);
		Result startRowResult = metaTable.getRowOrBefore(searchRow, HConstants.CATALOG_FAMILY);
		System.out.println(startRowResult);
	}

}
