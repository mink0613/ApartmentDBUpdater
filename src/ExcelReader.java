import java.io.FileInputStream;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import common.JCode;

public class ExcelReader {

	private final int col_code = 0;
	
	private final int col_name_1 = 1;
	
	private final int col_name_2 = 2;
	
	private final int col_name_3 = 3;
	
	public ArrayList<JCode> ReadJCodeFile() {
		
		ArrayList<JCode> jCodeList = new ArrayList<>();
		
		try {
			
			FileInputStream fis = new FileInputStream("C:\\Develop_Sourcecode\\ApartmentDBUpdater\\jscode20180122\\KIKcd_B.20180122.xls");
            HSSFWorkbook workbook = new HSSFWorkbook(fis);
            HSSFSheet sheet = workbook.getSheetAt(0); 
            int rows = sheet.getPhysicalNumberOfRows();
            int lastCode = -1;
            
            for (int rowIndex = 1; rowIndex < rows; rowIndex++) {
                HSSFRow row = sheet.getRow(rowIndex);
                if (row != null) {
                    
                	String strCode = String.valueOf((int)row.getCell(col_code).getNumericCellValue());
                    int code = Integer.parseInt(strCode.substring(0, 5));
                    String name1 = row.getCell(col_name_1).getStringCellValue();
                    
                    if (lastCode == code) {
                    	continue;
                    }
                    
                    if (!name1.equals("서울특별시")) {
                    
                    	continue;
                    }
                    
                    lastCode = code;
                    HSSFCell cell2 = row.getCell(col_name_2);
                    HSSFCell cell3 = row.getCell(col_name_3);
                    
                    String name2;
                    String name3;
                    
                    if (cell2 == null) {
                    	name2 = "";
                    } else {
                    	name2 = cell2.getStringCellValue();
                    }
                    
                    if (cell3 == null) {
                    	name3 = "";
                    } else {
                    	name3 = cell3.getStringCellValue();
                    }
                    
                    JCode jCode = new JCode(code, name1, name2, name3);
                    jCodeList.add(jCode);
                }
            }
            
            workbook.close();
		} catch (Exception e) {
			
			System.out.println(e.toString());
		}
		
		return jCodeList;
	}
}
