package com.zkc.mall.portal.dao;

import com.zkc.mall.mbg.model.CmsSubject;
import com.zkc.mall.mbg.model.PmsBrand;
import com.zkc.mall.mbg.model.PmsProduct;
import com.zkc.mall.portal.domain.FlashPromotionProduct;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HomeDao {
	
	List<PmsBrand> getRecommendBrandList(@Param("offset") int offset, @Param("pageSize") Integer pageSize);
	
	List<PmsProduct> getHotProductList(@Param("offset") int offset, @Param("pageSize") Integer pageSize);
	
	List<PmsProduct> getNewProductList(@Param("offset") int offset, @Param("pageSize") Integer pageSize);
	
	/**
	 * 获取秒杀商品
	 */
	List<FlashPromotionProduct> getFlashProductList(@Param("flashPromotionId") Long flashPromotionId, @Param("sessionId") Long sessionId);
	
	
	List<CmsSubject> getRecommendSubjectList(@Param("offset") Integer offset, @Param("pageSize") Integer pageSize);
}
