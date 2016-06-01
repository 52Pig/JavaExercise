package cn.itcast.jk.dao.impl;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import cn.itcast.jk.dao.ContractProductDao;
import cn.itcast.jk.domain.ContractProduct;

@Repository
public class ContractProductDaoImpl extends BaseDaoImpl<ContractProduct> implements ContractProductDao {
	//默认构造方法设置命名空间
	public ContractProductDaoImpl() {
		super.setNs("cn.itcast.jk.mapper.ContractProductMapper");
	}

	public void deleteByContractId(Serializable contractId) {
		super.getSqlSession().delete(this.getNs()+".deleteByContractId", contractId);
	}

}
