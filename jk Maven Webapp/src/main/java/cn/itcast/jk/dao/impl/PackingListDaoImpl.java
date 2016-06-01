package cn.itcast.jk.dao.impl;

import org.springframework.stereotype.Repository;

import cn.itcast.jk.dao.PackingListDao;
import cn.itcast.jk.domain.PackingList;

@Repository
public class PackingListDaoImpl extends BaseDaoImpl<PackingList> implements PackingListDao {
	//默认构造方法设置命名空间
	public PackingListDaoImpl() {
		super.setNs("cn.itcast.jk.mapper.PackingListMapper");
	}
}
