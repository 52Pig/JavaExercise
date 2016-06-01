package cn.itcast.jk.controller.ws;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WsExportController {

	//转向出口报运查看页面
	@RequestMapping("/ws/export/toview.action")
	public String toview(){
		return "/ws/export/jExportView.jsp";
	}
}
