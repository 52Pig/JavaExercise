package cn.itcast.jk.service.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.itcast.jk.dao.ExtCproductDao;
import cn.itcast.jk.dao.FactoryDao;
import cn.itcast.jk.dao.SysCodeDao;
import cn.itcast.jk.domain.Factory;
import cn.itcast.jk.domain.SysCode;
import cn.itcast.jk.pagination.Page;
import cn.itcast.jk.service.ExtCproductService;
import cn.itcast.util.Arith;

@Service
public class ExtCproductServiceImpl implements ExtCproductService {
	@Resource
	ExtCproductDao extCproductDao;
	@Resource
	FactoryDao factoryDao;
	@Resource
	SysCodeDao sysCodeDao;

	@Override
	public List<cn.itcast.jk.domain.ExtCproduct> findPage(Page page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<cn.itcast.jk.domain.ExtCproduct> find(Map paraMap) {
		return extCproductDao.find(paraMap);
	}

	@Override
	public cn.itcast.jk.domain.ExtCproduct get(Serializable id) {
		return extCproductDao.get(id);
	}

	@Override
	public void insert(cn.itcast.jk.domain.ExtCproduct extCproduct) {
		extCproduct.setId(UUID.randomUUID().toString());
		//计算浮点数 总金额=数量*单价
		if(extCproduct.getCnumber()!=null && extCproduct.getPrice()!=null){
			extCproduct.setAmount(extCproduct.getCnumber()*extCproduct.getPrice());
		}
		//合同总金额。。。
		
		extCproductDao.insert(extCproduct);
	}

	@Override
	public void update(cn.itcast.jk.domain.ExtCproduct extCproduct) {
		//计算浮点数 总金额=数量*单价
		Arith arith = new Arith();
		if(extCproduct.getCnumber()!=null && extCproduct.getPrice()!=null){
			extCproduct.setAmount(extCproduct.getCnumber()*extCproduct.getPrice());
		}
		//合同总金额。。。
		extCproductDao.update(extCproduct);
	}

	@Override
	public void deleteById(Serializable id) {
		extCproductDao.deleteById(id);
	}

	@Override
	public void delete(Serializable[] ids) {
		extCproductDao.delete(ids);
	}

	@Override
	public List<Factory> getFactoryList() {
		Map paraMap = new HashMap();
		paraMap.put("state", 1);			//1启用
		
		return factoryDao.find(paraMap);
	}

	@Override
	public List<SysCode> getCtypeList() {
		Map paraMap = new HashMap();
		paraMap.put("parentId", "0104");			//分类，在系统创建时初始化，用户不能进行变更；分类下的数据可以添加
		
		return sysCodeDao.find(paraMap);
	}

}
