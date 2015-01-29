package org.apache.phoenix.examples.nanos;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class TestNanos {

	public static void main(String[] args) {
		
		List<Integer> list = new ArrayList<Integer>();

		for(int i = 0 ; i < 100 ; i ++) {
			Timestamp x = new Timestamp(new java.util.Date().getTime());
			
			Calendar cal = Calendar.getInstance();
			cal.setTimeZone(TimeZone.getDefault());
			cal.setTime(x);
			
			long millis = cal.getTimeInMillis();
			int nanos = x.getNanos();
			Timestamp ts = new Timestamp(millis);
			ts.setNanos(ts.getNanos() + nanos);
			try {
				ts.setNanos(ts.getNanos() + nanos);
			} catch (Exception e) {
				list.add(i);
				e.printStackTrace();
			}
			System.out.println(i + " Success");
		}
		
		System.out.println(list);
	}

}
