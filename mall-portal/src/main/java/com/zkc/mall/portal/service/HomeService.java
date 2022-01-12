package com.zkc.mall.portal.service;

import com.zkc.mall.mbg.model.CmsSubject;
import com.zkc.mall.mbg.model.PmsProduct;
import com.zkc.mall.mbg.model.PmsProductCategory;
import com.zkc.mall.portal.domain.HomeContentResult;

import java.util.List;

public interface HomeService {
	
	HomeContentResult content();
	
	
	List<PmsProduct> recommendProductList(Integer pageSize, Integer pageNum);
	
	List<PmsProductCategory> getProductCategoryList(Long parentId);
	
	List<CmsSubject> getSubjectList(Long cateId, Integer pageSize, Integer pageNum);
	
	List<PmsProduct> hotProductList(Integer pageSize, Integer pageNum);
	
	List<PmsProduct> newProductList(Integer pageSize, Integer pageNum);
}
