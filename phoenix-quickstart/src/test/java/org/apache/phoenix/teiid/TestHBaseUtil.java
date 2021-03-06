package org.apache.phoenix.teiid;

import java.math.BigDecimal;
import java.sql.*;

import javax.sql.DataSource;

import bitronix.tm.resource.jdbc.PoolingDataSource;

@SuppressWarnings("nls")
public class TestHBaseUtil {
   
    
    public static Connection getDriverConnection(String driver, String url, String user, String pass) throws Exception {
        Class.forName(driver);
        return DriverManager.getConnection(url, user, pass); 
    }
    
    static PoolingDataSource pds = null ;
    
    public static DataSource setupDataSource(String jndiName) {
        if (null != pds)
            return pds;
        
        pds = new PoolingDataSource();
        pds.setUniqueName(jndiName);
        pds.setClassName("bitronix.tm.resource.jdbc.lrc.LrcXADataSource");
        pds.setMaxPoolSize(5);
        pds.setAllowLocalTransactions(true);
        pds.getDriverProperties().put("user", "");
        pds.getDriverProperties().put("password", "");
        pds.getDriverProperties().put("url", "jdbc:phoenix:127.0.0.1:2181");
        pds.getDriverProperties().put("driverClassName", "org.apache.phoenix.jdbc.PhoenixDriver");
        pds.init();
        
        return pds;
    }
    
    public static DataSource setupDataSource(String jndiName, String driver, String url, String user, String pass) {
        if (null != pds)
            return pds;
        
        pds = new PoolingDataSource();
        pds.setUniqueName(jndiName);
        pds.setClassName("bitronix.tm.resource.jdbc.lrc.LrcXADataSource");
        pds.setMaxPoolSize(5);
        pds.setAllowLocalTransactions(true);
        pds.getDriverProperties().put("user", user);
        pds.getDriverProperties().put("password", pass);
        pds.getDriverProperties().put("url", url);
        pds.getDriverProperties().put("driverClassName", driver);
        pds.init();
        
        return pds;
    }
    
    public static void executeBatchedUpdateDataType(Connection conn, String sql) throws SQLException {
        
        System.out.println("Update SQL: " + sql);
        
        byte b = 127;
        short s = 10000;
        long l = 1000000;
        float f = 3.6f;
        double d = 3.6;
        int in = 100;
        BigDecimal decimal = new BigDecimal(l);
        Date date = new Date(new java.util.Date().getTime());
        Time time = new Time(new java.util.Date().getTime());
        Timestamp timestramp = new Timestamp(new java.util.Date().getTime());
        
        PreparedStatement pstmt = null ;
        try {
            pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, "10001");
                pstmt.setString(2, "varchar");
                pstmt.setBytes(3, "varbinary".getBytes());
                pstmt.setString(4, "C");
                pstmt.setBoolean(5, Boolean.FALSE);
                pstmt.setByte(6, b);
                pstmt.setByte(7, b);
                pstmt.setShort(8, s);
                pstmt.setShort(9, s);
                pstmt.setInt(10, in);
                pstmt.setInt(11, in);
                pstmt.setLong(12, l);
                pstmt.setLong(13, l);
                pstmt.setFloat(14, f);
                pstmt.setFloat(15, f);
                pstmt.setDouble(16, d);
                pstmt.setBigDecimal(17, decimal);
                pstmt.setBigDecimal(18, decimal);
                pstmt.setDate(19, date);
                pstmt.setTime(20, time);
                pstmt.setTimestamp(21, timestramp);
                pstmt.addBatch();
            pstmt.executeBatch();
            if(!conn.getAutoCommit()) {
                conn.commit();
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            close(pstmt);
        }
        
    }
    
