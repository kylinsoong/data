package org.apache.hadoop.hbase.client;

import java.io.IOException;

public class TestClient {

	public static void main(String[] args) throws IOException {

		HBaseUtil.newInstance().scanTable("TEST");
	}

}
