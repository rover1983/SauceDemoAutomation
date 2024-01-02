package utils;

import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {
	
	static XSSFWorkbook workbook;
	static XSSFSheet sheet;
	
	public ExcelUtils(String excelPath, String sheetName) {
		
		try {
			workbook = new XSSFWorkbook(excelPath);
			sheet = workbook.getSheet(sheetName);
			
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	
	public static void getCellData() {
		
		String value = sheet.getRow(1).getCell(0).getStringCellValue();			
		System.out.println("Cell value is: "+value);
		
	}
	
	public static void getRowCount() {
		
		String excelPath = "./data/ExcelData.xlsx";
		try {
			XSSFWorkbook workbook = new XSSFWorkbook(excelPath);
			XSSFSheet sheet = workbook.getSheet("Sheet1");
			int rowsCount = sheet.getPhysicalNumberOfRows();
			System.out.println("Number of rows is: "+rowsCount);
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