    public static void executeBatchedUpdateDataType(Connection conn, String sql, int size) throws SQLException {
        
        System.out.println("Update SQL: " + sql);
        byte b = 127;
        short s = 10000;
        long l = 1000000;
        float f = 3.6f;
        double d = 3.6;
        BigDecimal decimal = new BigDecimal(l);
        Date date = new Date(new java.util.Date().getTime());
        Time time = new Time(new java.util.Date().getTime());
        Timestamp timestramp = new Timestamp(new java.util.Date().getTime());
        
        PreparedStatement pstmt = null ;
        try {
            pstmt = conn.prepareStatement(sql);
            for(int i = 0 ; i < size ; i ++) {
                pstmt.setString(1, 100 + i + "");
                pstmt.setString(2, "varchar");
                pstmt.setBytes(3, "varbinary".getBytes());
                pstmt.setString(4, "C");
                pstmt.setBoolean(5, Boolean.FALSE);
                pstmt.setByte(6, b);
                pstmt.setByte(7, b);
                pstmt.setShort(8, s);
                pstmt.setShort(9, s);
                pstmt.setInt(10, i);
                pstmt.setInt(11, i);
                pstmt.setLong(12, l);
                pstmt.setLong(13, l);
                pstmt.setFloat(14, f);
                pstmt.setFloat(15, f);
                pstmt.setDouble(16, d);
                pstmt.setBigDecimal(17, decimal);
                pstmt.setBigDecimal(18, decimal);
                pstmt.setDate(19, date);
                pstmt.setTime(20, time);
                pstmt.setTimestamp(21, timestramp);
                pstmt.addBatch();
            }
            pstmt.executeBatch();
            if(!conn.getAutoCommit()) {
                conn.commit();
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            close(pstmt);
        }
        
    }
    
    public static void executeBatchedUpdate(Connection conn, String sql, int size) throws SQLException {

        System.out.println("Update SQL: " + sql);
        
        PreparedStatement pstmt = null ;
        try {
            pstmt = conn.prepareStatement(sql);
            for(int i = 0 ; i < size ; i ++) {
                pstmt.setString(1, 126 + i + "");
                pstmt.setString(2, "Beijng");
                pstmt.setString(3, "Kylin Soong");
                pstmt.setString(4, "$8000.00");
                pstmt.setString(5, "Crystal Orange");
                pstmt.addBatch();
            }
            pstmt.executeBatch();
            if(!conn.getAutoCommit()) {
                conn.commit();
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            close(pstmt);
        }
    }

    public static void close(Connection conn) {

        if(null != conn) {
            try {
                conn.close();
                conn = null;
            } catch (SQLException e) {
                
            }
        }
    }
    
    public static void close(Statement stmt) {

        if(null != stmt) {
            try {
                stmt.close();
                stmt = null;
            } catch (SQLException e) {
                
            }
        }
    }
    
    public static void close(ResultSet rs, Statement stmt) {

        if (null != rs) {
            try {
                rs.close();
                rs = null;
            } catch (SQLException e) {
            }
        }
        
        if(null != stmt) {
            try {
                stmt.close();
                stmt = null;
            } catch (SQLException e) {
            }
        }
    }
    
    public static void printTableColumn(Connection conn, String sql) throws Exception {
        Statement stmt = null;
        ResultSet rs = null;
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            ResultSetMetaData metadata = rs.getMetaData();
            int columns = metadata.getColumnCount();
            for(int i = 1 ; i <= columns ; i ++) {
                System.out.print(metadata.getColumnName(i) + "/" + metadata.getColumnTypeName(i) + "  ");
            }
        } catch (Exception e) {
            throw e ;
        } finally {
            close(rs, stmt);
        }
    }
    
    public static int countResults(Connection conn, String sql) throws Exception{
        
        int count = 0;
        
        Statement stmt = null;
        ResultSet rs = null;
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while(rs.next()) {
                count ++ ;
            }
        } catch (Exception e) {
            throw e ;
        } finally {
            close(rs, stmt);
        }
        
        return count ;
        
    }
    
    public static Object query(Connection conn, String sql) throws Exception {
        
        Object result = null;
        
        Statement stmt = null;
        ResultSet rs = null;
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            rs.next();
            result = rs.getString(1);
        } catch (Exception e) {
            throw e ;
        } finally {
            close(rs, stmt);
        }
        
        return result ;
    }
    
    public static void executeQuery(Connection conn, String sql) throws Exception {
        
        System.out.println("Query SQL: " + sql);
        
        Statement stmt = null;
        ResultSet rs = null;
        
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            ResultSetMetaData metadata = rs.getMetaData();
            int columns = metadata.getColumnCount();
            for (int row = 1; rs.next(); row++) {
                System.out.print(row + ": ");
                for (int i = 0; i < columns; i++) {
                    if (i > 0) {
                        System.out.print(", ");
                    }
                    System.out.print(rs.getObject(i + 1));
                }
                System.out.println();
            }
        } catch (Exception e) {
            throw e ;
        } finally {
            close(rs, stmt);
        }
        
        System.out.println();
        
    }

    public static boolean executeUpdate(Connection conn, String sql) throws Exception {
        
        System.out.println("Update SQL: " + sql);
        
        Statement stmt = null;
        
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            throw e;
        } finally {
            close(stmt);
        }
        
        return true ;
    }

    public static void executeCallable(Connection conn, String sql) throws Exception {
        
        System.out.println("Query SQL: " + sql);
        
        CallableStatement cStmt = null;
        ResultSet rs = null;
        
        try {
            cStmt = conn.prepareCall(sql);
            boolean hadResults = cStmt.execute();
            while(hadResults) {
                rs = cStmt.getResultSet();
                int columns = rs.getMetaData().getColumnCount();
                for (int row = 1; rs.next(); row++) {
                    System.out.print(row + ": ");
                    for (int i = 0; i < columns; i++) {
                        if (i > 0) {
                            System.out.print(", ");
                        }
                        System.out.print(rs.getObject(i + 1));
                    }
                    System.out.println();
                }
                rs.close();
                hadResults = cStmt.getMoreResults();
            }
        } catch (Exception e) {
            throw e ;
        } finally {
            close(rs, cStmt);
        }
        
        System.out.println();
        
    }

    public static void insertTestData(Connection conn) throws Exception {
        executeUpdate(conn, "INSERT INTO Customer VALUES('101', 'Los Angeles, CA', 'John White', '$400.00', 'Chairs')");
        executeUpdate(conn, "INSERT INTO Customer VALUES('102', 'Atlanta, GA', 'Jane Brown', '$200.00', 'Lamps')");
        executeUpdate(conn, "INSERT INTO Customer VALUES('103', 'Pittsburgh, PA', 'Bill Green', '$500.00', 'Desk')");
        executeUpdate(conn, "INSERT INTO Customer VALUES('104', 'St. Louis, MO', 'Jack Black', '$8000.00', 'Bed')");
        executeUpdate(conn, "INSERT INTO Customer VALUES('105', 'Los Angeles, CA', 'John White', '$400.00', 'Chairs')");
    }

}
