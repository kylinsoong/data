package org.apache.hadoop.hbase.examples;

import java.io.IOException;
import java.util.Map;
import java.util.NavigableMap;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

public class HbaseQuickStart {

	public static void main(String[] args) throws Exception {
	
		createTable();

		addSomeData();
		
		addSomeMoreData();

		getRow();
		
		deleteTable();
	}

	static void deleteTable() throws IOException {
		Configuration conf = HBaseConfiguration.create();
		HBaseAdmin admin = new HBaseAdmin(conf);
		System.out.println("Disabling table...");
		admin.disableTable("my-table");
		System.out.println("Deleting table...");
		admin.deleteTable("my-table");
	}

	static void getRow() throws IOException {
		Configuration conf = HBaseConfiguration.create();
		HTable table = new HTable(conf, "my-table");
		Get get = new Get(Bytes.toBytes("row1"));
		get.setMaxVersions(3);
		get.addFamily(Bytes.toBytes("colfam1"));
		get.addColumn(Bytes.toBytes("colfam2"), Bytes.toBytes("qual1"));
		
		Result result = table.get(get);
		String row = Bytes.toString(result.getRow());
		
		String specificValue = Bytes.toString(result.getValue(Bytes.toBytes("colfam1"), Bytes.toBytes("qual1")));
		System.out.println("latest colfam1:qual1 is: " + specificValue);
		
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
		table.close();
	}

	static void addSomeMoreData() throws IOException {
		Configuration conf = HBaseConfiguration.create();
		HTable table = new HTable(conf, "my-table");
		Put put = new Put(Bytes.toBytes("row1"));
		put.add(Bytes.toBytes("colfam1"), Bytes.toBytes("qual1"), Bytes.toBytes("value1-a"));
		put.add(Bytes.toBytes("colfam3"), Bytes.toBytes("qual1"), Bytes.toBytes("value4"));
		table.put(put);
		table.flushCommits();
		table.close();
	}

	static void createTable() throws MasterNotRunningException, ZooKeeperConnectionException, IOException {
		Configuration conf = HBaseConfiguration.create();
		HBaseAdmin admin = new HBaseAdmin(conf);
		HTableDescriptor tableDescriptor = new HTableDescriptor(TableName.valueOf("my-table"));
		tableDescriptor.addFamily(new HColumnDescriptor("colfam1"));
		tableDescriptor.addFamily(new HColumnDescriptor("colfam2"));
		tableDescriptor.addFamily(new HColumnDescriptor("colfam3"));
		
		admin.createTable(tableDescriptor);
		boolean tableAvailable = admin.isTableAvailable("my-table");
		System.out.println("tableAvailable = " + tableAvailable);
	}

	static void addSomeData() throws Exception {
		Configuration conf = HBaseConfiguration.create();
		HTable table = new HTable(conf, "my-table");
		Put put = new Put(Bytes.toBytes("row1"));
		put.add(Bytes.toBytes("colfam1"), Bytes.toBytes("qual1"), Bytes.toBytes("value1"));
		put.add(Bytes.toBytes("colfam1"), Bytes.toBytes("qual2"), Bytes.toBytes("value2"));
		put.add(Bytes.toBytes("colfam2"), Bytes.toBytes("qual1"), Bytes.toBytes("value3"));
		table.put(put);
		table.flushCommits();
		table.close();
	}

}
