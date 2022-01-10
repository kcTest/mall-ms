package com.zkc.mall.portal.service;

import com.zkc.mall.mbg.model.PmsBrand;
import com.zkc.mall.mbg.model.PmsProduct;

import java.util.List;

public interface PortalBrandService {
	
	List<PmsBrand> recommendList(Integer pageSize, Integer pageNum);
	
	PmsBrand detail(Long brandId);
	
	List<PmsProduct> productList(Long brandId, Integer pageSize, Integer pageNum);
}
