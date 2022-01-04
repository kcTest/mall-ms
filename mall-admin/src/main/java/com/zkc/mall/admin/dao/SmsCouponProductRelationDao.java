package com.zkc.mall.admin.dao;

import com.zkc.mall.mbg.model.SmsCouponProductRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SmsCouponProductRelationDao {
	
	int insertList(@Param("list") List<SmsCouponProductRelation> productRelationList);
}
