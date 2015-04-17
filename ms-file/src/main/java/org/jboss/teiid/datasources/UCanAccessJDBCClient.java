package org.jboss.teiid.datasources;

import static org.jboss.teiid.datasources.JDBCUtil.executeQuery;
import static org.jboss.teiid.datasources.JDBCUtil.printTableColumn;

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class UCanAccessJDBCClient {
	
	public static final String UCANACCESS_URL = "jdbc:ucanaccess://src/main/resources/ODBCTesting.mdb";
	
	public static final String UCANACCESS_2007_URL = "jdbc:ucanaccess://src/test/resources/ucanaccess/ODBCTesting.accdb";


	public static void main(String[] args) throws Exception {

		Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
		
		Connection conn= DriverManager.getConnection(UCANACCESS_2007_URL);
		
		
//		testMetadata(conn);
		
//		testAccessLike(conn);
		
		testAggregateFunctions(conn);
		
//		testFunctions(conn);
		
//		testDataType(conn);
		
		JDBCUtil.close(conn);
	}


	 static void testMetadata(Connection conn) throws SQLException {
		 
		 DatabaseMetaData metadata = conn.getMetaData();
		 String fullVersion = metadata.getDatabaseProductVersion();
		 System.out.println(fullVersion);
	}


	static void testFunctions(Connection conn) throws Exception {
		executeQuery(conn, "SELECT ASC('A') FROM T20");
		executeQuery(conn, "SELECT switch('1'='1',1,false,2,true, 1 )FROM T20");
		executeQuery(conn, "SELECT atn(2) FROM T20");
		executeQuery(conn, "SELECT nz(null,'lampredotto'),nz('turtelaz','lampredotto'), nz(null, 1.5),nz(2, 2) FROM T20");
		executeQuery(conn, "SELECT date() FROM T20");
		executeQuery(conn, "SELECT time() FROM T20");
	}


	static void testAggregateFunctions(Connection conn) throws Exception {
//		initAggregateFunctionsData(conn);
		
//		executeQuery(conn, "SELECT * FROM T20");
//		executeQuery(conn, "SELECT DCount('*','T20','id > 100') FROM T20");
//		executeQuery(conn, "SELECT id, DCount('descr','T20','id > 100') FROM T20");
//		executeQuery(conn, "SELECT id, DCount('*','T20','id > 10000') FROM T20");
		
		String sql = "SELECT DCount('*','T20','id > 100')";
		executeQuery(conn, sql);
		printTableColumn(conn, sql);
		
		sql = "SELECT DSum('id', 'T20', '1=1')";
		executeQuery(conn, sql);
		printTableColumn(conn, sql);
		
		sql = "SELECT DMax('id', 'T20')";
		executeQuery(conn, sql);
		printTableColumn(conn, sql);
		
		sql = "SELECT DMin('id', 'T20')";
		executeQuery(conn, sql);
		printTableColumn(conn, sql);
		
		sql = "SELECT DAvg('id', 'T20')";
		executeQuery(conn, sql);
		printTableColumn(conn, sql);
		
		sql = "SELECT DFirst('id', 'T20')";
		executeQuery(conn, sql);
		printTableColumn(conn, sql);
		
		sql = "SELECT DLast('descr', 'T20')";
		executeQuery(conn, sql);
		printTableColumn(conn, sql);
	}
	
	static void testDataType(Connection conn) throws Exception {

		JDBCUtil.executeUpdate(conn, "CREATE TABLE DATATYPE_TEST (id LONG, c_yesno YESNO, c_byte BYTE, c_integer INTEGER, c_long LONG, c_single SINGLE, c_double DOUBLE, c_numeric numeric(24,5), c_currency CURRENCY, c_counter COUNTER, c_txt TEXT, c_ole OLE, c_memo MEMO, c_guid GUID, c_datatime DATETIME)");
		
		JDBCUtil.executeUpdate(conn, "INSERT INTO DATATYPE_TEST (id, c_yesno, c_byte, c_integer, c_long, c_single, c_double, c_numeric, c_currency, c_counter, c_txt, c_ole, c_memo, c_guid, c_datatime) VALUES( 10001, 1, 127, 5, 10000, 5.6666, 6.7, 0.100051, 4.55555, 10, 'This is TXT column', null, 'a', 'This is MEMO column', #10/03/2008 10:34:35 PM#)");
		
		long v_long = 10001;
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
		
		JDBCUtil.executeQuery(conn, "SELECT * FROM DATATYPE_TEST");
		
		JDBCUtil.executeUpdate(conn, "UPDATE DATATYPE_TEST SET c_txt = 'updated test' WHERE id = 10001");
		
		JDBCUtil.executeQuery(conn, "SELECT * FROM DATATYPE_TEST");
		
		JDBCUtil.executeUpdate(conn, "DELETE FROM DATATYPE_TEST WHERE id = 10001");
		
//		JDBCUtil.executeQuery(conn, "SELECT * FROM DATATYPE_TEST");
		
		JDBCUtil.printTableColumn(conn, "SELECT * FROM DATATYPE_TEST");
	}



	static void testAccessLike(Connection conn) throws Exception {
		
//		initAccessLikeData(conn);
		
		JDBCUtil.executeQuery(conn, "select * from T21");
		JDBCUtil.executeQuery(conn, "select * from T21 where descr like 'a[*]a' order by ID");
		JDBCUtil.executeQuery(conn, "select * from T21 where descr like \"a*a\"  AND '1'='1' and (descr) like \"a*a\" ORDER BY ID");
		JDBCUtil.executeQuery(conn, "select * from T21 where descr like 'a%a'");
		JDBCUtil.executeQuery(conn, "select * from T21 where descr like 'P[A-F]###'");
		JDBCUtil.executeQuery(conn, "select * from T21 where (T21.descr\n) \nlike 'P[!A-F]###' AND '1'='1'");
		JDBCUtil.executeQuery(conn, "select * from T21 where descr='aba'");
	}

	static void initAggregateFunctionsData(Connection conn) throws Exception {
		JDBCUtil.executeUpdate(conn, "CREATE TABLE T20 (id INTEGER,descr text(400), num numeric(12,3), date0 datetime)");
		JDBCUtil.executeUpdate(conn, "INSERT INTO T20 (id,descr,num,date0)  VALUES( 1234,'Show must go off',-1110.55446,#11/22/2003 10:42:58 PM#)");
		JDBCUtil.executeUpdate(conn, "INSERT INTO T20 (id,descr,num,date0)  VALUES( 12344,'Show must go up and down',-113.55446,#11/22/2003 10:42:58 PM#)");
	}
	
	static void initAccessLikeData(Connection conn) throws Exception {
		
		JDBCUtil.executeUpdate(conn, "CREATE TABLE T21 (id counter primary key, descr memo)");
		
		JDBCUtil.executeUpdate(conn, "INSERT INTO T21 (descr)  VALUES( 'dsdsds')");
		JDBCUtil.executeUpdate(conn, "INSERT INTO T21 (descr)  VALUES( 'aa')");
		JDBCUtil.executeUpdate(conn, "INSERT INTO T21 (descr)  VALUES( 'aBa')");
		JDBCUtil.executeUpdate(conn, "INSERT INTO T21 (descr)  VALUES( 'aBBBa')");
		JDBCUtil.executeUpdate(conn, "INSERT INTO T21 (descr)  VALUES( 'PB123')");
		
		JDBCUtil.executeUpdate(conn, "INSERT INTO T21 (descr)  VALUES( 'PZ123')");
		JDBCUtil.executeUpdate(conn, "INSERT INTO T21 (descr)  VALUES( 'a*a')");
		JDBCUtil.executeUpdate(conn, "INSERT INTO T21 (descr)  VALUES( 'A*a')");
		JDBCUtil.executeUpdate(conn, "INSERT INTO T21 (descr)  VALUES( '#')");
		JDBCUtil.executeUpdate(conn, "INSERT INTO T21 (descr)  VALUES( '*')");
	}

}
