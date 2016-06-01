package cn.itcast.jk.service;

import java.util.List;
import java.util.Map;

import cn.itcast.jk.domain.Contract;

public interface ContractHisService {
	public List<Contract> find(Map paraMap);		//带条件查询，条件可以为null，既没有条件；返回list对象集合
}
