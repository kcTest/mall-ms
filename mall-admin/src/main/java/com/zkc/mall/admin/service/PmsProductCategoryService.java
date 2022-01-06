package com.zkc.mall.admin.service;

import com.zkc.mall.admin.dto.PmsProductCategoryParam;
import com.zkc.mall.admin.dto.PmsProductParam;
import org.springframework.transaction.annotation.Transactional;

public interface PmsProductCategoryService {
	
	@Transactional
	int create(PmsProductCategoryParam product);
	
	@Transactional
	int update(Long id, PmsProductCategoryParam productCategoryParam);
}
