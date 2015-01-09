package org.apache.phoenix.examples;

import java.sql.Connection;

public class JDBCConnAutoCommitTest {

	public static void main(String[] args) throws Exception {

		Connection conn = JDBCUtil.getDriverConnection("", "", "", "");
	}

}
