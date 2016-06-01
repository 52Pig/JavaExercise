package cn.itcast.jk.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import cn.itcast.jk.domain.PackingList;
import cn.itcast.jk.pagination.Page;

public interface PackingListService {
	public List<PackingList> findPage(Page page);	//分页查询
	public List<PackingList> find(Map paraMap);		//带条件查询，条件可以为null，既没有条件；返回list对象集合
	public PackingList get(Serializable id);		//只查询一个，常用于修改
	public void insert(PackingList entity);			//插入，用实体作为参数
	public void update(PackingList entity);			//修改，用实体作为参数
	public void deleteById(Serializable id);		//按id删除，删除一条；支持整数型和字符串类型ID
	public void delete(Serializable[] ids);			//批量删除；支持整数型和字符串类型ID

	public String getDivDataCreate(String[] exportIds);	//获取报运号链接
	public String getDivDataUpdate(PackingList entity);	//获取报运号链接
	public String getDivDataView(PackingList entity, String contextPath);	//获取报运号链接
	
}
