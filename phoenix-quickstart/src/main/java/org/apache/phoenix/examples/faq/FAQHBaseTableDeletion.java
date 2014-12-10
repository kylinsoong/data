package org.apache.phoenix.examples.faq;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.TableNotFoundException;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.HBaseAdmin;

public class FAQHBaseTableDeletion {

	public static void main(String[] args) throws MasterNotRunningException, ZooKeeperConnectionException, IOException {
		Configuration conf = HBaseConfiguration.create();
		HBaseAdmin admin = new HBaseAdmin(conf);
		try {
			System.out.println("Disabling table...");
			admin.disableTable("t1");
			System.out.println("Deleting table...");
			admin.deleteTable("t1");
		} catch (Exception e) {
			if(e instanceof TableNotFoundException) {
				System.out.println("Table t1 not exist");
			}
		}
	}

}
