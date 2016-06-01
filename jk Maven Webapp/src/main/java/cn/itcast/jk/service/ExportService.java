package cn.itcast.jk.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import cn.itcast.jk.domain.Export;
import cn.itcast.jk.pagination.Page;

public interface ExportService {
	public List<Export> findPage(Page page);	//分页查询
	public List<Export> find(Map paraMap);		//带条件查询，条件可以为null，既没有条件；返回list对象集合
	public Export get(String id);				//只查询一个，常用于修改
	public Export wsview(String contractNo);	//按合同号查询报运信息
	public void insert(Serializable[] ids);		//合同的id
	public void update(Export entity,
			Integer[] mr_orderNo,
			String[] mr_id,
			Integer[] mr_changed,
			Integer[] mr_cnumber,
			Double[] mr_grossWeight,
			Double[] mr_netWeight,
			Double[] mr_sizeLength,
			Double[] mr_sizeWidth,
			Double[] mr_sizeHeight,
			Double[] mr_exPrice,
			Double[] mr_tax
	);			//修改，用实体作为参数
	public void deleteById(Serializable id);		//按id删除，删除一条；支持整数型和字符串类型ID
	public void delete(Serializable[] ids);			//批量删除；支持整数型和字符串类型ID

	public void submit(Serializable[] ids);	//上报
	public void cancel(Serializable[] ids);	//取消
}
