package org.apache.poi.xslf.usermodel.teiid;

import java.io.FileInputStream;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class TEIID3219XSSF {
	
	/*
	 * Available xlsx files:
	 *      3219.xlsx
	 *      names.xlsx
	 *      test.xlsx
	 *      test-0.xlsx
	 *      test-1.xlsx
	 *      test-2.xlsx
	 */		
	private void test() throws Exception {
		FileInputStream xlsFileStream = new FileInputStream("file/test.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook(xlsFileStream);
		XSSFSheet sheet = workbook.getSheet("Sheet1");
		FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
		Iterator<Row> rowIterator = sheet.iterator();
		
		System.out.println("LastRowNum: " + sheet.getLastRowNum() + ", ActiveCell: " + sheet.getActiveCell());
		
		while(rowIterator.hasNext()) {
			Row row = rowIterator.next();
			for(int i = 0 ; i < row.getLastCellNum(); i ++) {
				Cell cell = row.getCell(i, Row.RETURN_BLANK_AS_NULL);
//				Object type = cell == null ? " " : cell.getCellType();
				if(i == row.getLastCellNum() -1 ) {
					System.out.println(cell);
				} else {
					System.out.print(cell + ", ");
				}
				
			}
						
		}
		
		
//		workbook.close();
	}

	public static void main(String[] args) throws Exception {
		new TEIID3219XSSF().test();
	}

}
