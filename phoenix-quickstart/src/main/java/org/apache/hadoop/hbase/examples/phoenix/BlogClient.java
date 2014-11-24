package org.apache.hadoop.hbase.examples.phoenix;

import java.sql.Connection;

public class BlogClient {

	public static void main(String[] args) throws Exception {
		
		Connection conn = PhoenixUtils.getPhoenixConnection("127.0.0.1:2181");
		JDBCUtil.executeQuery(conn, "SELECT * FROM STOCK_SYMBOL");
		JDBCUtil.close(conn);
	}
}
