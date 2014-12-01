package org.apache.hadoop.hbase.examples.customer;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.HBaseAdmin;

public class CreateTable {

	public static void main(String[] args) throws MasterNotRunningException, ZooKeeperConnectionException, IOException {
		Configuration conf = HBaseConfiguration.create();
		HBaseAdmin admin = new HBaseAdmin(conf);
		HTableDescriptor tableDescriptor = new HTableDescriptor(TableName.valueOf("Customer"));
		tableDescriptor.addFamily(new HColumnDescriptor("customer"));
		tableDescriptor.addFamily(new HColumnDescriptor("sales"));
		admin.createTable(tableDescriptor);
		boolean tableAvailable = admin.isTableAvailable("Customer");
		System.out.println("tableAvailable = " + tableAvailable);
	}

}
