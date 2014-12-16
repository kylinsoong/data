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

public class TestCustomerDML {
	
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
	public void testSelect() throws Exception {
		
		JDBCUtil.executeQuery(conn, "SELECT * FROM \"Customer\"");
				
		JDBCUtil.executeQuery(conn, "SELECT customer.\"city\", customer.\"amount\" FROM \"Customer\" AS customer");
		
		JDBCUtil.executeQuery(conn, "SELECT DISTINCT customer.\"city\" FROM \"Customer\" AS customer");
		
		JDBCUtil.executeQuery(conn, "SELECT customer.\"city\", customer.\"amount\" FROM \"Customer\" AS customer WHERE customer.\"ROW_ID\" = '104'");
		
	}
	
	@Test
	public void testSelectOrderBy() throws Exception {

		JDBCUtil.executeQuery(conn, "SELECT customer.\"ROW_ID\", customer.\"city\", customer.\"name\", customer.\"amount\", customer.\"product\" FROM \"Customer\" AS customer ORDER BY customer.\"ROW_ID\"");
		
		JDBCUtil.executeQuery(conn, "SELECT customer.\"ROW_ID\", customer.\"city\", customer.\"name\", customer.\"amount\", customer.\"product\" FROM \"Customer\" AS customer ORDER BY customer.\"ROW_ID\" ASC");
		
		JDBCUtil.executeQuery(conn, "SELECT customer.\"ROW_ID\", customer.\"city\", customer.\"name\", customer.\"amount\", customer.\"product\" FROM \"Customer\" AS customer ORDER BY customer.\"ROW_ID\" DESC");
	
		JDBCUtil.executeQuery(conn, "SELECT customer.\"ROW_ID\", customer.\"city\", customer.\"name\", customer.\"amount\", customer.\"product\" FROM \"Customer\" AS customer ORDER BY customer.\"name\", customer.\"city\" DESC");
	}
	
	@Test
	public void testCustomerTableMapping() throws Exception {
		
		String ddl = "CREATE TABLE IF NOT EXISTS \"Customer\" (\"Row_Id\" VARCHAR PRIMARY KEY, \"customer\".\"name\" VARCHAR, \"customer\".\"city\" VARCHAR, \"sales\".\"product\" VARCHAR, \"sales\".\"amount\" VARCHAR)";
		JDBCUtil.executeUpdate(conn, ddl);
		
		JDBCUtil.executeQuery(conn, "SELECT * FROM \"Customer\"");
		
		JDBCUtil.printTableColumn(conn, "SELECT * FROM \"Customer\"");
		
		JDBCUtil.executeQuery(conn, "SELECT \"ROW_ID\", \"city\", \"name\", \"amount\", \"product\" FROM \"Customer\"");
		
		JDBCUtil.executeQuery(conn, "SELECT customer.\"ROW_ID\", customer.\"city\", customer.\"name\", customer.\"amount\", customer.\"product\" FROM \"Customer\" AS customer");
	}

}
