package com.zkc.mall.admin.service;

import com.zkc.mall.mbg.model.SmsFlashPromotion;

import java.util.List;

public interface SmsFlashPromotionService {
	
	int create(SmsFlashPromotion flashPromotion);
	
	int update(Long id, SmsFlashPromotion flashPromotion);
	
	int delete(Long id);
	
	int updateStatus(Long id, Integer status);
	
	SmsFlashPromotion getItem(Long id);
	
	List<SmsFlashPromotion> list(String title, Integer pageSize, Integer pageNum);
}
