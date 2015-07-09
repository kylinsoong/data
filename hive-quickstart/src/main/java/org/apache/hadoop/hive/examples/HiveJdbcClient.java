package org.apache.hadoop.hive.examples;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class HiveJdbcClient {
    
    private static String driverName = "org.apache.hive.jdbc.HiveDriver";

    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        Class.forName(driverName);
        
        Connection conn = DriverManager.getConnection("jdbc:hive2://192.168.1.105:10000/default", "hive", "");
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM employee");
        while(rs.next()) {
            System.out.println(rs.getInt(1));
        }
        
        rs.close();
        stmt.close();
        conn.close();
        
    }

}
