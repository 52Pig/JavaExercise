package cn.itcast.jk.dao.impl;

import org.springframework.stereotype.Repository;

import cn.itcast.jk.dao.ExportProductDao;
import cn.itcast.jk.domain.ExportProduct;

@Repository
public class ExportProductDaoImpl extends BaseDaoImpl<ExportProduct> implements ExportProductDao {
	//默认构造方法设置命名空间
	public ExportProductDaoImpl() {
		super.setNs("cn.itcast.jk.mapper.ExportProductMapper");
	}

	@Override
	public void deleteByExportId(String exportId) {
		super.getSqlSession().delete(this.getNs()+".deleteByExportId", exportId);
	}
}
