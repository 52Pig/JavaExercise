package cn.itcast.jk.service;

import java.util.List;

import cn.itcast.jk.vo.OutProductVO;

public interface OutProductService {
	public List<OutProductVO> find(String inputDate);
}
