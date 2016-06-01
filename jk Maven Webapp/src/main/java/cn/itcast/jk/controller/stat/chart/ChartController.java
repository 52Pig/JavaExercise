package cn.itcast.jk.controller.stat.chart;

import java.io.FileNotFoundException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.itcast.common.springdao.SqlDao;
import cn.itcast.util.file.FileUtil;

@Controller
public class ChartController {

	@Resource
	SqlDao sqlDao;
	
	//厂家销售情况统计
	@RequestMapping("/stat/chart/factorySale.action")
	public String factorySale(HttpServletRequest request) throws FileNotFoundException{
		/*
		 * 开发步骤:
		 * 1、获取数据，封装到List<String>
		 * 2、拼接xml结构，生成对应目录下的data.xml文本文件
		 * 3、返回对应目录index.xml，调用jStat.jsp
		 */
		String path = request.getSession().getServletContext().getRealPath("/");
		String sql = "SELECT f.factory_name,cp.sumnum FROM (SELECT factory_id,factory_name FROM factory_c WHERE state=1) f LEFT JOIN (SELECT factory_id,SUM(cnumber) AS sumnum FROM contract_product_c GROUP BY factory_id ) cp ON f.factory_id=cp.factory_id WHERE cp.sumnum IS NOT NULL";
		this.writeXML(path, "factorysale", this.getPieXML(this.getData(sql)));
		
		return "/stat/chart/jStat.jsp?forward="+"factorysale";
	}
	
	//货物销售前10名
	@RequestMapping("/stat/chart/productSale.action")
	public String productSale(HttpServletRequest request) throws FileNotFoundException{
		String sql = "SELECT product_no,SUM(cnumber) AS sumnum FROM contract_product_c GROUP BY product_no ORDER BY sumnum DESC LIMIT 10";
		String path = request.getSession().getServletContext().getRealPath("/");
		
		this.writeXML(path, "productsale", this.getColumnAndLineXML(this.getData(sql)));
		
		return "/stat/chart/jStat.jsp?forward="+"productsale";
	}
	
	//系统访问压力图
	@RequestMapping("/stat/chart/onlineInfo.action")
	public String onlineInfo(HttpServletRequest request) throws FileNotFoundException{
		String sql = "SELECT t.a1,IFNULL(p.countnum,0) AS countnum FROM (SELECT a1 FROM online_t)  t LEFT JOIN (SELECT SUBSTRING(login_time,12,2) AS a1,COUNT(login_time) AS countnum FROM login_log_p GROUP BY SUBSTRING(login_time,12,2) ) p ON t.a1=p.a1";
		String path = request.getSession().getServletContext().getRealPath("/");
		
		this.writeXML(path, "onlineinfo", this.getColumnAndLineXML(this.getData(sql)));
		
		return "/stat/chart/jStat.jsp?forward="+"onlineinfo";
	}
	
	//获取数据源
	private List<String> getData(String sql){
		List<String> oList = sqlDao.executeSQL(sql);
		return oList;
	}
	
	//获取饼形图xml
	private String getPieXML(List<String> oList){
		StringBuffer sBuf = new StringBuffer();
		sBuf.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		sBuf.append("<pie>");
		for(int i=0;i<oList.size();){
			sBuf.append("  <slice title=\"").append(oList.get(i++)).append("\" pull_out=\"true\">").append(oList.get(i++)).append("</slice>");
		}
		sBuf.append("</pie>");
		
		return sBuf.toString();
	}
	
	//获取柱状图的xml
	private String getColumnAndLineXML(List<String> oList){
		int xid = 0;
		StringBuffer sBuf = new StringBuffer();
		sBuf.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		sBuf.append("<chart>");
		sBuf.append("	<series>");
		
		for(int i=0;i<oList.size();){
			sBuf.append("		<value xid=\"").append(xid++).append("\">").append(oList.get(i++)).append("</value>");
			i++;		//skip
		}
		
		sBuf.append("	</series>");
		sBuf.append("	<graphs>");
		sBuf.append("		<graph gid=\"1\">");
		
		xid = 0;
		for(int i=0;i<oList.size();){
			i++;		//skip
			sBuf.append("			<value xid=\"").append(xid++).append("\" color=\"#318DBD\">").append(oList.get(i++)).append("</value>");
		}
		
		sBuf.append("		</graph>");
		sBuf.append("	</graphs>");
		sBuf.append("</chart>");
		
		return sBuf.toString();
	}
	
	//写文件，写文本文件
	private void writeXML(String path, String dir, String content) throws FileNotFoundException{
		FileUtil fu = new FileUtil();
		fu.createTxt(path + "/stat/chart/"+dir+"/", "data.xml", content, "utf-8");
	}
}
