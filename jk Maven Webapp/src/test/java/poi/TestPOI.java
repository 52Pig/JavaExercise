package poi;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

public class TestPOI {
	@Test
	public void test() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, InterruptedException, IOException {
		String xlsFile = "c:/testPOI.xlsx";					//输出文件
		Workbook wb = new SXSSFWorkbook(100);					
		Sheet sheet = wb.createSheet("我的第一个工作簿");		//建立新的sheet对象

		Row nRow = null;
		Cell nCell   = null;
		
		Class.forName("com.mysql.jdbc.Driver").newInstance();  
		String url = "jdbc:mysql://localhost:3306/jkmore100?characterEncoding=UTF-8";
		String user = "root";
		String password = "root";
		
		Connection conn = DriverManager.getConnection(url, user,password);   
		Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);   

		String sql = "select * from hpa limit 65536";
		sql = "select * from hpa";

		
		long  startTime = System.currentTimeMillis();	//开始时间
		System.out.println("strat execute time: " + new java.util.Date());

		//实际调用时要分次读取，否则记录过多。目前可以支持到百万，但读取耗时，占用大量内存
		ResultSet rs = stmt.executeQuery(sql);  		
		long readDataTime = System.currentTimeMillis();	//准备数据时间
		System.out.println("read data execute  time: " + (readDataTime - startTime)/1000 + "m");
		
		//context
		int rowNo = 0;
		int colNo = 0;
		while(rs.next()) {
			colNo = 0;
			nRow = sheet.createRow(rowNo++);

			nCell = nRow.createCell(colNo++);
			nCell.setCellValue(rs.getString(colNo));
			
			nCell = nRow.createCell(colNo++);
			nCell.setCellValue(rs.getString(colNo));
			
			if(rowNo%100==0){			//每100行输出日志
				System.out.println("row no: " + rowNo);
			}
			
			Thread.sleep(1);			//休息一下，防止对CPU占用
		}
		
		long finishedTime = System.currentTimeMillis();	//处理完成时间
		System.out.println("finished execute  time: " + (finishedTime - startTime)/1000 + "m");
		
		
		FileOutputStream fOut = new FileOutputStream(xlsFile);
		wb.write(fOut);
		fOut.flush();
		fOut.close();
		
		long stopTime = System.currentTimeMillis();		//写文件时间
		System.out.println("write xls file time: " + (stopTime - startTime)/1000 + "m");
		
		rs.close();   
		stmt.close();   
		conn.close(); 
	}
}
