package org.apache.phoenix.examples;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JDBCConnectionTest {

	@SuppressWarnings("static-access")
	public static void main(String[] args) throws SQLException, InterruptedException {
		
		List<Connection> list = new ArrayList<Connection>();
		
		for(int i = 0 ; i < 10 ; i ++) {
			Connection conn = DriverManager.getConnection("jdbc:phoenix:127.0.0.1:2181");
			list.add(conn);
		}

		
		System.out.println(list);
		
		Thread.currentThread().sleep(Long.MAX_VALUE);
	}

}
