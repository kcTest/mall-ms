package com.zkc.mall.admin.service.impl;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.zkc.mall.admin.service.SmsFlashPromotionService;
import com.zkc.mall.mbg.mapper.SmsFlashPromotionMapper;
import com.zkc.mall.mbg.model.SmsFlashPromotion;
import com.zkc.mall.mbg.model.SmsFlashPromotionExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class SmsFlashPromotionServiceImpl implements SmsFlashPromotionService {
	
	@Resource
	private SmsFlashPromotionMapper flashPromotionMapper;
	
	@Override
	public int create(SmsFlashPromotion flashPromotion) {
		flashPromotion.setCreateTime(new Date());
		return flashPromotionMapper.insert(flashPromotion);
	}
	
	@Override
	public int update(Long id, SmsFlashPromotion flashPromotion) {
		flashPromotion.setId(id);
		return flashPromotionMapper.updateByPrimaryKey(flashPromotion);
	}
	
	@Override
	public int delete(Long id) {
		return flashPromotionMapper.deleteByPrimaryKey(id);
	}
	
	@Override
	public int updateStatus(Long id, Integer status) {
		SmsFlashPromotion smsFlashPromotion = new SmsFlashPromotion();
		smsFlashPromotion.setId(id);
		smsFlashPromotion.setStatus(status);
		return flashPromotionMapper.updateByPrimaryKeySelective(smsFlashPromotion);
	}
	
	@Override
	public SmsFlashPromotion getItem(Long id) {
		return flashPromotionMapper.selectByPrimaryKey(id);
	}
	
	@Override
	public List<SmsFlashPromotion> list(String title, Integer pageSize, Integer pageNum) {
		PageHelper.startPage(pageNum, pageSize);
		SmsFlashPromotionExample example = new SmsFlashPromotionExample();
		if (!StrUtil.isEmpty(title)) {
			example.createCriteria().andTitleLike("%" + title + "%");
		}
		return flashPromotionMapper.selectByExample(example);
	}
}
