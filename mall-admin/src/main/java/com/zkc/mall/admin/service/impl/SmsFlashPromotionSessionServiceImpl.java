package com.zkc.mall.admin.service.impl;

import com.zkc.mall.admin.dto.SmsFlashPromotionSessionDetail;
import com.zkc.mall.admin.service.SmsFlashPromotionProductRelationService;
import com.zkc.mall.admin.service.SmsFlashPromotionSessionService;
import com.zkc.mall.mbg.mapper.SmsFlashPromotionSessionMapper;
import com.zkc.mall.mbg.model.SmsFlashPromotionSession;
import com.zkc.mall.mbg.model.SmsFlashPromotionSessionExample;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SmsFlashPromotionSessionServiceImpl implements SmsFlashPromotionSessionService {
	
	@Autowired
	private SmsFlashPromotionSessionMapper flashPromotionSessionMapper;
	@Autowired
	private SmsFlashPromotionProductRelationService flashPromotionProductRelationService;
	
	@Override
	public int create(SmsFlashPromotionSession flashPromotionSession) {
		flashPromotionSession.setCreateTime(new Date());
		return flashPromotionSessionMapper.insert(flashPromotionSession);
	}
	
	@Override
	public int update(Long id, SmsFlashPromotionSession flashPromotionSession) {
		flashPromotionSession.setId(id);
		return flashPromotionSessionMapper.updateByPrimaryKey(flashPromotionSession);
	}
	
	@Override
	public int delete(Long id) {
		return flashPromotionSessionMapper.deleteByPrimaryKey(id);
	}
	
	@Override
	public int updateStatus(Long id, Integer status) {
		SmsFlashPromotionSession smsFlashPromotionSession = new SmsFlashPromotionSession();
		smsFlashPromotionSession.setId(id);
		smsFlashPromotionSession.setStatus(status);
		return flashPromotionSessionMapper.updateByPrimaryKeySelective(smsFlashPromotionSession);
	}
	
	@Override
	public SmsFlashPromotionSession getItem(Long id) {
		return flashPromotionSessionMapper.selectByPrimaryKey(id);
	}
	
	@Override
	public List<SmsFlashPromotionSession> list() {
		return flashPromotionSessionMapper.selectByExample(new SmsFlashPromotionSessionExample());
	}
	
	@Override
	public List<SmsFlashPromotionSessionDetail> selectList(Long flashPromotionId) {
		
		SmsFlashPromotionSessionExample example = new SmsFlashPromotionSessionExample();
		example.createCriteria().andStatusEqualTo(1);
		List<SmsFlashPromotionSession> promotionSessionList = flashPromotionSessionMapper.selectByExample(example);
		
		List<SmsFlashPromotionSessionDetail> flashPromotionSessionDetailList = new ArrayList<>();
		for (SmsFlashPromotionSession promotionSession : promotionSessionList) {
			
			SmsFlashPromotionSessionDetail promotionSessionDetail = new SmsFlashPromotionSessionDetail();
			BeanUtils.copyProperties(promotionSession, promotionSessionDetail);
			long count = flashPromotionProductRelationService.getCount(flashPromotionId, promotionSession.getId());
			promotionSessionDetail.setProductCount(count);
			flashPromotionSessionDetailList.add(promotionSessionDetail);
		}
		
		return flashPromotionSessionDetailList;
	}
	
}
