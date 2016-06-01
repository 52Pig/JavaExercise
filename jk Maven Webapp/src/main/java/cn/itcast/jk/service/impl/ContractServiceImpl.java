package cn.itcast.jk.service.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.itcast.common.springdao.SqlDao;
import cn.itcast.jk.dao.ContractDao;
import cn.itcast.jk.dao.ContractProductDao;
import cn.itcast.jk.dao.ExtCproductDao;
import cn.itcast.jk.pagination.Page;
import cn.itcast.jk.service.ContractService;
import cn.itcast.jk.vo.ContractVO;
import cn.itcast.util.UtilFuns;

@Service
public class ContractServiceImpl implements ContractService {
	@Resource
	ContractDao contractDao;
	@Resource
	ContractProductDao contractProductDao;
	@Resource
	ExtCproductDao extCproductDao;
	@Resource
	SqlDao sqlDao;

	@Override
	public List<cn.itcast.jk.domain.Contract> findPage(Page page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<cn.itcast.jk.domain.Contract> find(Map paraMap) {
		return contractDao.find(paraMap);
	}

	@Override
	public cn.itcast.jk.domain.Contract get(Serializable id) {
		return contractDao.get(id);
	}

	@Override
	public void insert(cn.itcast.jk.domain.Contract entity) {
		entity.setId(UUID.randomUUID().toString());
		entity.setState(0);			//0草稿1已上报
		contractDao.insert(entity);
	}

	@Override
	public void update(cn.itcast.jk.domain.Contract entity) {
		contractDao.update(entity);
	}

	//级联删除
	public void deleteById(Serializable id) {
		extCproductDao.deleteByContractId(id);		//删除合同下的附件信息
		contractProductDao.deleteByContractId(id);	//删除合同下的货物信息
		contractDao.deleteById(id);
	}

	@Override
	public void delete(Serializable[] ids) {
		for(int i=0;i<ids.length;i++){
			extCproductDao.deleteByContractId(ids[i]);		//删除合同下的附件信息
			contractProductDao.deleteByContractId(ids[i]);	//删除合同下的货物信息
		}
		contractDao.delete(ids);
	}

	@Override
	public void submit(Serializable[] ids) {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("state", 1);		//1上报
		map.put("ids", ids);
		contractDao.updateState(map);
	}

	@Override
	public void cancel(Serializable[] ids) {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("state", 0);		//0草稿
		map.put("ids", ids);
		contractDao.updateState(map);
	}

	public ContractVO view(String id) {
		return contractDao.view(id);
	}

	@Override
	public void hole(String[] ids,String from, String to) {			//x,y
//		String inIds = UtilFuns.joinInStr(ids);		//'x','y'
//		
//		String[] sqls = new String[6];
//		sqls[0] = "INSERT INTO contract_his_c SELECT * FROM contract_c WHERE contract_id IN ("+inIds+");";
//		sqls[1] = "INSERT INTO contract_product_his_c SELECT * FROM contract_product_c WHERE contract_product_id IN (SELECT contract_product_id FROM contract_product_c WHERE contract_id IN ("+inIds+"));";
//		sqls[2] = "INSERT INTO ext_cproduct_his_c SELECT * FROM ext_cproduct_c WHERE ext_cproduct_id IN (SELECT ext_cproduct_id FROM ext_cproduct_c WHERE contract_product_id IN (SELECT contract_product_id FROM contract_product_c WHERE contract_id IN ("+inIds+")));";
//		
////		sqls[3] = "DELETE FROM ext_cproduct_c WHERE ext_cproduct_id IN (SELECT ext_cproduct_id FROM ext_cproduct_c WHERE contract_product_id IN (SELECT contract_product_id FROM contract_product_c WHERE contract_id IN ("+inIds+")));";
////		sqls[4] = "DELETE FROM contract_product_c WHERE contract_product_id IN (SELECT contract_product_id FROM contract_product_c WHERE contract_id IN ("+inIds+"));";
////		sqls[5] = "DELETE FROM contract_c WHERE contract_id IN ("+inIds+");";
//		
//		sqls[3] = "DELETE FROM ext_cproduct_c WHERE contract_product_id IN (SELECT contract_product_id FROM contract_product_c WHERE contract_id IN ("+inIds+"));";
//		sqls[4] = "DELETE FROM contract_product_c WHERE contract_id IN ("+inIds+");";
//		sqls[5] = "DELETE FROM contract_c WHERE contract_id IN ("+inIds+");";		
//		for(String s : sqls){
//			System.out.println(s);
//		}
//		
//		//sqlDao.batchSQL(sqls);			//批量执行sql
		
		String inStr = UtilFuns.joinInStr(ids);		//'x','y'
		StringBuffer sBuf = new StringBuffer();
		sBuf.append("INSERT INTO contract").append(to).append("_c SELECT * FROM contract").append(from).append("_c WHERE contract_id IN (").append(inStr).append(");");
		sBuf.append("INSERT INTO contract_product").append(to).append("_c SELECT * FROM contract_product").append(from).append("_c WHERE contract_id IN (").append(inStr).append(");");
		sBuf.append("INSERT INTO ext_cproduct").append(to).append("_c SELECT * FROM ext_cproduct").append(from).append("_c WHERE contract_product_id IN ( SELECT contract_product_id FROM contract_product").append(from).append("_c WHERE contract_id IN (").append(inStr).append("));");
		
		sBuf.append("delete FROM ext_cproduct").append(from).append("_c WHERE contract_product_id IN ( SELECT contract_product_id FROM contract_product").append(from).append("_c WHERE contract_id IN (").append(inStr).append("));");
		sBuf.append("delete from contract_product").append(from).append("_c WHERE contract_id IN (").append(inStr).append(");");
		sBuf.append("delete from contract").append(from).append("_c WHERE contract_id IN (").append(inStr).append(");");
		
		sqlDao.batchSQL(sBuf.toString().split(";"));
	}
}
