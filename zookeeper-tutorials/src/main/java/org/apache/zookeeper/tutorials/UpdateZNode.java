package org.apache.zookeeper.tutorials;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;

public class UpdateZNode {
	
	private static ZooKeeper zk;

	private static ZooKeeperConnector zkconnector;
	
	public static void update(String path, byte[] data) throws KeeperException, InterruptedException {
		zk.setData(path, data, zk.exists(path, true).getAversion());
	}

	public static void main(String[] args) throws Exception {

		String path = "/sampleznode";
		byte[] data = "sample znode data version 2".getBytes();

		zkconnector = new ZooKeeperConnector();

		zk = zkconnector.connect("localhost");
		
		update(path, data);
	}

}
