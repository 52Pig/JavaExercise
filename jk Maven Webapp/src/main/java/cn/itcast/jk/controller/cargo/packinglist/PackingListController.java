package cn.itcast.jk.controller.cargo.packinglist;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.itcast.jk.controller.BaseController;
import cn.itcast.jk.domain.PackingList;
import cn.itcast.jk.service.PackingListService;

@Controller
public class PackingListController extends BaseController {
	@Resource
	PackingListService packingListService;
	
	@RequestMapping("/cargo/packinglist/list.action")
	public String list(Model model){
		List<PackingList> dataList = packingListService.find(null);
		model.addAttribute("dataList", dataList);
		
		return "/cargo/packinglist/jPackingListList.jsp";
	}
	
	//关联新增
	@RequestMapping("/cargo/packinglist/tocreate.action")
	public String tocreate(@RequestParam("id")String[] exportIds, Model model){
		
		model.addAttribute("divData", packingListService.getDivDataCreate(exportIds));		//传递页面的变量
		
		return "/cargo/packinglist/jPackingListCreate.jsp";
	}
	
	//新增保存
	@RequestMapping("/cargo/packinglist/insert.action")
	public String insert(PackingList packingList){
		packingList = this.doPackingList(packingList);
		packingListService.insert(packingList);
		
		return "redirect:/cargo/packinglist/list.action";
	}
	
	@RequestMapping("/cargo/packinglist/toupdate.action")
	public String toupdate(String id, Model model){
		PackingList obj = packingListService.get(id);
		model.addAttribute("obj", obj);
		
		//准备div数据
		model.addAttribute("divData", packingListService.getDivDataUpdate(obj));
		
		return "/cargo/packinglist/jPackingListUpdate.jsp";
	}
	
	//修改保存
	@RequestMapping("/cargo/packinglist/update.action")
	public String update(PackingList packingList){
		packingList = this.doPackingList(packingList);
		packingListService.update(packingList);
		
		return "redirect:/cargo/packinglist/list.action";
	}
	
	
	
	//删除
	@RequestMapping("/cargo/packinglist/delete.action")
	public String delete(String[] id){
		packingListService.delete(id);
		
		return "redirect:/cargo/packinglist/list.action";
	}
	
	//查看
	@RequestMapping("/cargo/packinglist/toview.action")
	public String toview(String id, Model model, HttpServletRequest request){
		PackingList obj = packingListService.get(id);
		model.addAttribute("obj", obj);
		
		model.addAttribute("divData", packingListService.getDivDataView(obj, request.getSession().getServletContext().getContextPath()));
		
		return "/cargo/packinglist/jPackingListView.jsp";
	}
	
	//处理exportIds,exportNos
	private PackingList doPackingList(PackingList packingList){
		String[] tmpIds = packingList.getExportIds().split(",");	//exportId|exportNo
		StringBuffer ids = new StringBuffer();
		StringBuffer nos = new StringBuffer();
		
		for(int i=0;i<tmpIds.length;i++){
			String[] tmp = tmpIds[i].split("\\|");
			ids.append(tmp[0]).append("|");
			nos.append(tmp[1]).append("|");
		}
		ids.delete(ids.length()-1, ids.length());
		nos.delete(nos.length()-1, nos.length());
		
		packingList.setExportIds(ids.toString());
		packingList.setExportNos(nos.toString());
		
		return packingList;
	}
}
