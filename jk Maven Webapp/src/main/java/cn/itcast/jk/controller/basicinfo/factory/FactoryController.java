package cn.itcast.jk.controller.basicinfo.factory;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.itcast.common.springdao.SqlDao;
import cn.itcast.jk.controller.BaseController;
import cn.itcast.jk.domain.Factory;
import cn.itcast.jk.service.FactoryService;
import cn.itcast.util.UtilFuns;

@Controller
public class FactoryController extends BaseController {
	@Resource
	FactoryService factoryService;
	@Resource
	SqlDao sqlDao;
	
	//查询
	@RequestMapping("/basicinfo/factory/list.action")
	public String list(Model model){
		List<Factory> dataList = factoryService.find(null);
		model.addAttribute("dataList", dataList);
		
		return "/basicinfo/factory/jFactoryList.jsp";
	}
	
	//转向新增页面
	@RequestMapping("/basicinfo/factory/tocreate.action")
	public String tocreate(){
		return "/basicinfo/factory/jFactoryCreate.jsp";
	}
	
	//新增保存
	@RequestMapping("/basicinfo/factory/insert.action")
	public String insert(Factory factory){
		factoryService.insert(factory);
		
		return "redirect:/basicinfo/factory/list.action";
	}
	
	//转向修改页面
	@RequestMapping("/basicinfo/factory/toupdate.action")
	public String toupdate(String id, Model model){
		Factory obj = factoryService.get(id);
		model.addAttribute("obj", obj);
		
		return "/basicinfo/factory/jFactoryUpdate.jsp";
	}
	
	//修改保存
	@RequestMapping("/basicinfo/factory/update.action")
	public String update(Factory factory){
		factoryService.update(factory);
		
		return "redirect:/basicinfo/factory/list.action";
	}
	
	//删除一条
	@RequestMapping("/basicinfo/factory/deleteById.action")
	public String deleteById(String id){
		factoryService.deleteById(id);
		
		return "redirect:/basicinfo/factory/list.action";
	}
	
	//批量删除
	@RequestMapping("/basicinfo/factory/delete.action")
	public String delete(@RequestParam("id")String[] ids){
		factoryService.delete(ids);
		
		return "redirect:/basicinfo/factory/list.action";
	}
	
	//查看
	@RequestMapping("/basicinfo/factory/toview.action")
	public String toview(String id, Model model){
		Factory obj = factoryService.get(id);
		model.addAttribute("obj", obj);
		
		return "/basicinfo/factory/jFactoryView.jsp";
	}
	
	//启用
	@RequestMapping("/basicinfo/factory/start.action")
	public String start(@RequestParam("id")String[] ids){
		factoryService.start(ids);
		
		return "redirect:/basicinfo/factory/list.action";
	}
	
	
	//停用
	@RequestMapping("/basicinfo/factory/stop.action")
	public String stop(@RequestParam("id")String[] ids){
		factoryService.stop(ids);
		
		return "redirect:/basicinfo/factory/list.action";
	}
	
