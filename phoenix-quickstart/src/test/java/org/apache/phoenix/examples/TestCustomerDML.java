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
	public void testInsert() throws Exception{
		JDBCUtil.executeUpdate(conn, "UPSERT INTO \"Customer\" (\"ROW_ID\", \"city\", \"name\", \"amount\", \"product\") VALUES ('106', 'Beijing', 'Kylin Soong', '$8000.00', 'Crystal Orange')");
		JDBCUtil.executeUpdate(conn, "UPSERT INTO \"Customer\" (\"ROW_ID\", \"city\", \"name\", \"amount\", \"product\") VALUES ('107', 'Beijing', 'Kylin Soong', '$8000.00', 'Crystal Orange')");
		JDBCUtil.executeQuery(conn, "SELECT * FROM \"Customer\"");
	}
	
	@Test
	public void testBatchedInsert() throws Exception {
		JDBCUtil.executeBatchedUpdate(conn, "UPSERT INTO \"Customer\" (\"ROW_ID\", \"city\", \"name\", \"amount\", \"product\") VALUES (?, ?, ?, ?, ?)", 3);
		JDBCUtil.executeBatchedUpdate(conn, "UPSERT INTO \"Customer\" VALUES (?, ?, ?, ?, ?)", 4);
		JDBCUtil.executeQuery(conn, "SELECT * FROM \"Customer\"");
	}
	
	
	
	@Test
	public void testSelect() throws Exception {
		
		JDBCUtil.executeQuery(conn, "SELECT * FROM \"Customer\"");
				
		JDBCUtil.executeQuery(conn, "SELECT Customer.\"city\", Customer.\"amount\" FROM \"Customer\" AS Customer");
		
		JDBCUtil.executeQuery(conn, "SELECT DISTINCT Customer.\"city\" FROM \"Customer\" AS Customer");
		
		JDBCUtil.executeQuery(conn, "SELECT Customer.\"city\", Customer.\"amount\" FROM \"Customer\" AS Customer WHERE Customer.\"ROW_ID\" = '105'");
		
		JDBCUtil.executeQuery(conn, "SELECT Customer.\"city\", Customer.\"amount\" FROM \"Customer\" AS Customer WHERE Customer.\"ROW_ID\" = '105' OR Customer.\"name\" = 'John White'");
		
		JDBCUtil.executeQuery(conn, "SELECT Customer.\"city\", Customer.\"amount\" FROM \"Customer\" AS Customer WHERE Customer.\"ROW_ID\" = '105' AND Customer.\"name\" = 'John White'");
	}
	
	@Test
	public void testSelectOrderBy() throws Exception {

		JDBCUtil.executeQuery(conn, "SELECT Customer.\"ROW_ID\", Customer.\"city\", Customer.\"name\", Customer.\"amount\", Customer.\"product\" FROM \"Customer\" AS Customer ORDER BY Customer.\"ROW_ID\"");
		
		JDBCUtil.executeQuery(conn, "SELECT Customer.\"ROW_ID\", Customer.\"city\", Customer.\"name\", Customer.\"amount\", Customer.\"product\" FROM \"Customer\" AS Customer ORDER BY Customer.\"ROW_ID\" ASC");
		
		JDBCUtil.executeQuery(conn, "SELECT Customer.\"ROW_ID\", Customer.\"city\", Customer.\"name\", Customer.\"amount\", Customer.\"product\" FROM \"Customer\" AS Customer ORDER BY Customer.\"ROW_ID\" DESC");
	
		JDBCUtil.executeQuery(conn, "SELECT Customer.\"ROW_ID\", Customer.\"city\", Customer.\"name\", Customer.\"amount\", Customer.\"product\" FROM \"Customer\" AS Customer ORDER BY Customer.\"name\", Customer.\"city\" DESC");
	}
	
	@Test
	public void testSelectGroupBy() throws Exception{
		
		JDBCUtil.executeQuery(conn, "SELECT COUNT(Customer.\"ROW_ID\") FROM \"Customer\" AS Customer WHERE Customer.\"name\" = 'John White'");

		JDBCUtil.executeQuery(conn, "SELECT Customer.\"name\", COUNT(Customer.\"ROW_ID\") FROM \"Customer\" AS Customer GROUP BY Customer.\"name\"");
		
		JDBCUtil.executeQuery(conn, "SELECT Customer.\"name\", COUNT(Customer.\"ROW_ID\") FROM \"Customer\" AS Customer GROUP BY Customer.\"name\" HAVING COUNT(Customer.\"ROW_ID\") > 1");
		
		JDBCUtil.executeQuery(conn, "SELECT Customer.\"name\", Customer.\"city\", COUNT(Customer.\"ROW_ID\") FROM \"Customer\" AS Customer GROUP BY Customer.\"name\", Customer.\"city\"");
		
		JDBCUtil.executeQuery(conn, "SELECT Customer.\"name\", Customer.\"city\", COUNT(Customer.\"ROW_ID\") FROM \"Customer\" AS Customer GROUP BY Customer.\"name\", Customer.\"city\" HAVING COUNT(Customer.\"ROW_ID\") > 1");
	}
	
	@Test
	public void testSelectLimit() throws Exception{
		JDBCUtil.executeQuery(conn, "SELECT * FROM \"Customer\"");
		JDBCUtil.executeQuery(conn, "SELECT * FROM \"Customer\" LIMIT 3");
	}
	
	@Test
	public void testCustomerTableMapping() throws Exception {
		
		String ddl = "CREATE TABLE IF NOT EXISTS \"Customer\" (\"Row_Id\" VARCHAR PRIMARY KEY, \"customer\".\"name\" VARCHAR, \"customer\".\"city\" VARCHAR, \"sales\".\"product\" VARCHAR, \"sales\".\"amount\" VARCHAR)";
		JDBCUtil.executeUpdate(conn, ddl);
		
		JDBCUtil.executeQuery(conn, "SELECT * FROM \"Customer\"");
		
		JDBCUtil.printTableColumn(conn, "SELECT * FROM \"Customer\"");
		
		JDBCUtil.executeQuery(conn, "SELECT \"ROW_ID\", \"city\", \"name\", \"amount\", \"product\" FROM \"Customer\"");
		
		JDBCUtil.executeQuery(conn, "SELECT Customer.\"ROW_ID\", Customer.\"city\", Customer.\"name\", Customer.\"amount\", Customer.\"product\" FROM \"Customer\" AS Customer");
	}

}
