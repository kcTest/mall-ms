package com.zkc.mall.portal.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.github.pagehelper.PageHelper;
import com.zkc.mall.mbg.mapper.*;
import com.zkc.mall.mbg.model.*;
import com.zkc.mall.portal.dao.HomeDao;
import com.zkc.mall.portal.domain.FlashPromotionProduct;
import com.zkc.mall.portal.domain.HomeContentResult;
import com.zkc.mall.portal.domain.HomeFlashPromotion;
import com.zkc.mall.portal.service.HomeService;
import com.zkc.mall.portal.util.MyDateUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class HomeServiceImpl implements HomeService {
	
	@Resource
	private SmsHomeAdvertiseMapper homeAdvertiseMapper;
	@Resource
	private SmsFlashPromotionMapper flashPromotionMapper;
	@Resource
	private PmsProductMapper productMapper;
	@Resource
	private PmsProductCategoryMapper productCategoryMapper;
	@Resource
	private CmsSubjectMapper subjectMapper;
	@Resource
	private SmsFlashPromotionSessionMapper flashPromotionSessionMapper;
	
	@Resource
	private HomeDao homeDao;
	
	@Override
	public HomeContentResult content() {
		HomeContentResult result = new HomeContentResult();
		//获取首页广告
		result.setAdvertiseList(getHomeAdvertiseList());
		//获取推荐品牌
		result.setBrandList(homeDao.getRecommendBrandList(0, 6));
		//获取秒杀信息
		result.setHomeFlashPromotion(getHomeFlashPromotion());
		//获取新品推荐
		result.setNewProductList(homeDao.getNewProductList(0, 4));
		//获取人气推荐
		result.setHotProductList(homeDao.getHotProductList(0, 4));
		//获取推荐专题
		result.setSubjectList(homeDao.getRecommendSubjectList(0, 4));
		return result;
	}
	
	@Override
	public List<PmsProduct> recommendProductList(Integer pageSize, Integer pageNum) {
		PageHelper.startPage(pageNum, pageSize);
		PmsProductExample example = new PmsProductExample();
		example.createCriteria().andDeleteStatusEqualTo(0).andPublishStatusEqualTo(1);
		return productMapper.selectByExample(example);
	}
	
	@Override
	public List<PmsProductCategory> getProductCategoryList(Long parentId) {
		PmsProductCategoryExample example = new PmsProductCategoryExample();
		example.createCriteria().andShowStatusEqualTo(1).andParentIdEqualTo(parentId);
		example.setOrderByClause("sort desc");
		return productCategoryMapper.selectByExample(example);
	}
	
	@Override
	public List<CmsSubject> getSubjectList(Long cateId, Integer pageSize, Integer pageNum) {
		PageHelper.startPage(pageNum, pageSize);
		CmsSubjectExample example = new CmsSubjectExample();
		CmsSubjectExample.Criteria criteria = example.createCriteria().andShowStatusEqualTo(1);
		if (cateId != null) {
			criteria.andCategoryIdEqualTo(cateId);
		}
		return subjectMapper.selectByExample(example);
	}
	
	@Override
	public List<PmsProduct> hotProductList(Integer pageSize, Integer pageNum) {
		int offset = pageSize * (pageNum - 1);
		return homeDao.getHotProductList(offset, pageSize);
	}
	
	@Override
	public List<PmsProduct> newProductList(Integer pageSize, Integer pageNum) {
		int offset = pageSize * (pageNum - 1);
		return homeDao.getNewProductList(offset, pageSize);
	}
	
	private HomeFlashPromotion getHomeFlashPromotion() {
		HomeFlashPromotion homeFlashPromotion = new HomeFlashPromotion();
		//获取当前秒杀活动
		Date now = new Date();
		SmsFlashPromotion flashPromotion = getFlashPromotion(now);
		if (flashPromotion != null) {
			//获取当前秒杀场次
			SmsFlashPromotionSession flashPromotionSession = getFlashPromotionSession(now);
			if (flashPromotionSession != null) {
				homeFlashPromotion.setStartTime(flashPromotionSession.getStartTime());
				homeFlashPromotion.setEndTime(flashPromotionSession.getEndTime());
				//获取下一个秒杀场次
				SmsFlashPromotionSession nextFlashPromotionSession = getNextFlashPromotionSession(now);
				if (nextFlashPromotionSession != null) {
					homeFlashPromotion.setNextStartTime(nextFlashPromotionSession.getStartTime());
					homeFlashPromotion.setEndTime(nextFlashPromotionSession.getEndTime());
				}
				//获取秒杀商品
				List<FlashPromotionProduct> flashPromotionProductList = homeDao.getFlashProductList(flashPromotion.getId(), flashPromotionSession.getId());
				homeFlashPromotion.setProductList(flashPromotionProductList);
			}
		}
		
		return homeFlashPromotion;
	}
	
	/**
	 * 根据下一个场次信息
	 */
	private SmsFlashPromotionSession getNextFlashPromotionSession(Date now) {
		SmsFlashPromotionSessionExample sessionExample = new SmsFlashPromotionSessionExample();
		sessionExample.createCriteria().andStartTimeLessThanOrEqualTo(now);
		sessionExample.setOrderByClause("start_time asc");
		List<SmsFlashPromotionSession> promotionSessionList = flashPromotionSessionMapper.selectByExample(sessionExample);
		if (CollUtil.isNotEmpty(promotionSessionList)) {
			return promotionSessionList.get(0);
		}
		return null;
	}
	
	/**
	 * 根据时间获取当前秒杀场次
	 */
	private SmsFlashPromotionSession getFlashPromotionSession(Date now) {
		Date current = MyDateUtil.getDate(now);
		SmsFlashPromotionSessionExample sessionExample = new SmsFlashPromotionSessionExample();
		sessionExample.createCriteria().andStartTimeLessThanOrEqualTo(current)
				.andEndTimeGreaterThanOrEqualTo(current);
		List<SmsFlashPromotionSession> promotionSessionList = flashPromotionSessionMapper.selectByExample(sessionExample);
		if (CollUtil.isNotEmpty(promotionSessionList)) {
			return promotionSessionList.get(0);
		}
		return null;
	}
	
	/**
	 * 根据时间获取秒杀活动
	 */
	private SmsFlashPromotion getFlashPromotion(Date now) {
		Date currentDate = MyDateUtil.getDate(now);
		SmsFlashPromotionExample example = new SmsFlashPromotionExample();
		example.createCriteria().andStatusEqualTo(1)
				.andStartDateLessThanOrEqualTo(currentDate)
				.andEndDateGreaterThanOrEqualTo(currentDate);
		List<SmsFlashPromotion> promotionList = flashPromotionMapper.selectByExample(example);
		if (CollUtil.isNotEmpty(promotionList)) {
			return promotionList.get(0);
		}
		return null;
	}
	
	private List<SmsHomeAdvertise> getHomeAdvertiseList() {
		
		SmsHomeAdvertiseExample example = new SmsHomeAdvertiseExample();
		example.createCriteria().andTypeEqualTo(1).andStatusEqualTo(1);
		example.setOrderByClause("sort desc");
		return homeAdvertiseMapper.selectByExample(example);
	}
}
