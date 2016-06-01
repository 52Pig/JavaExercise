package cn.itcast.jk.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.itcast.jk.dao.ContractDao;
import cn.itcast.jk.domain.Contract;
import cn.itcast.jk.vo.ContractVO;

@Repository
public class ContractDaoImpl extends BaseDaoImpl<Contract> implements ContractDao {
	//默认构造方法设置命名空间
	public ContractDaoImpl() {
		super.setNs("cn.itcast.jk.mapper.ContractMapper");
	}

	public void updateState(Map<String, Object> map) {
		super.getSqlSession().update(this.getNs() + ".updateState", map);
	}

	public ContractVO view(String id) {
		return super.getSqlSession().selectOne(this.getNs()+".view", id);
	}
}
