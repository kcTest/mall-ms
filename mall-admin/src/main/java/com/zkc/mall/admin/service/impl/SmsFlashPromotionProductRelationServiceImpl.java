package com.zkc.mall.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.zkc.mall.admin.dao.SmsFlashPromotionProductRelationDao;
import com.zkc.mall.admin.dto.SmsFlashPromotionProduct;
import com.zkc.mall.admin.service.SmsFlashPromotionProductRelationService;
import com.zkc.mall.mbg.mapper.SmsFlashPromotionProductRelationMapper;
import com.zkc.mall.mbg.model.SmsFlashPromotionProductRelation;
import com.zkc.mall.mbg.model.SmsFlashPromotionProductRelationExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SmsFlashPromotionProductRelationServiceImpl implements SmsFlashPromotionProductRelationService {
	
	@Resource
	private SmsFlashPromotionProductRelationMapper relationMapper;
	@Resource
	private SmsFlashPromotionProductRelationDao relationDao;
	
	@Override
	public int create(List<SmsFlashPromotionProductRelation> flashPromotionProductRelationList) {
		for (SmsFlashPromotionProductRelation relation : flashPromotionProductRelationList) {
			relationMapper.insert(relation);
		}
		
		return flashPromotionProductRelationList.size();
	}
	
	@Override
	public int update(Long id, SmsFlashPromotionProductRelation flashPromotionProductRelation) {
		flashPromotionProductRelation.setId(id);
		return relationMapper.updateByPrimaryKey(flashPromotionProductRelation);
	}
	
	@Override
	public int delete(Long id) {
		return relationMapper.deleteByPrimaryKey(id);
	}
	
	@Override
	public SmsFlashPromotionProductRelation getItem(Long id) {
		return relationMapper.selectByPrimaryKey(id);
	}
	
	@Override
	public List<SmsFlashPromotionProduct> list(Long flashPromotionId, Long flashPromotionSessionId, Integer pageSize, Integer pageNum) {
		PageHelper.startPage(pageNum, pageSize);
		return relationDao.getList(flashPromotionId, flashPromotionSessionId);
	}
	
	@Override
	public long getCount(Long flashPromotionId, Long flashPromotionSessionId) {
		
		SmsFlashPromotionProductRelationExample example = new SmsFlashPromotionProductRelationExample();
		example.createCriteria()
				.andFlashPromotionIdEqualTo(flashPromotionId)
				.andFlashPromotionSessionIdEqualTo(flashPromotionSessionId);
		return relationMapper.countByExample(example);
	}
}
