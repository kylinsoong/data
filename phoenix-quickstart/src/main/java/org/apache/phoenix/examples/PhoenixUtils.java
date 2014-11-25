package org.apache.phoenix.examples;

import java.sql.Connection;
import java.sql.DriverManager;

public class PhoenixUtils {

	public static Connection getPhoenixConnection(String zkQuorum) throws Exception {
		Class.forName("org.apache.phoenix.jdbc.PhoenixDriver");
		String connectionURL = "jdbc:phoenix:" + zkQuorum;
		Connection r = DriverManager.getConnection(connectionURL);
		r.setAutoCommit(true);
		return r;
	}
}
