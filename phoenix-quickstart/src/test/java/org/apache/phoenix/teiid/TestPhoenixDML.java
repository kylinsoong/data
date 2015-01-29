package org.apache.phoenix.teiid;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.sql.DataSource;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestPhoenixDML {
	
	static final String JDBC_DRIVER = "org.apache.phoenix.jdbc.PhoenixDriver";
    static final String JDBC_URL = "jdbc:phoenix:127.0.0.1:2181";
    static final String JDBC_USER = "";
    static final String JDBC_PASS = "";
    
    static Connection conn = null;
    
    static final String CUSTOMER = "CREATE TABLE IF NOT EXISTS \"Customer\"(\"ROW_ID\" VARCHAR PRIMARY KEY, \"customer\".\"city\" VARCHAR, \"customer\".\"name\" VARCHAR, \"sales\".\"amount\" VARCHAR, \"sales\".\"product\" VARCHAR)";
    static final String TYPES_TEST = "CREATE TABLE IF NOT EXISTS \"TypesTest\" (\"ROW_ID\" VARCHAR PRIMARY KEY, \"f\".\"q1\" VARCHAR, \"f\".\"q2\" VARBINARY, \"f\".\"q3\" VARCHAR, \"f\".\"q4\" BOOLEAN, \"f\".\"q5\" TINYINT, \"f\".\"q6\" TINYINT, \"f\".\"q7\" SMALLINT, \"f\".\"q8\" SMALLINT, \"f\".\"q9\" INTEGER, \"f\".\"q10\" INTEGER, \"f\".\"q11\" BIGINT, \"f\".\"q12\" BIGINT, \"f\".\"q13\" FLOAT, \"f\".\"q14\" FLOAT, \"f\".\"q15\" DOUBLE, \"f\".\"q16\" DECIMAL, \"f\".\"q17\" DECIMAL, \"f\".\"q18\" DATE, \"f\".\"q19\" TIME, \"f\".\"q20\" TIMESTAMP)";
    static final String TIMES_TEST = "CREATE TABLE IF NOT EXISTS \"TimesTest\"(\"ROW_ID\" VARCHAR PRIMARY KEY, \"f\".\"column1\" DATE, \"f\".\"column2\" TIME, \"f\".\"column3\" TIMESTAMP)";
    
    @BeforeClass
    public static void init() throws Exception {
    	DataSource ds = TestHBaseUtil.setupDataSource("java:/hbaseDS", JDBC_DRIVER, JDBC_URL, JDBC_USER, JDBC_PASS);
    	conn = ds.getConnection();
    	conn.setAutoCommit(true);
    	TestHBaseUtil.executeUpdate(conn, CUSTOMER);
    	TestHBaseUtil.executeUpdate(conn, TYPES_TEST);
    	TestHBaseUtil.executeUpdate(conn, TIMES_TEST);

    }
    
    @Test
    public void testMetaData() throws SQLException {
    	assertNotNull(conn);
    	DatabaseMetaData metadata = conn.getMetaData();
    	ResultSet rs = metadata.getTables(null, null, null, null);
    	Set<String> set = new HashSet<String>();
    	if (rs != null) {
    		while(rs.next()){
        		String name = rs.getString(3);
        		set.add(name);
    		}
    	}
    	assertTrue(set.contains("Customer"));
    	assertTrue(set.contains("TimesTest"));
    	assertTrue(set.contains("TypesTest"));
    }
    
    @Test
    public void testInsert() throws Exception {
    	TestHBaseUtil.executeUpdate(conn, "UPSERT INTO \"Customer\" VALUES('101', 'Los Angeles, CA', 'John White', '$400.00', 'Chairs')");
    	TestHBaseUtil.executeUpdate(conn, "UPSERT INTO \"Customer\" VALUES('102', 'Atlanta, GA', 'Jane Brown', '$200.00', 'Lamps')");
    	TestHBaseUtil.executeUpdate(conn, "UPSERT INTO \"Customer\" VALUES('103', 'Pittsburgh, PA', 'Bill Green', '$500.00', 'Desk')");
    	TestHBaseUtil.executeUpdate(conn, "UPSERT INTO \"Customer\" VALUES('104', 'St. Louis, MO', 'Jack Black', '$8000.00', 'Bed')");
    	TestHBaseUtil.executeUpdate(conn, "UPSERT INTO \"Customer\" VALUES('105', 'Los Angeles, CA', 'John White', '$400.00', 'Chairs')");
    	TestHBaseUtil.executeUpdate(conn, "UPSERT INTO \"Customer\" VALUES('108', 'Beijing', 'Kylin Soong', '$8000.00', 'Crystal Orange')");
    }
    
    @Test
    public void testBatchedInsert() throws SQLException {
        TestHBaseUtil.executeBatchedUpdate(conn, "UPSERT INTO \"Customer\" VALUES (?, ?, ?, ?, ?)", 2);
        TestHBaseUtil.executeBatchedUpdate(conn, "UPSERT INTO \"Customer\"(\"ROW_ID\", \"city\", \"name\", \"amount\", \"product\") VALUES (?, ?, ?, ?, ?)", 2);
        TestHBaseUtil.executeBatchedUpdate(conn, "UPSERT INTO \"Customer\" VALUES (?, ?, ?, ?, ?)", 1);
    }
    
    @Test
    public void testSelect() throws Exception {
        TestHBaseUtil.executeQuery(conn, "SELECT * FROM \"Customer\"");
        TestHBaseUtil.executeQuery(conn, "SELECT \"city\", \"amount\" FROM \"Customer\"");
        TestHBaseUtil.executeQuery(conn, "SELECT DISTINCT \"city\" FROM \"Customer\"");
        TestHBaseUtil.executeQuery(conn, "SELECT \"city\", \"amount\" FROM \"Customer\" WHERE \"ROW_ID\"='105'");
    }
    
    @Test
    public void testSelectOrderBy() throws Exception {
        TestHBaseUtil.executeQuery(conn, "SELECT * FROM \"Customer\" ORDER BY \"ROW_ID\"");
        TestHBaseUtil.executeQuery(conn, "SELECT * FROM \"Customer\" ORDER BY \"ROW_ID\" ASC");
        TestHBaseUtil.executeQuery(conn, "SELECT * FROM \"Customer\" ORDER BY \"ROW_ID\" DESC");
        TestHBaseUtil.executeQuery(conn, "SELECT * FROM \"Customer\" ORDER BY \"name\", \"city\" DESC");
    }
    
    @Test
    public void testSelectGroupBy() throws Exception {
        TestHBaseUtil.executeQuery(conn, "SELECT COUNT(\"ROW_ID\") FROM \"Customer\" WHERE \"name\"='John White'");
        TestHBaseUtil.executeQuery(conn, "SELECT \"name\", COUNT(\"ROW_ID\") FROM \"Customer\" GROUP BY \"name\"");
        TestHBaseUtil.executeQuery(conn, "SELECT \"name\", COUNT(\"ROW_ID\") FROM \"Customer\" GROUP BY \"name\" HAVING COUNT(\"ROW_ID\") > 1");
        TestHBaseUtil.executeQuery(conn, "SELECT \"name\", \"city\", COUNT(\"ROW_ID\") FROM \"Customer\" GROUP BY \"name\", \"city\"");
        TestHBaseUtil.executeQuery(conn, "SELECT \"name\", \"city\", COUNT(\"ROW_ID\") FROM \"Customer\" GROUP BY \"name\", \"city\" HAVING COUNT(\"ROW_ID\") > 1");
    }
    
    @Test
    public void testSelectLimit() throws Exception {
        TestHBaseUtil.executeQuery(conn, "SELECT * FROM \"Customer\" LIMIT 3");
        TestHBaseUtil.executeQuery(conn, "SELECT * FROM \"Customer\" ORDER BY \"ROW_ID\" DESC LIMIT 3");
    }
    
    
    @Test
    public void testConditionAndOr() throws Exception {
        TestHBaseUtil.executeQuery(conn, "SELECT * FROM \"Customer\" WHERE \"ROW_ID\"='105' OR \"name\"='John White'");
        TestHBaseUtil.executeQuery(conn, "SELECT * FROM \"Customer\" WHERE \"ROW_ID\"='105' AND \"name\"='John White'");
        TestHBaseUtil.executeQuery(conn, "SELECT * FROM \"Customer\" WHERE \"ROW_ID\"='105' AND (\"name\"='John White' OR \"name\"='Kylin Soong')");
    }
    
    @Test
    public void testConditionComparison() throws Exception {
        TestHBaseUtil.executeQuery(conn, "SELECT * FROM \"Customer\" WHERE \"ROW_ID\" = '108'");
        TestHBaseUtil.executeQuery(conn, "SELECT * FROM \"Customer\" WHERE \"ROW_ID\" > '108'");
        TestHBaseUtil.executeQuery(conn, "SELECT * FROM \"Customer\" WHERE \"ROW_ID\" < '108'");
        TestHBaseUtil.executeQuery(conn, "SELECT * FROM \"Customer\" WHERE \"ROW_ID\" >= '108'");
        TestHBaseUtil.executeQuery(conn, "SELECT * FROM \"Customer\" WHERE \"ROW_ID\" <= '108'");
        TestHBaseUtil.executeQuery(conn, "SELECT * FROM \"Customer\" WHERE \"ROW_ID\" BETWEEN '105' AND '108'");
        TestHBaseUtil.executeQuery(conn, "SELECT * FROM \"Customer\" WHERE \"ROW_ID\" LIKE '10%'");
        TestHBaseUtil.executeQuery(conn, "SELECT * FROM \"Customer\" WHERE \"ROW_ID\" IN ('105', '106')");
    }
    
    @Test
    public void testFunctions() throws Exception {
        TestHBaseUtil.executeQuery(conn, "SELECT COUNT(\"ROW_ID\") AS totalCount FROM \"Customer\" WHERE \"name\" = 'Kylin Soong'");
    }
    
    @Test
    public void testTimesTypes() throws Exception {
        TestHBaseUtil.executeQuery(conn, "SELECT * FROM \"TimesTest\"");
        
        Date date = new Date(new java.util.Date().getTime());
        Time time = new Time(new java.util.Date().getTime());
        Timestamp timestramp = new Timestamp(new java.util.Date().getTime());
        timestramp.setNanos(100);
        
        PreparedStatement pstmt = null ;
        try {
            pstmt = conn.prepareStatement("UPSERT INTO \"TimesTest\" VALUES (?, ?, ?, ?)");
            pstmt.setString(1, "100");
            pstmt.setDate(2, date);
            pstmt.setTime(3, time);
            pstmt.setTimestamp(4, timestramp);
            pstmt.addBatch();
            pstmt.executeBatch();
        } catch (SQLException e) {
            throw e;
        } finally {
            TestHBaseUtil.close(pstmt);
        }
        
        TestHBaseUtil.executeQuery(conn, "SELECT * FROM \"TimesTest\"");
        
    }
    
    @AfterClass 
    public static void tearDown() {
    	TestHBaseUtil.close(conn);
    }
    
    public static void main(String[] args) throws Exception {
    	init();
    	
    	
    	
    	tearDown();
    }
}
