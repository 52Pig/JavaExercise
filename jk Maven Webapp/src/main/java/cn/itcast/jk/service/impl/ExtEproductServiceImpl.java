package cn.itcast.jk.service.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.itcast.jk.dao.ExtEproductDao;
import cn.itcast.jk.dao.FactoryDao;
import cn.itcast.jk.dao.SysCodeDao;
import cn.itcast.jk.domain.Factory;
import cn.itcast.jk.domain.SysCode;
import cn.itcast.jk.pagination.Page;
import cn.itcast.jk.service.ExtEproductService;
import cn.itcast.util.Arith;

@Service
public class ExtEproductServiceImpl implements ExtEproductService {
	@Resource
	ExtEproductDao extEproductDao;
	@Resource
	FactoryDao factoryDao;

	@Override
	public List<cn.itcast.jk.domain.ExtEproduct> findPage(Page page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<cn.itcast.jk.domain.ExtEproduct> find(Map paraMap) {
		return extEproductDao.find(paraMap);
	}

	@Override
	public cn.itcast.jk.domain.ExtEproduct get(Serializable id) {
		return extEproductDao.get(id);
	}

	@Override
	public void insert(cn.itcast.jk.domain.ExtEproduct extEproduct) {
		extEproduct.setId(UUID.randomUUID().toString());
		extEproductDao.insert(extEproduct);
	}

	@Override
	public void update(cn.itcast.jk.domain.ExtEproduct extEproduct) {
		extEproductDao.update(extEproduct);
	}

	@Override
	public void deleteById(Serializable id) {
		extEproductDao.deleteById(id);
	}

	@Override
	public void delete(Serializable[] ids) {
		extEproductDao.delete(ids);
	}

}
