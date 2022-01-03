package com.zkc.mall.admin.service;

import com.zkc.mall.mbg.model.UmsResourceCategory;

import java.util.List;

public interface UmsResourceCategoryService {
	
	List<UmsResourceCategory> listAll();
	
	int create(UmsResourceCategory umsResourceCategory);
	
	int update(Long id, UmsResourceCategory umsResourceCategory);
	
	int delete(Long id);
}
