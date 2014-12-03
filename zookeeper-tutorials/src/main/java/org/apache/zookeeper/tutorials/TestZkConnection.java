package org.apache.zookeeper.tutorials;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.zookeeper.ZooKeeper;

public class TestZkConnection {
	
	static {
		String pattern = "[%d{ABSOLUTE}] [%t] %5p (%F:%L) - %m%n";
		PatternLayout layout = new PatternLayout(pattern);
		ConsoleAppender consoleAppender = new ConsoleAppender(layout);
		Logger.getRootLogger().setLevel(Level.DEBUG);
		Logger.getRootLogger().addAppender(consoleAppender);
	}

	private static ZooKeeper zk ;
	
	private static ZooKeeperConnector zkconnector;
	
	private static List<String> znodelist = new ArrayList<String>();
	
	public static void main(String[] args) throws Exception {
		
		zkconnector = new ZooKeeperConnector();
		
		zk = zkconnector.connect("localhost");
		
		znodelist = zk.getChildren("/", true);
		
		for(String znode : znodelist) {
			System.out.println(znode);
		}
	}
}
