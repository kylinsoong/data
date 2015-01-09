package org.apache.phoenix.examples;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestDateTypes {
	
	static {
	      String pattern = "%d %-5p [%c] (%t) %m%n";
	      PatternLayout layout = new PatternLayout(pattern);
	      ConsoleAppender consoleAppender = new ConsoleAppender(layout);
	      Logger.getRootLogger().setLevel(Level.WARN);
	      Logger.getRootLogger().addAppender(consoleAppender);  
		}
	
	private static Connection conn;
	
	@BeforeClass
	public static void init() throws Exception {
		conn = PhoenixUtils.getPhoenixConnection("127.0.0.1:2181");
	}
	
	@AfterClass
	public static void close() throws SQLException {
		conn.close();
	}
	
	@Test
	public void testCreate() throws Exception {
		JDBCUtil.executeUpdate(conn, "CREATE TABLE IF NOT EXISTS \"TimesTest\" (\"ROW_ID\" VARCHAR PRIMARY KEY, \"f\".\"q1\" DATE, \"f\".\"q2\" TIME, \"f\".\"q3\" TIMESTAMP)");
	}
	
	@Test
	public void testDrop() throws Exception {
		JDBCUtil.executeUpdate(conn, "DROP TABLE IF EXISTS \"TimesTest\" CASCADE");
	}
	
	@Test
	public void testSelect() throws Exception {
		JDBCUtil.executeQuery(conn, "SELECT * FROM \"TimesTest\"");
		JDBCUtil.executeQuery(conn, "SELECT g_0.\"ROW_ID\", g_0.\"q1\", g_0.\"q2\", g_0.\"q3\" FROM \"TimesTest\" AS g_0");
	}

}
