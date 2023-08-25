package com.qa.openCart.utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.qa.openCart.factory.DriverFactory;

public class ExcelUtil {

	private Workbook book;
	private Sheet sheet;
	

	//public String testFilePath = prop.getProperty("testdata");
	//public String testFilePath = "./src/test/resources/testData/QAopenCartTestData.xlsx";
	public String testFilePath = DriverFactory.testDataPath;

	
	public Object[][] getTestDataFromSheet(String sheetName) {
		Object data[][] = null;
		try {
			FileInputStream ip = new FileInputStream(testFilePath);
				book = WorkbookFactory.create(ip);
				sheet = book.getSheet(sheetName);
				int rowCount = sheet.getLastRowNum();
				int colCount = sheet.getRow(0).getLastCellNum();
				data = new Object[rowCount][colCount];
				
				for(int i=0; i<rowCount; i++) {
					for(int j=0; j<colCount; j++) {
						data[i][j] = sheet.getRow(i+1).getCell(j).toString();
					}
				}
				
			} catch (FileNotFoundException e) {
					e.printStackTrace();
			} catch (InvalidFormatException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} 
		return data;
		}
		
	}

