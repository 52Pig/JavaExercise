package cn.itcast.jk.service.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebService;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import cn.itcast.jk.dao.ContractDao;
import cn.itcast.jk.dao.ContractProductDao;
import cn.itcast.jk.dao.ExportDao;
import cn.itcast.jk.dao.ExportProductDao;
import cn.itcast.jk.dao.ExtCproductDao;
import cn.itcast.jk.dao.ExtEproductDao;
import cn.itcast.jk.domain.Contract;
import cn.itcast.jk.domain.ContractProduct;
import cn.itcast.jk.domain.Export;
import cn.itcast.jk.domain.ExportProduct;
import cn.itcast.jk.domain.ExtCproduct;
import cn.itcast.jk.domain.ExtEproduct;
import cn.itcast.jk.pagination.Page;
import cn.itcast.jk.service.ExportService;

@Service
@WebService
public class ExportServiceImpl implements ExportService {
	@Resource
	ExportDao exportDao;
	@Resource
	ExportProductDao exportProductDao;
	@Resource
	ExtEproductDao extEproductDao;
	@Resource 
	ContractDao contractDao;
	@Resource
	ContractProductDao contractProductDao;
	@Resource
	ExtCproductDao extCproductDao;
	
	@WebMethod(exclude=true)
	public void setExportDao(ExportDao exportDao){
		this.exportDao = exportDao;
	}

	@Override
	@WebMethod(exclude=true)
	public List<cn.itcast.jk.domain.Export> findPage(Page page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@WebMethod(exclude=true)
	public List<cn.itcast.jk.domain.Export> find(Map paraMap) {
		return exportDao.find(paraMap);
	}

	@Override
	public cn.itcast.jk.domain.Export get(String id) {
		return exportDao.get(id);
	}

	@Override
	public Export wsview(String contractNo) {
		return exportDao.wsview(contractNo);
	}

	@Override
	@WebMethod(exclude=true)
	public void insert(Serializable[] ids) {
		//开发步骤：
		//1.把选择的多个合同的ID和合同号保存到报运表中
		//2.把这几个合同下的货物信息插入到当前报运下的货物表中（冗余设计）
		//3.把这几个货物下的附件信息插入到当前对于的货物的附件下（冗余设计）
		//4.保存报运对象
		
		StringBuffer contractIdsBuf = new StringBuffer();			//合同id集合
		StringBuffer contractNosBuf = new StringBuffer();			//合同号集合
		for(Serializable contractId : ids ){
			contractIdsBuf.append(contractId).append(",");
			Contract contract = contractDao.get(contractId);	//获取合同对象
			contractNosBuf.append(contract.getContractNo()).append(" ");
		}
		contractIdsBuf.delete(contractIdsBuf.length()-1, contractIdsBuf.length());		//删除最后一个字符
		
		
		Export export = new Export();
		export.setId(UUID.randomUUID().toString());
		export.setContractIds(contractIdsBuf.toString());
		export.setCustomerContract(contractNosBuf.toString());
		export.setState(0);			//0草稿
		
		//处理货物信息
		for(int i=0;i<ids.length;i++){
			Map paraMap = new HashMap();
			paraMap.put("contractId", ids[i]);		//设置查询条件，查询某个合同下的货物信息
			List<ContractProduct> cpList = contractProductDao.find(paraMap);
			
			for(int j=0;j<cpList.size();j++){
				ContractProduct cp = cpList.get(j);	//获取当前货物
				
				ExportProduct ep = new ExportProduct();
				ep.setId(UUID.randomUUID().toString());
				ep.setExportId(export.getId());		//外键值
				
				ep.setProductNo(cp.getProductNo());
				ep.setFactoryName(cp.getFactoryName());
				ep.setPackingUnit(cp.getPackingUnit());
				ep.setCnumber(cp.getCnumber().intValue());	//bigDecimal转换为int
				ep.setBoxNum(cp.getBoxNum().intValue());
				
				exportProductDao.insert(ep);
				
				//处理附件
				Map extParaMap = new HashMap();
				extParaMap.put("contractProductId", cp.getId());	//获取当前货物下的所有附件
				List<ExtCproduct> extcpList = extCproductDao.find(extParaMap);
				for(int k=0;k<extcpList.size();k++){
					ExtCproduct extCproduct = extcpList.get(k);		//附件信息
					
					ExtEproduct extEproduct = new ExtEproduct();
					
					BeanUtils.copyProperties(extCproduct, extEproduct);
					extEproduct.setId(UUID.randomUUID().toString());
					extEproduct.setExportProductId(ep.getId());		//外键值
					
					extEproductDao.insert(extEproduct);
				}
				
			}
		}
		
		exportDao.insert(export);
	}

	@Override
	@WebMethod(exclude=true)
	public void deleteById(Serializable id) {
		exportDao.deleteById(id);
	}

	@Override
	@WebMethod(exclude=true)
	public void delete(Serializable[] ids) {
		//级联删除
		for(Serializable exportId : ids){
			extEproductDao.deleteByExportId((String)exportId);
			exportProductDao.deleteByExportId((String)exportId);
		}
		exportDao.delete(ids);
	}

	@Override
	@WebMethod(exclude=true)
	public void submit(Serializable[] ids) {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("state", 1);		//1上报
		map.put("ids", ids);
		exportDao.updateState(map);
	}

	@Override
	@WebMethod(exclude=true)
	public void cancel(Serializable[] ids) {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("state", 0);		//0草稿
		map.put("ids", ids);
		exportDao.updateState(map);
	}

	@Override
	@WebMethod(exclude=true)
	public void update(Export entity,Integer[] mr_orderNo, String[] mr_id, Integer[] mr_changed,
			Integer[] mr_cnumber, Double[] mr_grossWeight,
			Double[] mr_netWeight, Double[] mr_sizeLength,
			Double[] mr_sizeWidth, Double[] mr_sizeHeight, Double[] mr_exPrice,
			Double[] mr_tax) {
		
		exportDao.update(entity);
		
		//处理货物信息
		for(int i=0;i<mr_orderNo.length;i++){
			if(mr_changed.equals("1")){			//修改优化
				ExportProduct ep = exportProductDao.get(mr_id[i]);
				
				ep.setCnumber(mr_cnumber[i]);
				ep.setGrossWeight(mr_grossWeight[i]);
				ep.setNetWeight(mr_netWeight[i]);
				ep.setSizeLength(mr_sizeLength[i]);
				ep.setSizeWidth(mr_sizeWidth[i]);
				ep.setSizeHeight(mr_sizeHeight[i]);
				ep.setExPrice(mr_exPrice[i]);
				ep.setTax(mr_tax[i]);
				
				exportProductDao.update(ep);
			}
		}
	}


}
