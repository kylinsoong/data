package org.apache.zookeeper.tutorials;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooDefs.Ids;

public class CreateZNode {
	
	private static ZooKeeper zk;

	private static ZooKeeperConnector zkconnector;
	
	public static void create(String path, byte[] data) throws KeeperException, InterruptedException {
		zk.create(path, data, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
	}

	public static void main(String[] args) throws Exception {

		String path = "/sampleznode";
		byte[] data = "sample znode data".getBytes();

		zkconnector = new ZooKeeperConnector();

		zk = zkconnector.connect("localhost");
		
		create(path, data);
	}

}
