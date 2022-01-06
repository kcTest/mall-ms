package com.zkc.mall.admin.service;

import com.zkc.mall.mbg.model.PmsSkuStock;

import java.util.List;

public interface PmsSkuStockService {
	
	List<PmsSkuStock> getList(Long pid, String skuCode);
	
	int update(List<PmsSkuStock> skuStockList);
}
