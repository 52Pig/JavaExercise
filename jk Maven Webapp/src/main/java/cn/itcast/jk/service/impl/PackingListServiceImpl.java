package cn.itcast.jk.service.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.itcast.jk.dao.ExportDao;
import cn.itcast.jk.dao.PackingListDao;
import cn.itcast.jk.domain.Export;
import cn.itcast.jk.domain.PackingList;
import cn.itcast.jk.pagination.Page;
import cn.itcast.jk.service.PackingListService;

@Service
public class PackingListServiceImpl implements PackingListService {
	@Resource
	PackingListDao packingListDao;
	@Resource
	ExportDao exportDao;

	@Override
	public List<PackingList> findPage(Page page) {
		return packingListDao.findPage(page);
	}

	@Override
	public List<PackingList> find(Map paraMap) {
		return packingListDao.find(paraMap);
	}

	@Override
	public PackingList get(Serializable id) {
		return packingListDao.get(id);
	}

	@Override
	public void insert(PackingList entity) {
		entity.setId(UUID.randomUUID().toString());
		
		//处理状态流转
		//1.查出装箱关联多个报运，2.将它们的状态都修改为2-装箱
		
		Map map = new HashMap();
		map.put("state", 2);			//2-装箱
		map.put("ids",entity.getExportIds().split("\\|"));	//报运ids
		
		exportDao.updateState(map);
		
		packingListDao.insert(entity);
	}

	@Override
	public void update(PackingList entity) {
		packingListDao.update(entity);
	}

	@Override
	public void deleteById(Serializable id) {
		packingListDao.deleteById(id);
	}

	@Override
	public void delete(Serializable[] ids) {
		packingListDao.delete(ids);
	}

	@Override
	public String getDivDataCreate(String[] exportIds) {
		//只传递报运id
		StringBuffer sBuf = new StringBuffer();
		for(int i=0;i<exportIds.length;i++){
			Export export = exportDao.get(exportIds[i]);
			sBuf.append("<input type=\"checkbox\" name=\"exportIds\" value=\"").append(exportIds[i]).append("|").append(export.getCustomerContract()).append("\" checked class=\"input\"/>");
			sBuf.append(export.getCustomerContract()).append("&nbsp&nbsp;");
		}
		return sBuf.toString();
	}

	@Override
	public String getDivDataUpdate(PackingList entity) {
		String[] exportIds = entity.getExportIds().split("\\|");
		String[] exportNos = entity.getExportNos().split("\\|");
		
		//只传递报运id
		StringBuffer sBuf = new StringBuffer();
		for(int i=0;i<exportIds.length;i++){
			sBuf.append("<input type=\"checkbox\" name=\"exportIds\" value=\"").append(exportIds[i]).append("|").append(exportNos[i]).append("\" checked class=\"input\"/>");
			sBuf.append(exportNos[i]).append("&nbsp&nbsp;");
		}
		return sBuf.toString();
	}

	@Override
	public String getDivDataView(PackingList entity, String contextPath) {
		String[] exportIds = entity.getExportIds().split("\\|");
		String[] exportNos = entity.getExportNos().split("\\|");
		
		//只传递报运id
		StringBuffer sBuf = new StringBuffer();
		for(int i=0;i<exportIds.length;i++){
			sBuf.append("<a href='").append(contextPath).append("/cargo/export/toview.action?id=").append(exportIds[i]).append("'>");
			sBuf.append(exportNos[i]).append("</a>&nbsp&nbsp;");
		}
		return sBuf.toString();
	}

}
