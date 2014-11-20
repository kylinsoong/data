package org.apache.poi.xslf.usermodel.teiid;

import java.io.FileInputStream;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;


public class TEIID3219HSSF {
	
	private void test() throws Exception {
		FileInputStream xlsFileStream = new FileInputStream("file/names.xls");
		HSSFWorkbook workbook = new HSSFWorkbook(xlsFileStream);
		HSSFSheet sheet = workbook.getSheet("Sheet1");
		FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
		Iterator<Row> rowIterator = sheet.iterator();
		
		while(rowIterator.hasNext()) {
			Row row = rowIterator.next();
			for(int i = 0 ; i < row.getLastCellNum(); i ++) {
				Cell cell = row.getCell(i, Row.RETURN_BLANK_AS_NULL);
				System.out.print(cell + ", ");
			}
						
			System.out.println();
		}
		
		
//		workbook.close();
	}

	public static void main(String[] args) throws Exception {
		new TEIID3219HSSF().test();
	}


}
