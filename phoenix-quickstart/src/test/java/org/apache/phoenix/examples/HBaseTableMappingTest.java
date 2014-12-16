package org.apache.phoenix.examples;

import java.io.IOException;
import java.sql.Connection;

import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.phoenix.examples.faq.FAQHBaseTableCreation;
import org.apache.phoenix.examples.faq.FAQHBaseTableDeletion;
import org.apache.phoenix.examples.faq.FAQHBaseTablePutData;
import org.junit.BeforeClass;
import org.junit.Test;

public class HBaseTableMappingTest {
	
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
	
	@Test
	public void testViewMapping() throws Exception {
		
		JDBCUtil.executeUpdate(conn, "DROP VIEW IF EXISTS \"t1\" CASCADE");
		
		JDBCUtil.executeUpdate(conn, "CREATE VIEW \"t1\" ( pk VARCHAR PRIMARY KEY, \"f1\".\"qualifier1\" VARCHAR, \"f1\".\"qualifier2\" VARCHAR, \"f1\".\"qualifier3\" VARCHAR)");
		
		JDBCUtil.executeQuery(conn, "SELECT * FROM \"t1\"");
		
		JDBCUtil.printTableColumn(conn, "SELECT * FROM \"t1\"");
		
		JDBCUtil.executeQuery(conn, "SELECT t1.PK, t1.\"qualifier1\", t1.\"qualifier3\", t1.\"qualifier3\" FROM \"t1\" AS t1");
		
		JDBCUtil.executeUpdate(conn, "DROP VIEW \"t1\"");
	}
	
	@Test
	public void testTableMapping() throws Exception {
						
		JDBCUtil.executeUpdate(conn, "DROP TABLE IF EXISTS \"t1\" CASCADE");
		
		JDBCUtil.executeUpdate(conn, "CREATE TABLE \"t1\" ( pk VARCHAR PRIMARY KEY, \"f1\".\"qualifier1\" VARCHAR, \"f1\".\"qualifier2\" VARCHAR, \"f1\".\"qualifier3\" VARCHAR)");
		
		JDBCUtil.executeQuery(conn, "SELECT * FROM \"t1\"");
		
		JDBCUtil.printTableColumn(conn, "SELECT * FROM \"t1\"");
		
		JDBCUtil.executeQuery(conn, "SELECT t1.PK, t1.\"qualifier1\", t1.\"qualifier3\", t1.\"qualifier3\" FROM \"t1\" AS t1");
		
		JDBCUtil.executeUpdate(conn, "UPSERT INTO \"t1\" VALUES('1000006', 'New Added Value', 'New Added Value', 'New Added Value')");
		
		JDBCUtil.executeQuery(conn, "SELECT t1.PK, t1.\"qualifier1\", t1.\"qualifier3\", t1.\"qualifier3\" FROM \"t1\" AS t1");
	}
	
	@Test
	public void testCustomerTableMapping() throws Exception {
//		String ddl = "CREATE TABLE IF NOT EXISTS \"Customer\" (\"Row_Id\" VARCHAR, \"customer\".\"name\" VARCHAR, \"customer\".\"city\" VARCHAR, CONSTRAINT PK_\"Customer\" PRIMARY KEY (\"Row_Id\"))";
		
		String ddl = "CREATE TABLE IF NOT EXISTS \"Customer\" (\"Row_Id\" VARCHAR PRIMARY KEY, \"customer\".\"name\" VARCHAR, \"customer\".\"city\" VARCHAR, \"sales\".\"product\" VARCHAR, \"sales\".\"amount\" VARCHAR)";
		JDBCUtil.executeUpdate(conn, ddl);
		
		JDBCUtil.executeQuery(conn, "SELECT * FROM \"Customer\"");
		
		JDBCUtil.printTableColumn(conn, "SELECT * FROM \"Customer\"");
		
		JDBCUtil.executeQuery(conn, "SELECT \"ROW_ID\", \"city\", \"name\", \"amount\", \"product\" FROM \"Customer\"");
		
		JDBCUtil.executeQuery(conn, "SELECT customer.\"ROW_ID\", customer.\"city\", customer.\"name\", customer.\"amount\", customer.\"product\" FROM \"Customer\" AS customer");
	}

	protected void prepareData() throws MasterNotRunningException, ZooKeeperConnectionException, IOException {
		FAQHBaseTableDeletion.main(new String[]{});
		FAQHBaseTableCreation.main(new String[]{});
		FAQHBaseTablePutData.main(new String[]{});
	}

}
