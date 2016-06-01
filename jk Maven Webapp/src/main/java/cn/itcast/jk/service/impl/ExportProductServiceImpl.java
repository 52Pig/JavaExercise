package cn.itcast.jk.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.itcast.jk.dao.ExportProductDao;
import cn.itcast.jk.pagination.Page;
import cn.itcast.jk.service.ExportProductService;

@Service
public class ExportProductServiceImpl implements ExportProductService {
	@Resource
	ExportProductDao exportProductDao;

	@Override
	public List<cn.itcast.jk.domain.ExportProduct> findPage(Page page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<cn.itcast.jk.domain.ExportProduct> find(Map paraMap) {
		return exportProductDao.find(paraMap);
	}

	@Override
	public cn.itcast.jk.domain.ExportProduct get(Serializable id) {
		return exportProductDao.get(id);
	}

	@Override
	public void insert(cn.itcast.jk.domain.ExportProduct exportProduct) {
		exportProduct.setId(UUID.randomUUID().toString());
		exportProductDao.insert(exportProduct);
	}

	@Override
	public void update(cn.itcast.jk.domain.ExportProduct exportProduct) {
		exportProductDao.update(exportProduct);
	}

	@Override
	public void deleteById(Serializable id) {
		exportProductDao.deleteById(id);
	}

	@Override
	public void delete(Serializable[] ids) {
		exportProductDao.delete(ids);
	}
}
