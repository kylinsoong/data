package com.vertica.jdbc.client;

import static com.vertica.jdbc.client.JDBCUtils.execute;

import java.sql.Connection;

public class VMartClient {

    public static void main(String[] args) throws Exception {

        String driver = "com.vertica.jdbc.Driver";
        String url = "jdbc:vertica://10.66.128.62:5433/VMart";
        String user = "dbadmin";
        String pass = "redhat";
        
        Connection conn = JDBCUtils.getDriverConnection(driver, url, user, pass);
        
        funtionExample(conn);
        
//        execute(conn, "SELECT employee_key, employee_gender, employee_first_name, employee_last_name, hire_date, employee_city, job_title FROM employee_dimension WHERE job_title = 'Regional Manager'", false);
        
        JDBCUtils.close(conn);
    }

    static void funtionExample(Connection conn) throws Exception {

        execute(conn, "SELECT ASCII('A'), BIT_LENGTH('abc'::varbinary), BITCOUNT('"+ "1".getBytes() + "')", false);
        
        execute(conn, "SELECT BITSTRING_TO_BINARY('0110000101100010'), BTRIM('xyxtrimyyx', 'xy'), INSERT ('123456',1,3,'abc')", false);
    }

}
