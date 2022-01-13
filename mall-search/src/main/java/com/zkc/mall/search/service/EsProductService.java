package com.zkc.mall.search.service;

import com.zkc.mall.search.domain.EsProduct;
import com.zkc.mall.search.domain.EsProductRelatedInfo;

import java.util.List;

public interface EsProductService {
	
	int importAll();
	
	void delete(Long id);
	
	void delete(List<Long> ids);
	
	EsProduct create(Long id);
	
	List<EsProduct> search(String keyword, Integer pageSize, Integer pageNum);
	
	List<EsProduct> search(String keyword, Long brandId, Long productCategoryId, Integer sort, Integer pageSize, Integer pageNum);
	
	List<EsProduct> recommend(Long id, Integer pageSize, Integer pageNum);
	
	EsProductRelatedInfo searchRelatedInfo(String keyword);
	
}
