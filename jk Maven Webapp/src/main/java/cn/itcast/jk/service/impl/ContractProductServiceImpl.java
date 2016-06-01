package cn.itcast.jk.service.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.itcast.jk.dao.ContractProductDao;
import cn.itcast.jk.dao.FactoryDao;
import cn.itcast.jk.domain.Factory;
import cn.itcast.jk.pagination.Page;
import cn.itcast.jk.service.ContractProductService;
import cn.itcast.util.Arith;

@Service
public class ContractProductServiceImpl implements ContractProductService {
	@Resource
	ContractProductDao contractProductDao;
	@Resource
	FactoryDao factoryDao;

	@Override
	public List<cn.itcast.jk.domain.ContractProduct> findPage(Page page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<cn.itcast.jk.domain.ContractProduct> find(Map paraMap) {
		return contractProductDao.find(paraMap);
	}

	@Override
	public cn.itcast.jk.domain.ContractProduct get(Serializable id) {
		return contractProductDao.get(id);
	}

	@Override
	public void insert(cn.itcast.jk.domain.ContractProduct contractProduct) {
		contractProduct.setId(UUID.randomUUID().toString());
		//计算浮点数 总金额=数量*单价
		Arith arith = new Arith();
		if(contractProduct.getCnumber()!=null && contractProduct.getPrice()!=null){
			contractProduct.setAmount(new BigDecimal(arith.mul(contractProduct.getCnumber().doubleValue(), contractProduct.getPrice().doubleValue())));
		}
		//合同总金额。。。
		
		contractProductDao.insert(contractProduct);
	}

	@Override
	public void update(cn.itcast.jk.domain.ContractProduct contractProduct) {
		//计算浮点数 总金额=数量*单价
		Arith arith = new Arith();
		if(contractProduct.getCnumber()!=null && contractProduct.getPrice()!=null){
			contractProduct.setAmount(new BigDecimal(arith.mul(contractProduct.getCnumber().doubleValue(), contractProduct.getPrice().doubleValue())));
		}
		//合同总金额。。。
		contractProductDao.update(contractProduct);
	}

	@Override
	public void deleteById(Serializable id) {
		contractProductDao.deleteById(id);
	}

	@Override
	public void delete(Serializable[] ids) {
		contractProductDao.delete(ids);
	}

	@Override
	public List<Factory> getFactoryList() {
		Map paraMap = new HashMap();
		paraMap.put("state", 1);			//1启用
		
		return factoryDao.find(paraMap);
	}

}
