package com.zkc.mall.admin.dao;

import com.zkc.mall.admin.dto.PmsProductCategoryWithChildrenItem;

import java.util.List;

public interface PmsProductCategoryDao {
	
	List<PmsProductCategoryWithChildrenItem> listWithChildren();
}
