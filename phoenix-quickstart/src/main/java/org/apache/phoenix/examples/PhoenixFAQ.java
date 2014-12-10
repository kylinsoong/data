package org.apache.phoenix.examples;

import java.sql.Connection;

/*
 * http://phoenix.apache.org/faq.html
 */
public class PhoenixFAQ extends ExampleBase{

	public static void main(String[] args) throws Exception {
		
//		helloWorld();

		mapExistingHBaseTable();
	}

	static void mapExistingHBaseTable() throws Exception {
		
		Connection conn = PhoenixUtils.getPhoenixConnection("127.0.0.1:2181");
		
		String sql = "CREATE VIEW IF NOT EXISTS \"Customer\"(pk VARCHAR PRIMARY KEY, \"customer\".\"city\" VARCHAR, \"customer\".\"name\" VARCHAR, \"sales\".\"amount\" VARCHAR, \"sales\".\"product\" VARCHAR)";
		JDBCUtil.executeUpdate(conn, sql);
		
		JDBCUtil.executeQuery(conn, "select * from \"Customer\"");
		
		
		JDBCUtil.close(conn);
	}

	/*
	 * I want to get started. Is there a Phoenix Hello World?
	 */
	static void helloWorld() throws Exception {

		Connection conn = PhoenixUtils.getPhoenixConnection("127.0.0.1:2181");
		
		JDBCUtil.executeUpdate(conn, "CREATE TABLE IF NOT EXISTS test (mykey integer not null primary key, mycolumn varchar)");
		
		JDBCUtil.executeUpdate(conn, "upsert into test values (1,'Hello')");
		JDBCUtil.executeUpdate(conn, "upsert into test values (2,'World!')");
		
		JDBCUtil.executeQuery(conn, "select * from test");
		
		JDBCUtil.close(conn);
	}
}
