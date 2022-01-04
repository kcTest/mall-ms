package com.zkc.mall.admin.service;

import com.zkc.mall.admin.dto.SmsFlashPromotionProduct;
import com.zkc.mall.mbg.model.SmsFlashPromotionProductRelation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SmsFlashPromotionProductRelationService {
	
	@Transactional
	int create(List<SmsFlashPromotionProductRelation> flashPromotionProductRelationList);
	
	int update(Long id, SmsFlashPromotionProductRelation flashPromotionProductRelation);
	
	int delete(Long id);
	
	SmsFlashPromotionProductRelation getItem(Long id);
	
	List<SmsFlashPromotionProduct> list(Long flashPromotionId, Long flashPromotionSessionId, Integer pageSize, Integer pageNum);
	
	long getCount(Long flashPromotionId, Long flashPromotionSessionId);
}
