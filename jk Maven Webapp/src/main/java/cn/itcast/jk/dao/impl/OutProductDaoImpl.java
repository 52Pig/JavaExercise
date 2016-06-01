package cn.itcast.jk.dao.impl;

import org.springframework.stereotype.Repository;

import cn.itcast.jk.dao.OutProductDao;
import cn.itcast.jk.vo.OutProductVO;

@Repository
public class OutProductDaoImpl extends BaseDaoImpl<OutProductVO> implements OutProductDao {
	//利用默认构造方法初始化命名空间
	public OutProductDaoImpl() {
		super.setNs("cn.itcast.jk.mapper.OutProductMapper");
	}

}
