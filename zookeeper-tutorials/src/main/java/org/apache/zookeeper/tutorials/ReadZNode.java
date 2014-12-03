package org.apache.zookeeper.tutorials;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;

public class ReadZNode {

	private static ZooKeeper zk;

	private static ZooKeeperConnector zkconnector;

	public static byte[] read(String path) throws KeeperException,
			InterruptedException {
		return zk.getData(path, true, zk.exists(path, true));
	}

	public static void main(String[] args) throws Exception {
		
		String path = "/sampleznode";
		byte[] data;

		zkconnector = new ZooKeeperConnector();

		zk = zkconnector.connect("localhost");
		
		data = read(path);
		
		for(byte b : data) {
			System.out.print((char)b);
		}
	}
}
