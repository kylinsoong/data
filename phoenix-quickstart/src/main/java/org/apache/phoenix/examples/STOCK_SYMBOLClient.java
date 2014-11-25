package org.apache.phoenix.examples;

import java.sql.Connection;

public class STOCK_SYMBOLClient {

	public static void main(String[] args) throws Exception {
		
		Connection conn = PhoenixUtils.getPhoenixConnection("127.0.0.1:2181");
		JDBCUtil.executeQuery(conn, "SELECT * FROM STOCK_SYMBOL");
		JDBCUtil.close(conn);
	}
}
