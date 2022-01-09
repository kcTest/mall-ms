package com.zkc.mall.portal.dao;

import com.zkc.mall.portal.domain.PromotionProduct;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PortalProductDao {
	
	List<PromotionProduct> getPromotionProductList(@Param("ids") List<Long> productIdList);
}
