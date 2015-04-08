package org.jboss.teeid.datasources;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MSAccessJDBCODBCClient {
	
	public static final String DNS_LESS_URL = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};Dbq=src/main/resources/ODBCTesting.mdb";

	public static void main(String[] args) throws ClassNotFoundException, SQLException {

		dnsLessConnection();
	}

	static void dnsLessConnection() throws ClassNotFoundException, SQLException {
		Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		Connection con = DriverManager.getConnection(DNS_LESS_URL);
	}

}
