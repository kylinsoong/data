package org.apache.phoenix.client;

import java.sql.Connection;
import java.sql.DriverManager;

import org.apache.phoenix.examples.JDBCUtil;

public class JDBCClient {

	public static void main(String[] args) throws Exception {

		String zkQuorum = "127.0.0.1:2181";
		
		Class.forName("org.apache.phoenix.jdbc.PhoenixDriver");
		String url = "jdbc:phoenix:" + zkQuorum;
		String user = "sa";
		String pass = "sa";
		Connection conn = DriverManager.getConnection(url, user, pass);
		
		JDBCUtil.executeQuery(conn, "SELECT * FROM \"Customer\"");
		
		JDBCUtil.close(conn);
	}

}
