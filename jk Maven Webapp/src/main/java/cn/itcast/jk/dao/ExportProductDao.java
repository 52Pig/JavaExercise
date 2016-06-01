package cn.itcast.jk.dao;

import cn.itcast.jk.domain.ExportProduct;

public interface ExportProductDao extends BaseDao<ExportProduct> {

	public void deleteByExportId(String exportId);
}
