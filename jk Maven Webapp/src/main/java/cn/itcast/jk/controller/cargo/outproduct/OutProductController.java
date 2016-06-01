package cn.itcast.jk.controller.cargo.outproduct;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.itcast.jk.controller.BaseController;
import cn.itcast.jk.service.OutProductService;
import cn.itcast.jk.vo.OutProductVO;
import cn.itcast.util.DownloadUtil;

@Controller
public class OutProductController extends BaseController {
	@Resource
	OutProductService outProductService;
	
	//转向输入年月的页面
	@RequestMapping("/cargo/outproduct/toedit.action")
	public String toedit(){
		return "/cargo/outproduct/jOutProduct.jsp";
	}
	
	//模板打印
	@RequestMapping("/cargo/outproduct/printTemplate.action")
	public void printTemplate(String inputDate, HttpServletRequest request, HttpServletResponse response) throws IOException{
		List<OutProductVO> dataList = outProductService.find(inputDate);
		
		//打开模板文件
		String path = request.getSession().getServletContext().getRealPath("/");		//jdk1.8 bug 在linux下不带后面你写的路径
		String tempFile = path + "/make/xlsprint/tOUTPRODUCT.xlsx";
		//tempFile = "c:\\tOUTPRODUCT.xls";
		Workbook wb = new XSSFWorkbook(new FileInputStream(new File(tempFile)));
		
		//写入业务内容
		Sheet sheet = wb.getSheetAt(0);		//获得工作表sheet
		Row nRow = null;
		Cell nCell = null;
		int rowNo = 2;
		int colNo = 1;
		
		//获取样式
		nRow = sheet.getRow(2);									//获得行对象
		nCell = nRow.getCell(1);								//获取单元格对象
		CellStyle customNameStyle = nCell.getCellStyle();		//获取到样式
		
		nCell = nRow.getCell(2);
		CellStyle contractNoStyle = nCell.getCellStyle();
		
		nCell = nRow.getCell(3);
		CellStyle productNoStyle = nCell.getCellStyle();
		
		nCell = nRow.getCell(4);
		CellStyle cnumberStyle = nCell.getCellStyle();
		
		nCell = nRow.getCell(5);
		CellStyle factoryStyle = nCell.getCellStyle();
		
		nCell = nRow.getCell(6);
		CellStyle extStyle = nCell.getCellStyle();
		
		nCell = nRow.getCell(7);
		CellStyle dateStyle = nCell.getCellStyle();
		
		nCell = nRow.getCell(9);
		CellStyle tradeStyle = nCell.getCellStyle();
		
		//大标题
		nRow = sheet.getRow(0);
		nCell = nRow.getCell(1);
		nCell.setCellValue(inputDate.replaceFirst("-0", "-").replaceFirst("-", "年") + "月份出货表");			//yyyy-MM

		//处理数据
		//for(int x=0;x<800;x++){
		for(int j=0;j<dataList.size();j++){
			System.out.println(rowNo);
			
			colNo = 1;								//列号初始化
			OutProductVO op = dataList.get(j);		//获取出货表对象
			
			nRow = sheet.createRow(rowNo++);
			nRow.setHeightInPoints(24);
			
			nCell = nRow.createCell(colNo++);
			nCell.setCellValue(op.getCustomName());
			nCell.setCellStyle(customNameStyle);	//设置样式
			
			nCell = nRow.createCell(colNo++);
			nCell.setCellValue(op.getContractNo());
			nCell.setCellStyle(contractNoStyle);
			
			nCell = nRow.createCell(colNo++);
			nCell.setCellValue(op.getProductNo());
			nCell.setCellStyle(productNoStyle);
			
			nCell = nRow.createCell(colNo++);
			nCell.setCellValue(op.getCnumber());
			nCell.setCellStyle(cnumberStyle);
			
			nCell = nRow.createCell(colNo++);
			nCell.setCellValue(op.getFactoryName());
			nCell.setCellStyle(factoryStyle);
			
			nCell = nRow.createCell(colNo++);
			nCell.setCellValue(op.getExts());
			nCell.setCellStyle(extStyle);
			
			nCell = nRow.createCell(colNo++);
			nCell.setCellValue(op.getDeliveryPeriod());
			nCell.setCellStyle(dateStyle);
			
			nCell = nRow.createCell(colNo++);
			nCell.setCellValue(op.getShipTime());
			nCell.setCellStyle(dateStyle);
			
			nCell = nRow.createCell(colNo++);
			nCell.setCellValue(op.getTradeTerms());
			nCell.setCellStyle(tradeStyle);
		}
		//}
		
		//下载
		DownloadUtil du = new DownloadUtil();
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		wb.write(os);
		du.download(os, response, "出货表.xlsx");
	}
	
