package com.zkc.mall.admin.service.impl;

import cn.hutool.core.util.StrUtil;
import com.zkc.mall.admin.dao.PmsSkuStockDao;
import com.zkc.mall.admin.service.PmsSkuStockService;
import com.zkc.mall.mbg.mapper.PmsSkuStockMapper;
import com.zkc.mall.mbg.model.PmsSkuStock;
import com.zkc.mall.mbg.model.PmsSkuStockExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		PmsSkuStockExample.Criteria criteria = example.createCriteria().andProductIdEqualTo(pid);
		if (!StrUtil.isEmpty(skuCode)) {
			criteria.andSkuCodeLike("%" + skuCode + "%");
		}
		return skuStockMapper.selectByExample(example);
	}
	
	@Override
	public int update(List<PmsSkuStock> skuStockList) {
		
		return skuStockDao.replaceList(skuStockList);
	}
}