	//导入
	@RequestMapping("/basicinfo/factory/importInfo.action")
	public String importInfo() throws InvalidFormatException, IOException{
		/*
		 * 开发步骤：
		 * 1、读取xls文件；创建模板，设置所有单元格为文本类型，然后黏贴数据到editplus，再黏贴到excel中，这样就不怕读取类型错误。
		 * 2、拼接成sql
		 * 3、批量插入
		 */
		
		//实现自动识别读取的xls版本，创建其对应的对象; 2003 HSSFWorkbook对象；2007 XSSFWorkbook对象
		String sql = "";
		File file = new File("c:\\7F28E200.xlsx");
		Workbook wb = new WorkbookFactory().create(file);		//自动识别excel版本
		Sheet sheet = wb.getSheetAt(0);
		
		Row nRow = sheet.getRow(1);
		Cell nCell = null;
		
		//是否标题行，标题行还可以多列
		int beginRowNo = 0;								//开始行
		int endRowNo = 	sheet.getLastRowNum();			//结束行
		int beginColNo = 0;								//开始列
		int endColNo = nRow.getLastCellNum();			//结束列

		StringBuffer sBuf = new StringBuffer();
		for(int i=beginRowNo;i<endRowNo;i++){
			nRow = sheet.getRow(i);						//行对象
//			
//			for(int j=beginColNo;j<endColNo;j++){
//				nCell = nRow.getCell(j++);				//单元格
//				System.out.println(nCell.toString());
//			}
			
			beginColNo = 0;								//开始列
//			
			
			sBuf.append("insert into factory_c (FACTORY_ID,FULL_NAME,FACTORY_NAME,CONTACTS,PHONE,MOBILE,FAX,INSPECTOR,CNOTE,ORDER_NO,STATE,CREATE_BY,CREATE_DEPT,CREATE_TIME) ");
			sBuf.append("values(");
			
			nCell = nRow.getCell(beginColNo++);	
			if(nCell==null){
				sBuf.append("''");
			}else{
				sBuf.append("'").append(UtilFuns.convertNull(nCell.getStringCellValue())).append("'");
			}
			sBuf.append(",");
			
			nCell = nRow.getCell(beginColNo++);		
			if(nCell==null){
				sBuf.append("''");
			}else{
				sBuf.append("'").append(UtilFuns.convertNull(nCell.getStringCellValue())).append("'");
			}
			sBuf.append(",");
			
			nCell = nRow.getCell(beginColNo++);		
			if(nCell==null){
				sBuf.append("''");
			}else{
				sBuf.append("'").append(UtilFuns.convertNull(nCell.getStringCellValue())).append("'");
			}
			sBuf.append(",");
			
			nCell = nRow.getCell(beginColNo++);		
			if(nCell==null){
				sBuf.append("''");
			}else{
				sBuf.append("'").append(UtilFuns.convertNull(nCell.getStringCellValue())).append("'");
			}
			sBuf.append(",");
			
			nCell = nRow.getCell(beginColNo++);		
			if(nCell==null){
				sBuf.append("''");
			}else{
				sBuf.append("'").append(UtilFuns.convertNull(nCell.getStringCellValue())).append("'");
			}
			sBuf.append(",");
			
			nCell = nRow.getCell(beginColNo++);		
			if(nCell==null){
				sBuf.append("''");
			}else{
				sBuf.append("'").append(UtilFuns.convertNull(nCell.getStringCellValue())).append("'");
			}
			sBuf.append(",");
			
			nCell = nRow.getCell(beginColNo++);		
			if(nCell==null){
				sBuf.append("''");
			}else{
				sBuf.append("'").append(UtilFuns.convertNull(nCell.getStringCellValue())).append("'");
			}
			sBuf.append(",");
			
			nCell = nRow.getCell(beginColNo++);		
			if(nCell==null){
				sBuf.append("''");
			}else{
				sBuf.append("'").append(UtilFuns.convertNull(nCell.getStringCellValue())).append("'");
			}
			sBuf.append(",");
			
			nCell = nRow.getCell(beginColNo++);		
			if(nCell==null){
				sBuf.append("''");
			}else{
				sBuf.append("'").append(UtilFuns.convertNull(nCell.getStringCellValue())).append("'");
			}
			sBuf.append(",");
			
			//orderNo
			nCell = nRow.getCell(beginColNo++);		
			if(nCell==null){
				sBuf.append("null");
			}else{
				sBuf.append(UtilFuns.convertNull(nCell.getStringCellValue()));
			}
			sBuf.append(",");
			
			//state
			nCell = nRow.getCell(beginColNo++);		
			if(nCell==null){
				sBuf.append("null");
			}else{
				sBuf.append(UtilFuns.convertNull(nCell.getStringCellValue()));
			}
			sBuf.append(",");
			
			nCell = nRow.getCell(beginColNo++);		
			if(nCell==null){
				sBuf.append("''");
			}else{
				sBuf.append("'").append(UtilFuns.convertNull(nCell.getStringCellValue())).append("'");
			}
			sBuf.append(",");
			
			nCell = nRow.getCell(beginColNo++);		
			if(nCell==null){
				sBuf.append("''");
			}else{
				sBuf.append("'").append(UtilFuns.convertNull(nCell.getStringCellValue())).append("'");
			}
			sBuf.append(",");
			
			nCell = nRow.getCell(beginColNo++);		
			if(nCell==null){
				sBuf.append("NULL");
			}else{
				sBuf.append("'").append(UtilFuns.convertNull(nCell.getStringCellValue())).append("'");
			}
			
			sBuf.append(");");
			
		}
		for(String s : sBuf.toString().split(";")){
			System.out.println(s);
		}
		
		sqlDao.batchSQL(sBuf.toString().split(";"));		//执行insert sql
		
		return "redirect:/basicinfo/factory/list.action";
	}
}
