package cn.itcast.jk.dao;


import java.util.Map;

import cn.itcast.jk.domain.Factory;

public interface FactoryDao extends BaseDao<Factory> {
	public void updateState(Map<String,Object> map);	//修改状态
}
