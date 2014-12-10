package org.apache.phoenix.examples.mapping;

import org.apache.phoenix.examples.ExampleBase;

public class MappingExample extends ExampleBase {
	
	static final String HBASE_TABLE_MAPPING = "CREATE TABLE IF NOT EXISTS \"Customer\" (ROW_ID VARCHAR PRIMARY KEY, \"sales\".\"amount\" VARCHAR, \"sales\".\"product\" VARCHAR, \"customer\".\"city\" VARCHAR, \"customer\".\"name\" VARCHAR)";

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
