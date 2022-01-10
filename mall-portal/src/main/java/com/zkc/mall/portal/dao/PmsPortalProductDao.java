package com.zkc.mall.portal.dao;

import com.zkc.mall.mbg.model.SmsCoupon;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PmsPortalProductDao {
	
	List<SmsCoupon> getAvailableCouponList(@Param("productId") Long productId, @Param("productCategoryId") Long productCategoryId);
}
