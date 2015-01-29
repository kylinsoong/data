package org.apache.phoenix.examples;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class MetaDataTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws SQLException {

		Connection conn = DriverManager.getConnection("jdbc:phoenix:127.0.0.1:2181");
		
		DatabaseMetaData metadata = conn.getMetaData();
		
		ResultSet rs = metadata.getTypeInfo();
		printColumnNameType(rs);
		if (rs != null) {
			while (rs.next()) {
				String TYPE_NAME = rs.getString(1);
				int DATA_TYPE = rs.getInt(2);
				int PRECISION = rs.getInt(3);
				boolean UNSIGNED_ATTRIBUTE = rs.getBoolean(10);
				System.out.println(TYPE_NAME + ", " + DATA_TYPE + ", " + PRECISION + ", " + UNSIGNED_ATTRIBUTE + "");
			}
		}
		
		rs = metadata.getTables(null, null, null, null);
		printColumnNameType(rs);
		if (rs != null) {
			while(rs.next()) {
				String TABLE_CATALOG = rs.getString(1);
				String TABLE_SCHEMA = rs.getString(2);
				String TABLE_NAME = rs.getString(3);
				String TABLE_TYPE = rs.getString(4);
				String REMARKS = rs.getString(5);
				String TYPE_NAME = rs.getString(6);
				String SQL = rs.getString(11);
				System.out.println(TABLE_CATALOG + ", " + TABLE_SCHEMA + ", " + TABLE_NAME + ", " + TABLE_TYPE + ", " + REMARKS + ", " + TYPE_NAME + ", " + rs.getString(7) + ", " + rs.getString(8) + ", " + rs.getString(9) + ", " + rs.getString(10));
			}
		}
		
	}
	
	private static void printColumnNameType(ResultSet rs) throws SQLException {

		ResultSetMetaData metadata  = rs.getMetaData();
		int columns = metadata.getColumnCount();
		for(int i = 1 ; i <= columns ; i ++) {
			System.out.print(i + ": " + metadata.getColumnName(i) + "/" + metadata.getColumnTypeName(i) + "    ");
		}
		
		System.out.println();
	}

}
