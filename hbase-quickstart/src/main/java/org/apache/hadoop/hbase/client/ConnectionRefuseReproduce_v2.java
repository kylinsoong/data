package org.apache.hadoop.hbase.client;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.HConnectionManager;

public class ConnectionRefuseReproduce_v2 {

	public static void main(String[] args) throws IOException {

		Configuration conf = HBaseConfiguration.create();
		HConnection hci = HConnectionManager.getConnection(conf);
		
		System.out.println(hci);
	}

}
