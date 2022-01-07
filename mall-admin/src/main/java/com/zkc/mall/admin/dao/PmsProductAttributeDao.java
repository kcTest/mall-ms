package com.zkc.mall.admin.dao;

import com.zkc.mall.admin.dto.ProductAttrInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PmsProductAttributeDao {
	
	List<ProductAttrInfo> getAttrInfo(@Param("productCategoryId") Long productCategoryId);
}
