package cn.itcast.jk.controller.cargo.contract;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.itcast.jk.domain.ContractProduct;
import cn.itcast.jk.service.ContractProductService;

@Controller
public class ContractProductController {
	@Resource
	ContractProductService contractProductService;
	
	//转向新增页面
	@RequestMapping("/cargo/contractproduct/tocreate.action")		//从表
	public String tocreate(String contractId, Model model){
		//准备数据
		model.addAttribute("contractId", contractId);		//传递主表ID
		
		//生产厂家
		model.addAttribute("factoryList", contractProductService.getFactoryList());
		
		//列表信息，某个合同下的货物信息
		Map paraMap = new HashMap();
		paraMap.put("contractId", contractId);
		List<ContractProduct> dataList = contractProductService.find(paraMap);
		model.addAttribute("dataList", dataList);
		
		return "/cargo/contract/jContractProductCreate.jsp";
	}
	
	//新增保存
	@RequestMapping("/cargo/contractproduct/insert.action")
	public String insert(ContractProduct contractProduct){
		contractProductService.insert(contractProduct);
		
		return "redirect:/cargo/contractproduct/tocreate.action";		//批量新增
	}
	
	//转向修改页面
	@RequestMapping("/cargo/contractproduct/toupdate.action")
	public String toupdate(String contractId, String id, Model model){
		//准备数据
		
		//生产厂家
		model.addAttribute("factoryList", contractProductService.getFactoryList());
		
		ContractProduct obj = contractProductService.get(id);
		model.addAttribute("obj", obj);
		
		return "/cargo/contract/jContractProductUpdate.jsp";
	}
	
	//修改保存
	@RequestMapping("/cargo/contractproduct/update.action")
	public String update(ContractProduct contractProduct){
		contractProductService.update(contractProduct);
		
		return "redirect:/cargo/contractproduct/tocreate.action";
	}
	
	//删除
	@RequestMapping("/cargo/contractproduct/deleteById.action")
	public String deleteById(String id){
		contractProductService.deleteById(id);
		
		return "redirect:/cargo/contractproduct/tocreate.action";
	}
}
