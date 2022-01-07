package com.zkc.mall.admin.service;

import com.zkc.mall.admin.dto.PmsProductCategoryParam;
import com.zkc.mall.admin.dto.PmsProductCategoryWithChildrenItem;
import com.zkc.mall.mbg.model.PmsProductCategory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PmsProductCategoryService {
	
	@Transactional
	int create(PmsProductCategoryParam product);
	
	@Transactional
	int update(Long id, PmsProductCategoryParam productCategoryParam);
	
	List<PmsProductCategory> getList(Long parentId, Integer pageSize, Integer pageNum);
	
	PmsProductCategory getItem(Long id);
	
	int delete(Long id);
	
	int updateNavStatus(List<Long> ids, Integer navStatus);
	
	int updateShowStatus(List<Long> ids, Integer showStatus);
	
	List<PmsProductCategoryWithChildrenItem> listWithChildren();
}
