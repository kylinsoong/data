package org.jboss.teiid.datasources;

import java.sql.Connection;
import java.sql.DriverManager;

public class MSAccessJDBCUCanAccessClient {
	
	public static final String UCANACCESS_URL = "jdbc:ucanaccess://src/main/resources/ODBCTesting.mdb";
	
	public static final String UCANACCESS_2007_URL = "jdbc:ucanaccess://src/main/resources/ODBCTesting.accdb";


	public static void main(String[] args) throws Exception {

		Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
		
		Connection conn= DriverManager.getConnection(UCANACCESS_2007_URL);
		
//		JDBCUtil.executeQuery(conn, "SELECT * FROM EmpData");
//		
//		JDBCUtil.executeQuery(conn, "SELECT * FROM EmpData_test");
//		
//		JDBCUtil.printTableColumn(conn, "SELECT * FROM EmpData");
		
		System.out.println(conn.createBlob().getBinaryStream());
		
		JDBCUtil.close(conn);
	}

}
