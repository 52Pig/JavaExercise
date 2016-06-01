package cn.itcast.jk.dao.impl;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import cn.itcast.jk.dao.ExtCproductDao;
import cn.itcast.jk.domain.ExtCproduct;

@Repository
public class ExtCproductDaoImpl extends BaseDaoImpl<ExtCproduct> implements ExtCproductDao {
	//默认构造方法设置命名空间
	public ExtCproductDaoImpl() {
		super.setNs("cn.itcast.jk.mapper.ExtCproductMapper");
	}

	public void deleteByContractId(Serializable contractId) {
		super.getSqlSession().delete(super.getNs()+".deleteByContractId", contractId);
	}

}