	@RequestMapping("/cargo/outproduct/print.action")
	public void print(String inputDate, HttpServletResponse response) throws IOException{		//inputDate 格式：yyyy-MM
		
		List<OutProductVO> dataList = outProductService.find(inputDate);
		
		Workbook wb = new HSSFWorkbook();
		Sheet sheet = wb.createSheet();
		Row nRow = null;
		Cell nCell = null;
		
		int rowNo = 0;				//行号
		int colNo = 1;				//列号
		
		//声明样式对象和字体对象
		CellStyle nStyle = wb.createCellStyle();
		Font nFont = wb.createFont();
		
		sheet.setColumnWidth(0, 2*272);		//列宽
		sheet.setColumnWidth(1, 26*272);	//列宽 BUG，API底层设置不够精确 256；272近似
		sheet.setColumnWidth(2, 12*272);
		sheet.setColumnWidth(3, 29*272);
		sheet.setColumnWidth(4, 10*272);
		sheet.setColumnWidth(5, 12*272);
		sheet.setColumnWidth(6, 8*272);
		sheet.setColumnWidth(7, 10*272);
		sheet.setColumnWidth(8, 10*272);
		sheet.setColumnWidth(9, 8*272);
		
		
		//大标题，合并单元格
		sheet.addMergedRegion(new CellRangeAddress(0,0,1,9));		//开始行，结束行，开始列，结束列
		//合并单元格的内容是写在合并前第一个单元格中
		nRow = sheet.createRow(rowNo++);
		nRow.setHeightInPoints(36);					//行高
		
		nCell = nRow.createCell(1);
		nCell.setCellValue(inputDate.replaceFirst("-0", "-").replaceFirst("-", "年") + "月份出货表");			//yyyy-MM
		nCell.setCellStyle(this.bigTitle(wb, nStyle, nFont));
										
		String[] title = new String[]{"客户","订单号","货号","数量","工厂","附件","工厂交期","船期","贸易条款"};
		
		nRow = sheet.createRow(rowNo++);
		nRow.setHeightInPoints(26.25f);
		
		//初始化
		nStyle = wb.createCellStyle();
		nFont = wb.createFont();
		
		for(int i=0;i<title.length;i++){
			nCell = nRow.createCell(i+1);
			nCell.setCellValue(title[i]);
			nCell.setCellStyle(this.title(wb, nStyle, nFont));
		}

		//初始化
		nStyle = wb.createCellStyle();
		nFont = wb.createFont();
		
		//处理数据
		for(int j=0;j<dataList.size();j++){
			colNo = 1;								//列号初始化
			OutProductVO op = dataList.get(j);		//获取出货表对象
			
			nRow = sheet.createRow(rowNo++);
			nRow.setHeightInPoints(24);
			
			nCell = nRow.createCell(colNo++);
			nCell.setCellValue(op.getCustomName());
			nCell.setCellStyle(this.text(wb, nStyle, nFont));
			
			nCell = nRow.createCell(colNo++);
			nCell.setCellValue(op.getContractNo());
			nCell.setCellStyle(this.text(wb, nStyle, nFont));
			
			nCell = nRow.createCell(colNo++);
			nCell.setCellValue(op.getProductNo());
			nCell.setCellStyle(this.text(wb, nStyle, nFont));
			
			nCell = nRow.createCell(colNo++);
			nCell.setCellValue(op.getCnumber());
			nCell.setCellStyle(this.text(wb, nStyle, nFont));
			
			nCell = nRow.createCell(colNo++);
			nCell.setCellValue(op.getFactoryName());
			nCell.setCellStyle(this.text(wb, nStyle, nFont));
			
			nCell = nRow.createCell(colNo++);
			nCell.setCellValue(op.getExts());
			nCell.setCellStyle(this.text(wb, nStyle, nFont));
			
			nCell = nRow.createCell(colNo++);
			nCell.setCellValue(op.getDeliveryPeriod());
			nCell.setCellStyle(this.text(wb, nStyle, nFont));
			
			nCell = nRow.createCell(colNo++);
			nCell.setCellValue(op.getShipTime());
			nCell.setCellStyle(this.text(wb, nStyle, nFont));
			
			nCell = nRow.createCell(colNo++);
			nCell.setCellValue(op.getTradeTerms());
			nCell.setCellStyle(this.text(wb, nStyle, nFont));
		}

		
		//OutputStream os = new FileOutputStream(new File("c:\\outproduct.xls"));
		//wb.write(os);
		//os.flush();
		//os.close();
		
		//下载
		DownloadUtil du = new DownloadUtil();
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		wb.write(os);
		du.download(os, response, "出货表.xls");
	}
	
	//大标题样式
	public CellStyle bigTitle(Workbook wb, CellStyle nStyle, Font nFont){
		nFont.setFontName("宋体");
		nFont.setFontHeightInPoints((short)16);
		nFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);				//字体加粗
		
		nStyle.setAlignment(CellStyle.ALIGN_CENTER);				//横向居中
		nStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);		//纵向居中
		
		nStyle.setFont(nFont);
		
		return nStyle;
	}
	
	//标题样式
	public CellStyle title(Workbook wb, CellStyle nStyle, Font nFont){
		nFont.setFontName("黑体");
		nFont.setFontHeightInPoints((short)12);
		
		nStyle.setAlignment(CellStyle.ALIGN_CENTER);				//横向居中
		nStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);		//纵向居中
		
		//表格线
		nStyle.setBorderTop(CellStyle.BORDER_THIN);					//上细线
		nStyle.setBorderBottom(CellStyle.BORDER_THIN);				//下细线
		nStyle.setBorderLeft(CellStyle.BORDER_THIN);				//左细线
		nStyle.setBorderRight(CellStyle.BORDER_THIN);				//右细线
		
		nStyle.setFont(nFont);
		
		return nStyle;
	}
	
	//文字样式
	public CellStyle text(Workbook wb, CellStyle nStyle, Font nFont){
		nFont.setFontName("Times New Roman");
		nFont.setFontHeightInPoints((short)10);
		
		nStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);		//纵向居中
		
		//表格线
		nStyle.setBorderTop(CellStyle.BORDER_THIN);					//上细线
		nStyle.setBorderBottom(CellStyle.BORDER_THIN);				//下细线
		nStyle.setBorderLeft(CellStyle.BORDER_THIN);				//左细线
		nStyle.setBorderRight(CellStyle.BORDER_THIN);				//右细线
		
		nStyle.setFont(nFont);
		
		return nStyle;
	}
}
