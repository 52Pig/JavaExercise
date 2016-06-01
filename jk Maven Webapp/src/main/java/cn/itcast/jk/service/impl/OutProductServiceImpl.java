package cn.itcast.jk.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.itcast.jk.dao.OutProductDao;
import cn.itcast.jk.service.OutProductService;
import cn.itcast.jk.vo.OutProductVO;

@Service
public class OutProductServiceImpl implements OutProductService {
	@Resource
	OutProductDao outProductDao;

	public List<OutProductVO> find(String inputDate) {
		Map paraMap = new HashMap();
		paraMap.put("inputDate", inputDate);
		
		return outProductDao.find(paraMap);
	}
}
