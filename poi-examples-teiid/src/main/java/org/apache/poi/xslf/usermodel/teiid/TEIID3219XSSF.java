package org.apache.poi.xslf.usermodel.teiid;

import java.io.FileInputStream;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class TEIID3219XSSF {
	
	private void test() throws Exception {
		// 3219.xlsx
		FileInputStream xlsFileStream = new FileInputStream("file/3219.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook(xlsFileStream);
		XSSFSheet sheet = workbook.getSheet("Sheet1");
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
		new TEIID3219XSSF().test();
	}

}
