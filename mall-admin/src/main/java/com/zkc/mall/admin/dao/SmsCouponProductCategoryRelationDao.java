package com.zkc.mall.admin.dao;

import com.zkc.mall.mbg.model.SmsCouponProductCategoryRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SmsCouponProductCategoryRelationDao {
	
	int insertList(@Param("list") List<SmsCouponProductCategoryRelation> couponProductCategoryRelationList);
}
