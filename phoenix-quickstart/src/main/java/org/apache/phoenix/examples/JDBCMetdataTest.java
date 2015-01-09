package org.apache.phoenix.examples;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

public class JDBCMetdataTest {

	static {
	      String pattern = "%d %-5p [%c] (%t) %m%n";
	      PatternLayout layout = new PatternLayout(pattern);
	      ConsoleAppender consoleAppender = new ConsoleAppender(layout);
	      Logger.getRootLogger().setLevel(Level.WARN);
	      Logger.getRootLogger().addAppender(consoleAppender);  
		}

	public static void main(String[] args) throws Exception {

		Connection conn = PhoenixUtils.getPhoenixConnection("127.0.0.1:2181");
		
		DatabaseMetaData metadata = conn.getMetaData();
		
		String quoteString = metadata.getIdentifierQuoteString();
		System.out.println(quoteString);
		
		ResultSet rs = metadata.getTypeInfo();

		while (rs.next()) {
			String name = rs.getString(1);
			boolean unsigned = rs.getBoolean(10);
			System.out.println(name + ", " + unsigned);
		}
		

		JDBCUtil.close(conn);
	}
}
