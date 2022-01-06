package com.zkc.mall.admin.dao;

import com.zkc.mall.mbg.model.PmsProductCategoryAttributeRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PmsProductCategoryAttributeRelationDao {
	
	int insertList(@Param("list") List<PmsProductCategoryAttributeRelation> relationList);
}
