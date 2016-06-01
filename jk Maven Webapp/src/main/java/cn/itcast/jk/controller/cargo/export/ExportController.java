package cn.itcast.jk.controller.cargo.export;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.itcast.jk.controller.BaseController;
import cn.itcast.jk.domain.Export;
import cn.itcast.jk.domain.ExportProduct;
import cn.itcast.jk.service.ExportProductService;
import cn.itcast.jk.service.ExportService;
import cn.itcast.util.UtilFuns;

@Controller
public class ExportController extends BaseController {
	@Resource
	ExportService exportService;
	@Resource
	ExportProductService exportProductService;

	@RequestMapping("/cargo/export/list.action")
	public String list(Model model){
		List<Export> dataList= exportService.find(null);
		model.addAttribute("dataList", dataList);
		
		return "/cargo/export/jExportList.jsp";
	}
	
	//新增保存，再转向列表页面
	@RequestMapping("/cargo/export/tocreate.action")
	public String tocreate(@RequestParam("id")String[] ids){			//合同的id
		exportService.insert(ids);
		
		return "redirect:/cargo/export/list.action";
	}
	
	//转向修改页面
	@RequestMapping("/cargo/export/toupdate.action")
	public String toupdate(String id, Model model){
		//准备数据
		//1.报运主信息
		Export obj = exportService.get(id);
		model.addAttribute("obj", obj);
		
		
		
		//2.货物信息	addTRRecord('mRecordTable', id, productNo, cnumber, grossWeight, netWeight, sizeLength, sizeWidth, sizeHeight, exPrice, tax)
		StringBuffer sBuf = new StringBuffer();
		Map paraMap = new HashMap();
		paraMap.put("exportId", obj.getId());
		List<ExportProduct> epList = exportProductService.find(paraMap);	//某个报运下的货物信息
		
		for(ExportProduct ep : epList){
			sBuf.append("addTRRecord('mRecordTable', '").append(ep.getId()).append("', '").append(ep.getProductNo()).append("', '").append(ep.getCnumber()).append("', '").append(UtilFuns.convertNull(ep.getGrossWeight())).append("', '").append(UtilFuns.convertNull(ep.getNetWeight())).append("', '").append(UtilFuns.convertNull(ep.getSizeLength())).append("', '").append(UtilFuns.convertNull(ep.getSizeWidth())).append("', '").append(UtilFuns.convertNull(ep.getSizeHeight())).append("', '").append(UtilFuns.convertNull(ep.getExPrice())).append("', '").append(UtilFuns.convertNull(ep.getTax())).append("');");
		}
		
		model.addAttribute("mRecordData", sBuf.toString());
		
		return "/cargo/export/jExportUpdate.jsp";
	}
	
	//修改保存
	@RequestMapping("/cargo/export/update.action")
	public String update(Export export,
				Integer[] mr_orderNo,
				String[] mr_id,
				Integer[] mr_changed,
				Integer[] mr_cnumber,
				Double[] mr_grossWeight,
				Double[] mr_netWeight,
				Double[] mr_sizeLength,
				Double[] mr_sizeWidth,
				Double[] mr_sizeHeight,
				Double[] mr_exPrice,
				Double[] mr_tax
			){
		exportService.update(export,mr_orderNo,mr_id,mr_changed,mr_cnumber,
				mr_grossWeight,mr_netWeight,mr_sizeLength,mr_sizeWidth,mr_sizeHeight,mr_exPrice,mr_tax);
		
		
		
		return "redirect:/cargo/export/list.action";
	}
	
	//级联删除
	@RequestMapping("/cargo/export/delete.action")
	public String delete(String[] id){
		exportService.delete(id);
		
		return "redirect:/cargo/export/list.action";
	}
	
	//转向查看页面
	@RequestMapping("/cargo/export/toview.action")
	public String toview(String id, Model model, HttpServletRequest request){
		Export obj = exportService.get(id);
		model.addAttribute("obj", obj);
		
		//加上合同的查看链接
		StringBuffer sBuf = new StringBuffer();
		String[] ids = obj.getContractIds().split(",");			//获得合同id集合
		String[] nos = obj.getCustomerContract().split(" ");	//获得合同号集合
		for(int i=0;i<ids.length;i++){
			if(UtilFuns.isNotEmpty(ids[i])){
				sBuf.append("<a href='").append(request.getContextPath())
				.append("/cargo/contract/toview.action?id=").append(ids[i]).append("'>").append(nos[i]).append("</a>");
				sBuf.append("&nbsp;&nbsp;");
			}
		}
		model.addAttribute("contractUrls", sBuf.toString());
		
		return "/cargo/export/jExportView.jsp";
	}
}
