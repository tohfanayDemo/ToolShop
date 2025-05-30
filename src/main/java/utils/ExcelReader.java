package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.File;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook; 

public class ExcelReader {

	
	private static Workbook workbook;
    private static FileInputStream fis;

    public static void openExcel(String filePath) throws IOException {
        fis = new FileInputStream(new File(filePath));  
        workbook = new XSSFWorkbook(fis);                
    }
    
    public static List<Map<String, String>> getExcelData(String sheetName) {
        List<Map<String, String>> dataList = new ArrayList<>();
       
            Sheet sheet = workbook.getSheet(sheetName);
            Row headerRow = sheet.getRow(0);
            int columnCount = headerRow.getLastCellNum();

            for (int i = 1; i <= sheet.getLastRowNum(); i++) { // Start from row 1 (skip headers)
                Row row = sheet.getRow(i);
                Map<String, String> dataMap = new HashMap<>();

                for (int j = 0; j < columnCount; j++) {
                    Cell headerCell = headerRow.getCell(j);
                    Cell valueCell = row.getCell(j);
                    String header = headerCell.getStringCellValue();
                    String value = (valueCell == null) ? "" : valueCell.toString();
                    dataMap.put(header, value);
                    
                }
                dataList.add(dataMap);
               
                
            }
               
        return dataList;
    }
    
    
    public static void closeExcel() throws IOException {
        if (workbook != null) {
            workbook.close();  
        }
        if (fis != null) {
            fis.close();  
        }
    }

}