package org.apache.phoenix.examples.datatypes;

import java.util.ArrayList;
import java.util.List;

import org.apache.phoenix.schema.PColumn;
import org.apache.phoenix.schema.PColumnImpl;
import org.apache.phoenix.schema.PDataType;
import org.apache.phoenix.schema.PName;
import org.apache.phoenix.schema.PTableImpl;

public class PTableTest {

	public static void main(String[] args) {
		
		System.out.println(PDataType.VARCHAR);
		
//		PColumnImpl pcolumn = new PColumnImpl(PName.EMPTY_COLUMN_NAME.);
		
		List<PColumn> columns = new ArrayList<PColumn>();

//		PTableImpl ptableImpl = PTableImpl.makePTable(null, null, "tableName", null, null, System.currentTimeMillis(), null, null, null, columns, null, null, null, false, null, null, null, null, null, null, null, null);
	}

}
