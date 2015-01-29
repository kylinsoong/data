package org.apache.phoenix.examples;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

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
		
		ResultSet rs = metadata.getTables(null, null, null, null);
		printColumnNameType(rs);
		while (rs.next()) {
			String name = rs.getString(1);
			boolean unsigned = rs.getBoolean(10);
			System.out.println(name + ", " + unsigned);
		}
		

		JDBCUtil.close(conn);
	}
	
	private static void printColumnNameType(ResultSet rs) throws SQLException {
		ResultSetMetaData metadata = rs.getMetaData();
		int columns = metadata.getColumnCount();
		for(int i = 1 ; i <= columns ; i ++) {
			System.out.print(i + ": " + metadata.getColumnName(i) + "/" + metadata.getColumnTypeName(i) + " ");
		}
		System.out.println();
	}
}
