package poi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;

public class testPOIDemo {
	@Test
	public void HSSF() throws IOException{
		//1.创建一个工作簿excel文件
		Workbook wb = new HSSFWorkbook();		//HSSF操作excel 2003以下版本
		//2.创建一个工作表sheet
		Sheet sheet = wb.createSheet();
		//3.创建一个行对象Row
		Row nRow = sheet.createRow(4);			//第五行,坐标从0开始
		//4.创建一个单元格对象，指定列
		Cell nCell = nRow.createCell(3);		//第四列
		//5.给单元格设置内容
		nCell.setCellValue("传智播客万年长！");
		//6.保存
		OutputStream os = new FileOutputStream(new File("c:\\testPOI.xls"));
		wb.write(os);
		//7.关闭
		os.close();
	}
	
	
	//带格式，简化对象创建，复用样式
	@Test
	public void HSSFStyle() throws IOException{
		Workbook wb = new HSSFWorkbook();				//HSSF操作excel 2003以下版本
		Sheet sheet = wb.createSheet();
		Row nRow = sheet.createRow(4);					//第五行,坐标从0开始
		Cell nCell = nRow.createCell(3);				//第四列
		
		CellStyle nStyle = wb.createCellStyle();
		Font nFont = wb.createFont();
		
		
		nCell.setCellValue("传智播客万年长！");
		nCell.setCellStyle(this.titleStyle(wb, nStyle, nFont));
		
		//再创建一个单元格
		nRow = sheet.createRow(5);
		nCell = nRow.createCell(5);
		
		nCell.setCellValue("java.itcast.cn");
		
		//样式初始化
		nStyle = wb.createCellStyle();
		nFont = wb.createFont();
		nCell.setCellStyle(this.textStyle(wb, nStyle, nFont));
		
		OutputStream os = new FileOutputStream(new File("c:\\testPOI.xls"));
		wb.write(os);
		os.close();
	}
	
	//带格式
	@Test
	public void HSSFStyleFont() throws IOException{
		Workbook wb = new HSSFWorkbook();				//HSSF操作excel 2003以下版本
		Sheet sheet = wb.createSheet();
		Row nRow = sheet.createRow(4);					//第五行,坐标从0开始
		Cell nCell = nRow.createCell(3);				//第四列
		
		nCell.setCellValue("传智播客万年长！");
		
		//创建一个单元格样式
		CellStyle nStyle = wb.createCellStyle();
		//创建一个字体对象
		Font nFont = wb.createFont();
		nFont.setFontName("华文行楷");				//设置字体
		nFont.setFontHeightInPoints((short)26);		//设置字体大小
		
		nStyle.setFont(nFont);
		nCell.setCellStyle(nStyle);
		
		//再创建一个单元格
		nRow = sheet.createRow(5);
		nCell = nRow.createCell(5);
		
		nCell.setCellValue("java.itcast.cn");
		
		nStyle = wb.createCellStyle();
		nFont = wb.createFont();
		nFont.setFontName("Times New Roman");
		nFont.setFontHeightInPoints((short)12);
		
		nStyle.setFont(nFont);
		nCell.setCellStyle(nStyle);
		
		OutputStream os = new FileOutputStream(new File("c:\\testPOI.xls"));
		wb.write(os);
		os.close();
	}
	
	
	//带格式
	@Test
	public void HSSFStyleMore() throws IOException{
		Workbook wb = new HSSFWorkbook();				//HSSF操作excel 2003以下版本
		Sheet sheet = wb.createSheet();
		Row nRow = sheet.createRow(4);					//第五行,坐标从0开始
		Cell nCell = nRow.createCell(3);				//第四列
		
		nCell.setCellValue("传智播客万年长！");
		
		//创建一个单元格样式
		CellStyle titleStyle = wb.createCellStyle();
		//创建一个字体对象
		Font titleFont = wb.createFont();
		titleFont.setFontName("华文行楷");				//设置字体
		titleFont.setFontHeightInPoints((short)26);		//设置字体大小
		
		titleStyle.setFont(titleFont);
		
		//再创建一个单元格
		Row xRow = sheet.createRow(5);
		Cell xCell = xRow.createCell(5);
		
		xCell.setCellValue("java.itcast.cn");
		
		CellStyle textStyle = wb.createCellStyle();
		Font textFont = wb.createFont();
		textFont.setFontName("Times New Roman");
		textFont.setFontHeightInPoints((short)12);
		
		textStyle.setFont(textFont);
		
		xCell.setCellStyle(textStyle);
		
		nCell.setCellStyle(titleStyle);
		OutputStream os = new FileOutputStream(new File("c:\\testPOI.xls"));
		wb.write(os);
		os.close();
	}
	
	//标题样式
	public CellStyle titleStyle(Workbook wb, CellStyle nStyle ,Font nFont ){
		nFont.setFontName("华文行楷");				//设置字体
		nFont.setFontHeightInPoints((short)28);		//设置字体大小
		nStyle.setFont(nFont);						//绑定字体对象
		
		return nStyle;
	}
	
	//文字样式
	public CellStyle textStyle(Workbook wb, CellStyle nStyle ,Font nFont ){
		nFont.setFontName("Times New Roman");				//设置字体
		nFont.setFontHeightInPoints((short)12);				//设置字体大小
		nStyle.setFont(nFont);								//绑定字体对象
		
		return nStyle;
	}
}
