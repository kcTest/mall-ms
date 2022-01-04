package com.zkc.mall.admin.dao;

import com.zkc.mall.admin.dto.SmsFlashPromotionProduct;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SmsFlashPromotionProductRelationDao {
	
	List<SmsFlashPromotionProduct> getList(@Param("flashPromotionId") Long flashPromotionId, @Param("flashPromotionSessionId") Long flashPromotionSessionId);
}
