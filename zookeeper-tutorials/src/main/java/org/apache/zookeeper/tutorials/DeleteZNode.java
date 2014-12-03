package org.apache.zookeeper.tutorials;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;

public class DeleteZNode {
	
	private static ZooKeeper zk;

	private static ZooKeeperConnector zkconnector;
	
	public static void delete(String path) throws InterruptedException, KeeperException {
		zk.delete(path, zk.exists(path, true).getVersion());
	}

	public static void main(String[] args) throws Exception {

		String path = "/sampleznode";

		zkconnector = new ZooKeeperConnector();

		zk = zkconnector.connect("localhost");
		
		delete(path);
	}

}
