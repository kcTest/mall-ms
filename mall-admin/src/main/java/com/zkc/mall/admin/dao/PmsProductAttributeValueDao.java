package com.zkc.mall.admin.dao;

import com.zkc.mall.mbg.model.PmsProductAttributeValue;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PmsProductAttributeValueDao {
	
	int insertList(@Param("list") List<PmsProductAttributeValue> productAttributeValueList);
}
