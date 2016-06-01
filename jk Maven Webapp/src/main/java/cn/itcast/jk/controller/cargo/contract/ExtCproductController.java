package cn.itcast.jk.controller.cargo.contract;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.itcast.jk.domain.ExtCproduct;
import cn.itcast.jk.service.ExtCproductService;

@Controller
public class ExtCproductController {
	@Resource
	ExtCproductService extCproductService;
	
	//转向新增页面
	@RequestMapping("/cargo/extcproduct/tocreate.action")				//从表
	public String tocreate(String contractProductId, Model model){
		//准备数据
		model.addAttribute("contractProductId", contractProductId);		//传递主表ID
		
		//生产厂家
		model.addAttribute("factoryList", extCproductService.getFactoryList());
		
		//附件分类
		model.addAttribute("ctypeList", extCproductService.getCtypeList());
		
		//列表信息，某个货物下的附件信息
		Map paraMap = new HashMap();
		paraMap.put("contractProductId", contractProductId);
		List<ExtCproduct> dataList = extCproductService.find(paraMap);
		model.addAttribute("dataList", dataList);
		
		return "/cargo/contract/jExtCproductCreate.jsp";
	}
	
	//新增保存
	@RequestMapping("/cargo/extcproduct/insert.action")
	public String insert(ExtCproduct extCproduct, Model model){
		extCproductService.insert(extCproduct);
		model.addAttribute("contractProductId", extCproduct.getContractProductId());
		
		return "redirect:/cargo/extcproduct/tocreate.action";		//批量新增
	}
	
	//转向修改页面
	@RequestMapping("/cargo/extcproduct/toupdate.action")
	public String toupdate(String contractProductId, String id, Model model){
		//准备数据
		
		//生产厂家
		model.addAttribute("factoryList", extCproductService.getFactoryList());
		
		//附件分类
		model.addAttribute("ctypeList", extCproductService.getCtypeList());
		
		ExtCproduct obj = extCproductService.get(id);
		model.addAttribute("obj", obj);
		
		return "/cargo/contract/jExtCproductUpdate.jsp";
	}
	
	//修改保存
	@RequestMapping("/cargo/extcproduct/update.action")
	public String update(ExtCproduct extCproduct, Model model){
		extCproductService.update(extCproduct);
		model.addAttribute("contractProductId", extCproduct.getContractProductId());
		
		return "redirect:/cargo/extcproduct/tocreate.action";
	}
	
	//删除
	@RequestMapping("/cargo/extcproduct/deleteById.action")
	public String deleteById(String id, String contractProductId, Model model){
		extCproductService.deleteById(id);
		model.addAttribute("contractProductId", contractProductId);
		
		return "redirect:/cargo/extcproduct/tocreate.action";
	}
}
