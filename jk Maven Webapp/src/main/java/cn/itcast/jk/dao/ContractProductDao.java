package cn.itcast.jk.dao;

import java.io.Serializable;

import cn.itcast.jk.domain.ContractProduct;

public interface ContractProductDao extends BaseDao<ContractProduct> {
	public void deleteByContractId(Serializable contractId);
}
