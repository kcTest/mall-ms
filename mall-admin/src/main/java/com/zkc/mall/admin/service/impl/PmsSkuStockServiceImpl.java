package com.zkc.mall.admin.service.impl;

import cn.hutool.core.util.StrUtil;
import com.zkc.mall.admin.dao.PmsSkuStockDao;
import com.zkc.mall.admin.service.PmsSkuStockService;
import com.zkc.mall.mbg.mapper.PmsProductMapper;
import com.zkc.mall.mbg.mapper.PmsSkuStockMapper;
import com.zkc.mall.mbg.model.PmsProductExample;
import com.zkc.mall.mbg.model.PmsSkuStock;
import com.zkc.mall.mbg.model.PmsSkuStockExample;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@Service
public class PmsSkuStockServiceImpl implements PmsSkuStockService {
	
	@Autowired
	private PmsSkuStockMapper skuStockMapper;
	
	@Autowired
	private PmsSkuStockDao skuStockDao;
	
	@Override
	public List<PmsSkuStock> getList(Long pid, String skuCode) {
		PmsSkuStockExample example = new PmsSkuStockExample();
		example.createCriteria().andIdEqualTo(pid);
		if (!StrUtil.isEmpty(skuCode)) {
			example.createCriteria().andSkuCodeLike("%" + skuCode + "%");
		}
		return skuStockMapper.selectByExample(example);
	}
	
	@Override
	public int update(List<PmsSkuStock> skuStockList) {
		
		return skuStockDao.replaceList(skuStockList);
	}
}
