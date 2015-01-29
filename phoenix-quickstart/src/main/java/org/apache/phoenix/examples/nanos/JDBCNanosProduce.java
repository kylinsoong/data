package org.apache.phoenix.examples.nanos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.TimeZone;

import org.apache.phoenix.examples.JDBCUtil;
import org.apache.phoenix.examples.PhoenixUtils;

public class JDBCNanosProduce {

	public static void main(String[] args) throws Exception {

		Connection conn = PhoenixUtils.getPhoenixConnection("127.0.0.1:2181");
		
		JDBCUtil.executeUpdate(conn, "CREATE TABLE IF NOT EXISTS \"NanosTest\" (\"ROW_ID\" VARCHAR PRIMARY KEY, \"f\".\"q\"  TIMESTAMP)");
	
		testInsert(conn);
		
//		testSelect(conn);
	}
	
	static void testSelect(Connection conn) throws Exception {
		
		Calendar cal = Calendar.getInstance();
		cal.setTimeZone(TimeZone.getDefault());
		
		String sql = "SELECT * FROM \"NanosTest\"";
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		while(rs.next()){
			System.out.println(rs.getString(1));
			System.out.println(rs.getTimestamp(2, cal));
		}
	}
	
	static void testInsert(Connection conn) throws SQLException {
		
		Calendar cal = Calendar.getInstance();
		cal.setTimeZone(TimeZone.getDefault());
		
		Timestamp timestramp = new Timestamp(new java.util.Date().getTime());
		String sql = "UPSERT INTO \"NanosTest\" (\"ROW_ID\", \"q\") VALUES (?, ?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, "100");
		pstmt.setTimestamp(2, timestramp, cal);
		pstmt.addBatch();
		
		pstmt.executeBatch();
		
		JDBCUtil.close(pstmt);
		JDBCUtil.close(conn);
	}

}
