package org.apache.hadoop.hbase.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.util.Bytes;

public class HBaseUtil {
	
	public static HBaseUtil getInstance() {
		return new HBaseUtil();
	}
	
	public static HBaseUtil newInstance() {
		return new HBaseUtil();
	}
	
	public void scanTable(String name) throws IOException {
		
		System.out.println("Scan table '" + name + "'");
		
		Configuration conf = HBaseConfiguration.create();
		HTable table = new HTable(conf, name);
		Scan scan = new Scan();
		ResultScanner rs = table.getScanner(scan);
		try {
			boolean isFirst = true;
			Table tables = new Table();
			for (Result r = rs.next(); r != null; r = rs.next()){
				initTable(r, tables);
//				for(Cell cell : r.rawCells()) {
//					tables.addCell(CellUtil.cloneRow(cell), CellUtil.cloneValue(cell));
//				}
			}
			
			isFirst = true;
			
			System.out.println(tables);
			
		} finally {
			  rs.close();
		}
		table.close();
	}
	
	
	boolean isFirst = true;
	
	private void initTable(Result result, Table tables) {
				
		NavigableMap<byte[], NavigableMap<byte[], NavigableMap<Long, byte[]>>> valueMap = result.getMap();
		for(byte[] key : valueMap.keySet()) {
			NavigableMap<byte[], NavigableMap<Long, byte[]>> value = valueMap.get(key);
			for(byte[] qualifier : value.keySet()) {
				NavigableMap<Long, byte[]> cell = value.get(qualifier);
				for(Long timestamp : cell.keySet()) {
					tables.addCell(result.getRow(), cell.get(timestamp));
				}
			}
		}
	}

	public void tableFormatOutput(Result result){
		for(Cell cell : result.rawCells()) {
			System.out.print(CellUtil.cloneRow(cell) + "   ");
		}
		System.out.println();
	}
	
	private class Table {
		int keySize = 0;
		int cellSize = 0;
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		
		public void addCell(byte[] row, byte[] cell) {
			
			String rowid = new String(row);
			if(rowid.length() > keySize) {
				keySize = rowid.length();
			}
			
			String cellval = new String(cell);
			if(cellval.length() > cellSize) {
				cellSize = cellval.length();
			}
			
			if(map.get(rowid) == null) {
				List<String> celllist = new ArrayList<String>();
				map.put(rowid, celllist);
			} else {
				map.get(rowid).add(cellval);
			}
		}
		
		public void addCell(String row, String cell) {
			
			if(row.length() > keySize) {
				keySize = row.length();
			}
			
			if(cell.length() > cellSize) {
				cellSize = cell.length();
			}
			
			if(map.get(row) == null) {
				List<String> celllist = new ArrayList<String>();
				map.put(row, celllist);
			} else {
				map.get(row).add(cell);
			}
		}

		public String toString() {
			StringBuffer sb = new StringBuffer();
			
			for(String key : map.keySet()){
				sb.append(getKey(key) + "   ");
				for(String cell : map.get(key)){
					sb.append(getCell(cell) + "   ");
				}
				sb.append("\n");
			}
			
			return sb.toString();
		}
		
		private String getCell(String cell) {
			int length = cell.length();
			for(int i = 0 ; i < keySize - length ; i ++) {
				cell = cell + " ";
			}
			return cell;
		}

		private String getKey(String key) {
			int length = key.length();
			for(int i = 0 ; i < keySize - length ; i ++) {
				key = key + " ";
			}
			return key;
		}
	}
	
	

	public void treeFormatOutput(Result result) {

		String row = Bytes.toString(result.getRow());
		System.out.println(row);
		for(byte[] key : result.getMap().keySet()) {
			String family = Bytes.toString(key);
			System.out.println("\t" + family);
			NavigableMap<byte[], NavigableMap<Long, byte[]>> value = result.getMap().get(key);
			for(byte[] qualifier : value.keySet()) {
				System.out.println("\t\t" + Bytes.toString(qualifier));
				NavigableMap<Long, byte[]> cell = value.get(qualifier);
				for(Long timestamp : cell.keySet()) {
					String cellValue = Bytes.toString(cell.get(timestamp));
					System.out.printf("\t\t\t%s, %d\n", cellValue, timestamp);
				}
			}
		}
	}

	

}
