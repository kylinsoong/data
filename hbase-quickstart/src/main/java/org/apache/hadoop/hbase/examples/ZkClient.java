package org.apache.hadoop.hbase.examples;

import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.Watcher.Event.KeeperState;

public class ZkClient {

	public static void main(String[] args) throws Exception {
		String path = "/hbase/meta-region-server";
		ZkClient client = new ZkClient();
		ZooKeeper zk = client.connect(path);
		byte[] data = zk.getData(path, false, zk.exists(path, false));
		System.out.println(new String(data));
	}
	
	private ZooKeeper zk;
	
	private CountDownLatch connSignal = new CountDownLatch(1);
	
	public ZooKeeper connect(String host) throws Exception {
		zk = new ZooKeeper(host, 5000, new Watcher(){

			public void process(WatchedEvent event) {
				if(event.getState() == KeeperState.SyncConnected) {
					connSignal.countDown();
				}
			}});
		connSignal.await();
		return zk;
	}

	public void close() throws InterruptedException {
		zk.close();
	}

}
