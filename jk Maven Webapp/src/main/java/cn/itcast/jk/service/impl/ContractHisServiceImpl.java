package cn.itcast.jk.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.itcast.jk.dao.ContractHisDao;
import cn.itcast.jk.domain.Contract;
import cn.itcast.jk.service.ContractHisService;

@Service
public class ContractHisServiceImpl implements ContractHisService {
	@Resource
	ContractHisDao contractHisDao;

	@Override
	public List<Contract> find(Map paraMap) {
		// TODO Auto-generated method stub
		return contractHisDao.find(paraMap);
	}

}
