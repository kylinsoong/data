package org.apache.phoenix.examples;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.phoenix.schema.PDataType;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestDataTypes {
	
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
	public void testVARBINARY() throws Exception{
		JDBCUtil.executeUpdate(conn, "CREATE TABLE IF NOT EXISTS TypesTest1 (PK VARCHAR PRIMARY KEY, column1 VARBINARY)");
		
//		JDBCUtil.executeUpdate(conn, "UPSERT INTO TypesTest1(PK, column1) VALUES('100', X'76617262696E617279')");
		
		PreparedStatement pstmt = null ;
		try {
			pstmt = conn.prepareStatement("UPSERT INTO TypesTest1(PK, column1) VALUES(?, ?)");
			for(int i = 0 ; i < 2 ; i ++) {
				pstmt.setString(1, 110 + i + "");
				pstmt.setBytes(2, "varbinary".getBytes());
				pstmt.addBatch();
			}
			pstmt.executeBatch();
			if(!conn.getAutoCommit()) {
				conn.commit();
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			JDBCUtil.close(pstmt);
		}
		
		JDBCUtil.executeQuery(conn, "SELECT * FROM TypesTest1");
	}
	
	@Test
	public void testLONG() throws Exception{
		JDBCUtil.executeUpdate(conn, "CREATE TABLE IF NOT EXISTS TypesTest2 (PK VARCHAR PRIMARY KEY, column1 BIGINT)");
	}
	
	@Test
	public void testDataTypes() {
		assertEquals("VARCHAR", PDataType.VARCHAR.getSqlTypeName());
		assertEquals("VARBINARY", PDataType.VARBINARY.getSqlTypeName());
		assertEquals("BOOLEAN", PDataType.BOOLEAN.getSqlTypeName());
		assertEquals("TINYINT", PDataType.TINYINT.getSqlTypeName());
		assertEquals("SMALLINT", PDataType.SMALLINT.getSqlTypeName());
		assertEquals("INTEGER", PDataType.INTEGER.getSqlTypeName());
		assertEquals("BIGINT", PDataType.LONG.getSqlTypeName());
		assertEquals("FLOAT", PDataType.FLOAT.getSqlTypeName());
		assertEquals("DOUBLE", PDataType.DOUBLE.getSqlTypeName());
		assertEquals("DECIMAL", PDataType.DECIMAL.getSqlTypeName());
		assertEquals("DATE", PDataType.DATE.getSqlTypeName());
		assertEquals("TIME", PDataType.TIME.getSqlTypeName());
		assertEquals("TIMESTAMP", PDataType.TIMESTAMP.getSqlTypeName());
	}
	
	@Test
	public void testDataTypesInsert() throws Exception{
		String sql = "UPSERT INTO \"TypesTest\" (\"ROW_ID\", \"q1\", \"q2\", \"q3\", \"q4\", \"q5\", \"q6\", \"q7\", \"q8\", \"q9\", \"q10\", \"q11\", \"q12\", \"q13\", \"q14\", \"q15\", \"q16\", \"q17\", \"q18\", \"q19\", \"q20\") VALUES ('100', 'varchar', X'76617262696E617279', 'C', FALSE, 127, 127, 10000, 10000, 0, 0, 1000000, 1000000, 3.6, 3.6, 3.6, 1000000, 1000000, {d '2014-12-20'}, {t '13:43:40'}, {ts '2014-12-20 13:43:40.197'})";
		JDBCUtil.executeUpdate(conn, sql);
	}
	

	public static void main(String[] args) throws Exception {
		
		init();

		new TestDataTypes().testDataTypesInsert();
		
		close();
		
	}

}
