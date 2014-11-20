package org.apache.poi.xslf.usermodel.teiid;

import java.io.FileInputStream;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class TEIID3219 {
	
	private void test() throws Exception {
		FileInputStream xlsFileStream = new FileInputStream("file/3219.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook(xlsFileStream);
		XSSFSheet sheet = workbook.getSheet("Sheet1");
		FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
		Iterator<Row> rowIterator = sheet.iterator();
		
		while(rowIterator.hasNext()) {
			Row row = rowIterator.next();
			Cell cell = row.getCell(0, Row.RETURN_BLANK_AS_NULL);
//			System.out.println(evaluator.evaluateInCell(cell).getCellType());
			System.out.println(cell);
//			System.out.println(row.getRowNum() + ", " + row.getFirstCellNum() + ", " + row.getLastCellNum());
		}
		
		
		workbook.close();
	}

	public static void main(String[] args) throws Exception {
		new TEIID3219().test();
	}

}
