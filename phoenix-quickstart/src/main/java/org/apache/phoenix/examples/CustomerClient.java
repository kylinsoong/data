package org.apache.phoenix.examples;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;
import java.util.TimeZone;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

public class CustomerClient {
	
	public final static TimeZone DEFAULT_TIME_ZONE = TimeZone.getDefault();
	
	 static class DatabaseCalender extends ThreadLocal<Calendar> {
	    	private String timeZone;
	    	public DatabaseCalender(String tz) {
	    		this.timeZone = tz;
	    	}
	    	@Override
	    	protected Calendar initialValue() {
	            if(this.timeZone != null && this.timeZone.trim().length() > 0) {
	            	TimeZone tz = TimeZone.getTimeZone(this.timeZone);
	                if(!DEFAULT_TIME_ZONE.hasSameRules(tz)) {
	            		return Calendar.getInstance(tz);
	                }
	            }      		
	    		return Calendar.getInstance();
	    	}
	    };
	    
	    static String databaseTimeZone = DEFAULT_TIME_ZONE.toString();
	    
	    static DatabaseCalender databaseCalender = new DatabaseCalender(databaseTimeZone);
	    
	    public static Calendar getDatabaseCalendar() {
	    	return databaseCalender.get();
	    }
	
	static {
	      String pattern = "%d %-5p [%c] (%t) %m%n";
	      PatternLayout layout = new PatternLayout(pattern);
	      ConsoleAppender consoleAppender = new ConsoleAppender(layout);
	      Logger.getRootLogger().setLevel(Level.INFO);
	      Logger.getRootLogger().addAppender(consoleAppender);  
		}

	public static void main(String[] args) throws Exception {

		Connection conn = PhoenixUtils.getPhoenixConnection("127.0.0.1:2181");
		
		Statement stmt = conn.createStatement();
		
		ResultSet rs = stmt.executeQuery("SELECT g_0.\"ROW_ID\", g_0.\"q1\", g_0.\"q2\", g_0.\"q3\" FROM \"TimesTest\" AS g_0");
		
		
		java.util.Calendar cal = Calendar.getInstance();
		cal.setTimeZone(TimeZone.getDefault());
		
		while(rs.next()) {
//			System.out.println(rs.getDate(2, getDatabaseCalendar() ));
//			System.out.println(rs.getTime(3, getDatabaseCalendar()));
			System.out.println(rs.getTimestamp(4, cal));
		}
		
		JDBCUtil.close(conn);
	}

}
