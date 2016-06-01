package cn.itcast.jk.dao;

import java.util.Map;

import cn.itcast.jk.domain.Export;

public interface ExportDao extends BaseDao<Export> {
	public void updateState(Map<String, Object> map);
	public Export wsview(String contractNo);
}
