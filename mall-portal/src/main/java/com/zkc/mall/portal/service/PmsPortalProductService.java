package com.zkc.mall.portal.service;

import com.zkc.mall.mbg.model.PmsProduct;
import com.zkc.mall.portal.domain.PmsPortalProductDetail;
import com.zkc.mall.portal.domain.PmsProductCategoryNode;

import java.util.List;

public interface PmsPortalProductService {
	
	List<PmsProduct> search(String keyword, Long brandId, Long productCategoryId, Integer sort, Integer pageSize, Integer pageNum);
	
	List<PmsProductCategoryNode> categoryTreeList();
	
	PmsPortalProductDetail detail(Long productId);
}
