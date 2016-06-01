package cn.itcast.jk.dao.impl;

import org.springframework.stereotype.Repository;

import cn.itcast.jk.dao.ExtEproductDao;
import cn.itcast.jk.domain.ExtEproduct;

@Repository
public class ExtEproductDaoImpl extends BaseDaoImpl<ExtEproduct> implements ExtEproductDao {
	//默认构造方法设置命名空间
	public ExtEproductDaoImpl() {
		super.setNs("cn.itcast.jk.mapper.ExtEproductMapper");
	}

	@Override
	public void deleteByExportId(String exportId) {
		super.getSqlSession().delete(this.getNs()+".deleteByExportId", exportId);
	}

}
