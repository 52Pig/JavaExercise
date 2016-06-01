package cn.itcast.jk.controller.cargo.contract;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.itcast.jk.controller.BaseController;
import cn.itcast.jk.domain.Contract;
import cn.itcast.jk.print.ContractPrint;
import cn.itcast.jk.service.ContractHisService;
import cn.itcast.jk.service.ContractService;
import cn.itcast.jk.vo.ContractVO;

@Controller
public class ContractController extends BaseController {
	@Resource
	ContractService contractService;
	@Resource
	ContractHisService contractHisService;
			
	//查询
	@RequestMapping("/cargo/contract/list.action")
	public String list(Model model){
		List<Contract> dataList = contractService.find(null);
		model.addAttribute("dataList", dataList);
		
		return "/cargo/contract/jContractList.jsp";
	}
	
	//转向新增页面
	@RequestMapping("/cargo/contract/tocreate.action")
	public String tocreate(){
		//准备数据
		
		return "/cargo/contract/jContractCreate.jsp";
	}
	
	//新增保存
	@RequestMapping("/cargo/contract/insert.action")
	public String insert(Contract contract){
		contractService.insert(contract);
		
		return "redirect:/cargo/contract/list.action";
	}
	
	//转向修改页面
	@RequestMapping("/cargo/contract/toupdate.action")
	public String toupdate(String id, Model model){
		Contract obj = contractService.get(id);
		model.addAttribute("obj", obj);
		
		return "/cargo/contract/jContractUpdate.jsp";
	}
	
	//修改保存
	@RequestMapping("/cargo/contract/update.action")
	public String update(Contract contract){
		contractService.update(contract);
		
		return "redirect:/cargo/contract/list.action";
	}
	
	//批量删除
	@RequestMapping("/cargo/contract/delete.action")
	public String delete(String[] id){
		contractService.delete(id);
		
		return "redirect:/cargo/contract/list.action";
	}
	
	//上报
	@RequestMapping("/cargo/contract/submit.action")
	public String submit(String[] id){
		contractService.submit(id);
		
		return "redirect:/cargo/contract/list.action";
	}
	
	//取消
	@RequestMapping("/cargo/contract/cancle.action")
	public String cancle(String[] id){
		contractService.cancel(id);
		
		return "redirect:/cargo/contract/list.action";
	}
	
	//查看
	@RequestMapping("/cargo/contract/toview.action")
	public String toview(String id, Model model){
		ContractVO obj = contractService.view(id);
		model.addAttribute("obj", obj);
		
		return "/cargo/contract/jContractView.jsp";
	}
	
	//购销合同查询，为船务人员准备，只能看到已上报的数据
	@RequestMapping("/cargo/contract/shipList.action")
	public String shipList(Model model){
		Map pMap = new HashMap();
		pMap.put("state", 1);			//1已上报
		List<Contract> dataList = contractService.find(pMap);
		
		model.addAttribute("dataList", dataList);
		
		return "/cargo/contract/jContractShipList.jsp";
	}
	
	//打印
	@RequestMapping("/cargo/contract/print.action")
	public void print(String id, HttpServletRequest request, HttpServletResponse response) throws ParseException, FileNotFoundException, IOException{
		ContractPrint contractPrint = new ContractPrint();
		ContractVO contract = contractService.view(id);			//获得合同数据
		contractPrint.print(contract, request, response);
	}
	
	//归档
	@RequestMapping("/cargo/contract/pigeonhole.action")
	public String pigeonhole(@RequestParam("id")String[] ids){
		contractService.hole(ids, "", "_his");
		
		return "redirect:/cargo/contracthis/tocontracthis.action";
	}
	
	//转向历史购销合同列表
	@RequestMapping("/cargo/contracthis/tocontracthis.action")
	public String tocontracthis(Model model){
		List<Contract> dataList = contractHisService.find(null);
		model.addAttribute("dataList", dataList);
		
		return "/cargo/contracthis/jContractHisList.jsp";
	}
	
	//取消归档
	@RequestMapping("/cargo/contracthis/pigeouthole.action")
	public String pigeouthole(@RequestParam("id")String[] ids){
		contractService.hole(ids, "_his", "");
		
		return "redirect:/cargo/contract/list.action";
	}
}
