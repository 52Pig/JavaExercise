package cn.itcast.jk.dao;

import cn.itcast.jk.domain.ExtEproduct;

public interface ExtEproductDao extends BaseDao<ExtEproduct> {
	public void deleteByExportId(String exportId);
}
