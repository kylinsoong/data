package org.apache.phoenix.examples.nanos;

import java.sql.Timestamp;

public class Test {

	public static void main(String[] args) {

		int nans = 999999999 + 1;
		
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		ts.setNanos(0);
		System.out.println(ts.getNanos());
	}

}
