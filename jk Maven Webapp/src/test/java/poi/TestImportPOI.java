package poi;

import java.io.File;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.Test;

public class TestImportPOI {
	@Test
	//测试xls文件的poi导入
	public void importPOI() throws InvalidFormatException, IOException{
		/*
		 * 开发步骤：
		 * 1、读取xls文件
		 * 2、拼接成sql
		 * 3、批量插入
		 */
		
		//实现自动识别读取的xls版本，创建其对应的对象; 2003 HSSFWorkbook对象；2007 XSSFWorkbook对象
		File file = new File("c:\\factory.xls");
		Workbook wb = new WorkbookFactory().create(file);		//自动识别excel版本
		Sheet sheet = wb.getSheetAt(0);
		
		Row nRow = sheet.getRow(1);
		Cell nCell = null;
		
		//是否标题行，标题行还可以多列
		int beginRowNo = 0;								//开始行
		int endRowNo = 	sheet.getLastRowNum();			//结束行
		int beginColNo = 0;								//开始列
		int endColNo = nRow.getLastCellNum();			//结束列

		
		for(int i=beginRowNo;i<endRowNo;i++){
			nRow = sheet.getRow(i++);					//行对象
			
			for(int j=beginColNo;j<endColNo;j++){
				nCell = nRow.getCell(j++);				//单元格
				System.out.println(nCell.toString());
			}
		}
		
		
		
	}
}
