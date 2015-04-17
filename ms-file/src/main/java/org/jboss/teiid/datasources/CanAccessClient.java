package org.jboss.teiid.datasources;

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Timestamp;

public class CanAccessClient {
	
	public static final String UCANACCESS_2007_URL = "jdbc:ucanaccess://src/main/resources/ODBCTesting.accdb";

	public static void main(String[] args) throws Exception {

		
		Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
		
		Connection conn= DriverManager.getConnection(UCANACCESS_2007_URL);
		
//		JDBCUtil.executeUpdate(conn, "CREATE TABLE DATATYPE_TEST (id LONG, c_yesno YESNO, c_byte BYTE, c_integer INTEGER, c_long LONG, c_single SINGLE, c_double DOUBLE, c_numeric numeric(24,5), c_currency CURRENCY, c_counter COUNTER, c_txt TEXT, c_ole OLE, c_memo MEMO, c_guid GUID, c_datatime DATETIME)");
		
		JDBCUtil.executeUpdate(conn, "INSERT INTO DATATYPE_TEST (id, c_yesno, c_byte, c_integer, c_long, c_single, c_double, c_numeric, c_currency, c_counter, c_txt, c_ole, c_memo, c_guid, c_datatime) VALUES( 10001, 1, 127, 5, 10000, 5.6666, 6.7, 0.100051, 4.55555, 10, 'This is TXT column', null, 'a', 'This is MEMO column', #10/03/2008 10:34:35 PM#)");
		
		long v_long = 10002;
		boolean v_boolean = true;
		byte v_byte = 127;
		int v_int = 5;
		double v_double = 5.6666;
		BigDecimal v_bigdecimal = new BigDecimal(4.5555);
		BigDecimal v_decimal = new BigDecimal(4);
		String v_string = "sting column";
		Blob v_blob = conn.createBlob();
		Timestamp v_timestramp = new Timestamp(new java.util.Date().getTime());
		
		PreparedStatement ps = conn.prepareStatement("INSERT INTO DATATYPE_TEST (id, c_yesno, c_byte, c_integer, c_long, c_single, c_double, c_numeric, c_currency, c_counter, c_txt, c_ole, c_memo, c_guid, c_datatime) VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
		ps.setLong(1, v_long);
		ps.setBoolean(2, v_boolean);
		ps.setByte(3, v_byte);
		ps.setInt(4, v_int);
		ps.setLong(5, v_long);
		ps.setDouble(6, v_double);
		ps.setDouble(7, v_double);
		ps.setBigDecimal(8, v_bigdecimal);
		ps.setBigDecimal(9, v_decimal);
		ps.setInt(10, v_int);
		ps.setString(11, v_string);
		ps.setBlob(12, v_blob);
		ps.setString(13, v_string);
		ps.setString(14, v_string);
		ps.setTimestamp(15, v_timestramp);
		ps.addBatch();
		ps.executeBatch();
		
		JDBCUtil.executeQuery(conn, "SELECT c_ole FROM DATATYPE_TEST");
		
		JDBCUtil.executeQuery(conn, "SELECT * FROM DATATYPE_TEST");
		
		JDBCUtil.executeUpdate(conn, "UPDATE DATATYPE_TEST SET c_txt = 'updated test' WHERE id = 10001");
		
		JDBCUtil.executeQuery(conn, "SELECT * FROM DATATYPE_TEST");
		
		JDBCUtil.executeUpdate(conn, "DELETE FROM DATATYPE_TEST WHERE id = 10001");
		
		JDBCUtil.executeQuery(conn, "SELECT * FROM DATATYPE_TEST");
		
		JDBCUtil.printTableColumn(conn, "SELECT * FROM DATATYPE_TEST");
		
		JDBCUtil.close(conn);
		
	}

}
