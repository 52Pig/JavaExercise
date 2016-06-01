package cn.itcast.jk.service.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.itcast.jk.dao.FactoryDao;
import cn.itcast.jk.pagination.Page;
import cn.itcast.jk.service.FactoryService;

@Service
public class FactoryServiceImpl implements FactoryService {
	@Resource
	FactoryDao factoryDao;

	@Override
	public List<cn.itcast.jk.domain.Factory> findPage(Page page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<cn.itcast.jk.domain.Factory> find(Map paraMap) {
		return factoryDao.find(paraMap);
	}

	@Override
	public cn.itcast.jk.domain.Factory get(Serializable id) {
		return factoryDao.get(id);
	}

	@Override
	public void insert(cn.itcast.jk.domain.Factory entity) {
		entity.setId(UUID.randomUUID().toString());
		entity.setState("1");			//0停用1启用
		factoryDao.insert(entity);
	}

	@Override
	public void update(cn.itcast.jk.domain.Factory entity) {
		factoryDao.update(entity);
	}

	@Override
	public void deleteById(Serializable id) {
		factoryDao.deleteById(id);
	}

	@Override
	public void delete(Serializable[] ids) {
		factoryDao.delete(ids);
	}

	@Override
	public void start(String[] ids) {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("state", 1);		//1启用
		map.put("ids", ids);
		factoryDao.updateState(map);
	}

	@Override
	public void stop(String[] ids) {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("state", 0);		//1停用
		map.put("ids", ids);
		factoryDao.updateState(map);
	}

}
