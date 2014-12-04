package org.apache.phoenix.examples;

import java.sql.Connection;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

public class CustomerClient {
	
	static {
	      String pattern = "%d %-5p [%c] (%t) %m%n";
	      PatternLayout layout = new PatternLayout(pattern);
	      ConsoleAppender consoleAppender = new ConsoleAppender(layout);
	      Logger.getRootLogger().setLevel(Level.DEBUG);
	      Logger.getRootLogger().addAppender(consoleAppender);  
		}

	public static void main(String[] args) throws Exception {

		Connection conn = PhoenixUtils.getPhoenixConnection("127.0.0.1:2181");
		JDBCUtil.executeQuery(conn, "SELECT * FROM Customer");
		JDBCUtil.close(conn);
	}

}
