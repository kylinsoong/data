package org.jboss.teiid.datasources;

import java.sql.Connection;
import java.sql.DriverManager;


public class MSAccessJDBCODBCClient {
	
	public static final String DNS_LESS_URL = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=src/main/resources/ODBCTesting.mdb";
	
	public static final String DNS_LESS_2007_URL = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb, *.accdb)};DBQ=src/main/resources/ODBCTesting.accdb";

	public static void main(String[] args) throws Exception {

		dnsLessConnection();
	}
	
	static void dnsLessConnection() throws Exception {
		Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		Connection conn = DriverManager.getConnection(DNS_LESS_2007_URL);
		
		JDBCUtil.executeQuery(conn, "SELECT * FROM EmpData");
		
		JDBCUtil.executeQuery(conn, "SELECT * FROM EmpData_test");
		
		JDBCUtil.close(conn);
	}

}
