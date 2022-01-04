package com.zkc.mall.admin.service;

import com.zkc.mall.admin.dto.SmsFlashPromotionSessionDetail;
import com.zkc.mall.mbg.model.SmsFlashPromotionSession;

import java.util.List;

public interface SmsFlashPromotionSessionService {
	
	int create(SmsFlashPromotionSession flashPromotionSession);
	
	int update(Long id, SmsFlashPromotionSession flashPromotionSession);
	
	int delete(Long id);
	
	int updateStatus(Long id, Integer status);
	
	SmsFlashPromotionSession getItem(Long id);
	
	List<SmsFlashPromotionSession> list();
	
	List<SmsFlashPromotionSessionDetail> selectList(Long flashPromotionId);
}
