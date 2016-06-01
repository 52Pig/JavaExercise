package cn.itcast.jk.dao.impl;

import org.springframework.stereotype.Repository;

import cn.itcast.jk.dao.ContractHisDao;
import cn.itcast.jk.domain.Contract;

@Repository
public class ContractHisDaoImpl extends BaseDaoImpl<Contract> implements ContractHisDao {
	//默认构造方法设置命名空间
	public ContractHisDaoImpl() {
		super.setNs("cn.itcast.jk.mapper.ContractHisMapper");
	}
}
