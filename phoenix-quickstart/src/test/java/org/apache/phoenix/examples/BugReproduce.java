package org.apache.phoenix.examples;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.TimeZone;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class BugReproduce {
	
private static Connection conn;

	static Calendar cal = Calendar.getInstance();
	
	
	@BeforeClass
	public static void init() throws Exception {
		conn = PhoenixUtils.getPhoenixConnection("127.0.0.1:2181");
		cal.setTimeZone(TimeZone.getDefault());
	}
	
	@AfterClass
	public static void close() throws SQLException {
		conn.close();
	}
	
	@Test
	public void testCreateTable() throws Exception {
		JDBCUtil.executeUpdate(conn, "CREATE TABLE IF NOT EXISTS \"TimesTest3\" (\"ROW_ID\" VARCHAR PRIMARY KEY, \"f\".\"q\"  UNSIGNED_TIMESTAMP)");
	}
	
	@Test
	public void testInsert() throws SQLException {
		
		Calendar cal = Calendar.getInstance();
		cal.setTimeZone(TimeZone.getDefault());
		
		Timestamp timestramp = new Timestamp(new java.util.Date().getTime());
		String sql = "UPSERT INTO \"TimesTest3\" (\"ROW_ID\", \"q\") VALUES (?, ?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, "100");
		pstmt.setTimestamp(2, timestramp, cal);
		pstmt.addBatch();
		
		pstmt.executeBatch();
		
		JDBCUtil.close(pstmt);
		JDBCUtil.close(conn);
	}
	
	@Test
	public void testSelect() throws Exception {
		
		Calendar cal = Calendar.getInstance();
		cal.setTimeZone(TimeZone.getDefault());
		
		String sql = "SELECT * FROM \"TimesTest3\"";
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		while(rs.next()){
			System.out.println(rs.getTimestamp(2, cal));
		}
	}
	
	public static void main(String[ ] args) throws Exception {
		init();
		
		new BugReproduce().testSelect();
		
		close();
	}

}
