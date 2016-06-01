package cn.itcast.jk.dao;

import java.util.Map;

import cn.itcast.jk.domain.Contract;
import cn.itcast.jk.vo.ContractVO;

public interface ContractDao extends BaseDao<Contract> {
	public void updateState(Map<String, Object> map);
	public ContractVO view(String id);
}
