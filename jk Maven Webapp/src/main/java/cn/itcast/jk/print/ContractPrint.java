package cn.itcast.jk.print;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import cn.itcast.jk.vo.ContractProductVO;
import cn.itcast.jk.vo.ContractVO;
import cn.itcast.util.DownloadUtil;
import cn.itcast.util.UtilFuns;
import cn.itcast.util.file.PoiUtil;

public class ContractPrint {
	
	public void print(ContractVO contract, HttpServletRequest request, HttpServletResponse response) throws ParseException, FileNotFoundException, IOException{
		//准备数据，读取模板，打印数据
		PoiUtil poiUtil = new PoiUtil();
		UtilFuns utilFuns = new UtilFuns();
		String path = request.getSession().getServletContext().getRealPath("/");
		List<ContractProductVO> cpList = contract.getContractProducts();		//获取货物集合
		
		//1.数据和业务分离
		Map<String,String> pageMap = null;			//存放一页数据
		List<Map> pageList = new ArrayList<Map>();					//存放页对象
		

		for(int i=0;i<cpList.size();i++){
			pageMap = new HashMap<String,String>();
			
			pageMap.put("offeror", "收 购 方："+contract.getOfferor());
			pageMap.put("contractNo", "合 同 号："+contract.getContractNo());
			pageMap.put("signingDate", "签单日期："+utilFuns.formatDateTimeCN(utilFuns.dateTimeFormat(contract.getSigningDate())));
			
			ContractProductVO cp = cpList.get(i);
			pageMap.put("factoryName", "生产工厂："+cp.getFactory().getFactoryName());
			pageMap.put("contacts", "联 系 人："+utilFuns.convertNull(cp.getFactory().getContacts()));
			pageMap.put("phone", "电    话："+cp.getFactory().getPhone());
			
			pageList.add(pageMap);
		}
		
		//2.读取模板
		Workbook wb = new HSSFWorkbook(new FileInputStream(new File("c:\\tCONTRACT.xls")));
		Row nRow = null;
		Cell nCell = null;
		
		//读取字体样式
		Sheet sheetStyle = wb.getSheetAt(1);
		nRow = sheetStyle.getRow(0);
		
		CellStyle shaanxiStyle = nRow.getCell(1).getCellStyle();
		
		nRow = sheetStyle.getRow(1);
		CellStyle jkStyle = nRow.getCell(1).getCellStyle();
		
		nRow = sheetStyle.getRow(2);
		CellStyle addressStyle = nRow.getCell(1).getCellStyle();
		
		nRow = sheetStyle.getRow(3);
		CellStyle companyStyle = nRow.getCell(1).getCellStyle();
		
		nRow = sheetStyle.getRow(4);
		CellStyle titleStyle = nRow.getCell(1).getCellStyle();
		
		nRow = sheetStyle.getRow(5);
		CellStyle subTitleStyle = nRow.getCell(1).getCellStyle();
		
		
		Sheet sheet = wb.getSheetAt(0);

		int rowNo = 0;
		
		//循环每页
		for(int j=0;j<pageList.size();j++){
			if(j>0){
				sheet.setRowBreak(rowNo++);		//在第startRow行设置分页符
			}

			pageMap = pageList.get(j);			//获取每页数据
		
			HSSFPatriarch patriarch = (HSSFPatriarch) sheet.createDrawingPatriarch();		//add picture
			poiUtil.setPicture(wb, patriarch, path+"/make/xlsprint/logo.jpg",rowNo,2,rowNo+3,2);
			
			nRow = sheet.createRow(rowNo++);
			nRow.setHeightInPoints(21f);
			nCell = nRow.createCell(3);
			nCell.setCellValue(" SHAANXI");
			nCell.setCellStyle(shaanxiStyle);
			
			nRow = sheet.createRow(rowNo++);
			nRow.setHeightInPoints(41f);
			nCell = nRow.createCell(3);
			nCell.setCellValue("     JK INTERNATIONAL ");
			nCell.setCellStyle(jkStyle);
			
			nRow = sheet.createRow(rowNo++);
			nRow.setHeightInPoints(20f);
			nCell = nRow.createCell(0);
			nCell.setCellValue("                 西经济技术开发区西城一路27号无迪大厦19楼");
			nCell.setCellStyle(addressStyle);		
			
			nCell = nRow.createCell(6);
			nCell.setCellValue(" CO., LTD.");
			nCell.setCellStyle(companyStyle);		
			
			nRow = sheet.createRow(rowNo++);
			nRow.setHeightInPoints(15f);
			
			nRow = sheet.createRow(rowNo++);
			nRow.setHeightInPoints(7f);
			
			poiUtil.setLine(wb, patriarch, rowNo, 2, rowNo, 8);	//draw line
			
			nRow = sheet.createRow(rowNo++);
			nRow.setHeightInPoints(30f);
			
			nCell = nRow.createCell(4);
			nCell.setCellValue("    购   销   合   同");
			nCell.setCellStyle(titleStyle);	
			
			nRow = sheet.createRow(rowNo++);
			nRow.setHeightInPoints(20f);
			
			nCell = nRow.createCell(1);
			nCell.setCellValue(pageMap.get("offeror"));
			nCell.setCellStyle(subTitleStyle);	
			
			nCell = nRow.createCell(5);
			nCell.setCellValue(pageMap.get("factoryName"));
			nCell.setCellStyle(subTitleStyle);	
			
			nRow = sheet.createRow(rowNo++);
			nRow.setHeightInPoints(20f);
			
			nCell = nRow.createCell(1);
			nCell.setCellValue(pageMap.get("contractNo"));
			nCell.setCellStyle(subTitleStyle);	
			
			nCell = nRow.createCell(5);
			nCell.setCellValue(pageMap.get("contacts"));
			nCell.setCellStyle(subTitleStyle);	
			
			nRow = sheet.createRow(rowNo++);
			nRow.setHeightInPoints(20f);
			
			nCell = nRow.createCell(1);
			nCell.setCellValue(pageMap.get("signingDate"));
			nCell.setCellStyle(subTitleStyle);	
			
			nCell = nRow.createCell(5);
			nCell.setCellValue(pageMap.get("phone"));
			nCell.setCellStyle(subTitleStyle);	
		
		}
		
		//下载
		DownloadUtil du = new DownloadUtil();
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		wb.write(os);
		du.download(os, response, "购销合同.xls");
	}
}
