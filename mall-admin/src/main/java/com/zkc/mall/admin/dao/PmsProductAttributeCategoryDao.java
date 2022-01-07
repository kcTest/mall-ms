package com.zkc.mall.admin.dao;

import com.zkc.mall.admin.dto.PmsProductAttributeCategoryItem;

import java.util.List;

public interface PmsProductAttributeCategoryDao {
	
	List<PmsProductAttributeCategoryItem> getListWithAttr();
}
