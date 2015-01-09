package org.apache.phoenix.datasource;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.sql.DataSource;

import bitronix.tm.resource.jdbc.PoolingDataSource;

public class TestDataSource {
	
	static {
		System.setProperty(Context.INITIAL_CONTEXT_FACTORY, "bitronix.tm.jndi.BitronixInitialContextFactory");
	}
	
	static PoolingDataSource pds = null ;
	
	static DataSource setupDataSource() {
		if (null != pds)
			return pds;
		
		pds = new PoolingDataSource();
		pds.setUniqueName("java:/accounts-ds");
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

	public static void main(String[] args) throws SQLException {

		DataSource ds = setupDataSource();
		Connection conn = ds.getConnection();
		System.out.println("Connection Commit Mode: " + conn.getAutoCommit());
		conn.close();
	}

}
