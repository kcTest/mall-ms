package com.zkc.mall.portal.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.zkc.mall.common.exception.Asserts;
import com.zkc.mall.mbg.mapper.*;
import com.zkc.mall.mbg.model.*;
import com.zkc.mall.portal.dao.SmsCouponHistoryDao;
import com.zkc.mall.portal.domain.CartPromotionItem;
import com.zkc.mall.portal.domain.SmsCouponHistoryDetail;
import com.zkc.mall.portal.service.UmsMemberCouponService;
import com.zkc.mall.portal.service.UmsMemberService;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class UmsMemberCouponServiceImpl implements UmsMemberCouponService {
	
	@Autowired
	private SmsCouponMapper couponMapper;
	
	@Autowired
	private SmsCouponHistoryMapper couponHistoryMapper;
	
	@Autowired
	private SmsCouponProductRelationMapper couponProductRelationMapper;
	
	@Autowired
	private SmsCouponProductCategoryRelationMapper couponProductCategoryRelationMapper;
	
	@Autowired
	private SmsCouponHistoryDao couponHistoryDao;
	
	@Autowired
	private PmsProductMapper productMapper;
	
	@Autowired
	private UmsMemberService memberService;
	
	@Override
	public void add(Long couponId) {
		UmsMember currentMember = memberService.getCurrentMember();
		Long memberId = currentMember.getId();
		//检查
		SmsCoupon coupon = couponMapper.selectByPrimaryKey(couponId);
		if (coupon == null) {
			Asserts.fail("优惠券不存在");
		}
		if (coupon.getCount() <= 0) {
			Asserts.fail("优惠券已经领完");
		}
		Date now = new Date();
		if (now.before(coupon.getEnableTime())) {
			Asserts.fail("优惠券未到领取时间");
		}
		SmsCouponHistoryExample example = new SmsCouponHistoryExample();
		example.createCriteria().andCouponIdEqualTo(couponId).andMemberIdEqualTo(memberId);
		long count = couponHistoryMapper.countByExample(example);
		if (count > coupon.getPerLimit()) {
			Asserts.fail("您已经领取过该优惠券");
		}
		
		//生成领取记录
		SmsCouponHistory history = new SmsCouponHistory();
		history.setCreateTime(now);
		history.setCouponId(couponId);
		history.setMemberId(memberId);
		history.setCouponCode(generateCouponCode(memberId));
		history.setMemberNickname(currentMember.getNickname());
		//主动领取
		history.setGetType(1);
		//未使用
		history.setUseStatus(0);
		couponHistoryMapper.insert(history);
		
		//修改优惠券表的数量
		coupon.setCount(coupon.getCount() - 1);
		coupon.setReceiveCount(coupon.getReceiveCount() == null ? 1 : coupon.getReceiveCount() + 1);
		couponMapper.updateByPrimaryKeySelective(coupon);
	}
	
	@Override
	public List<SmsCouponHistory> listHistory(Integer useStatus) {
		UmsMember member = memberService.getCurrentMember();
		SmsCouponHistoryExample example = new SmsCouponHistoryExample();
		SmsCouponHistoryExample.Criteria criteria = example.createCriteria();
		criteria.andMemberIdEqualTo(member.getId());
		if (useStatus != null) {
			criteria.andUseStatusEqualTo(useStatus);
		}
		return couponHistoryMapper.selectByExample(example);
	}
	
	@Override
	public List<SmsCoupon> list(Integer useStatus) {
		UmsMember member = memberService.getCurrentMember();
		return couponHistoryDao.getCounponList(member.getId(), useStatus);
	}
	
	@Override
	public List<SmsCouponHistoryDetail> listCart(List<CartPromotionItem> cartPromotionItemList, Integer type) {
		UmsMember member = memberService.getCurrentMember();
		Date now = new Date();
		//获取该用户所有可用优惠券
		List<SmsCouponHistoryDetail> couponHistoryDetailList = couponHistoryDao.getDetailList(member.getId());
		//根据优惠券类型判断是否可用
		List<SmsCouponHistoryDetail> enableList = new ArrayList<>();
		List<SmsCouponHistoryDetail> disableList = new ArrayList<>();
		for (SmsCouponHistoryDetail historyDetail : couponHistoryDetailList) {
			SmsCoupon coupon = historyDetail.getCoupon();
			Integer useType = coupon.getUseType();
			BigDecimal minPoint = coupon.getMinPoint();
			Date endTime = coupon.getEndTime();
			switch (useType) {
				case 0:
					//全场通用
					BigDecimal totalAmount0 = calcTotalAmount(cartPromotionItemList);
					//满足使用门槛
					if (now.before(endTime) && totalAmount0.intValue() > 0 && totalAmount0.subtract(minPoint).intValue() >= 0) {
						enableList.add(historyDetail);
					} else {
						disableList.add(historyDetail);
					}
					break;
				case 1:
					//指定分类
					List<Long> productCategoryIds = new ArrayList<>();
					for (SmsCouponProductCategoryRelation categoryRelation : historyDetail.getCategoryRelationList()) {
						productCategoryIds.add(categoryRelation.getProductCategoryId());
					}
					BigDecimal totalAmount1 = calcTotalAmountByCateId(cartPromotionItemList, productCategoryIds);
					if (now.before(endTime) && totalAmount1.intValue() > 0 && totalAmount1.subtract(minPoint).intValue() >= 0) {
						enableList.add(historyDetail);
					} else {
						disableList.add(historyDetail);
					}
					break;
				case 2:
					//指定商品
					List<Long> productIds = new ArrayList<>();
					for (SmsCouponProductRelation productRelation : historyDetail.getProductRelationList()) {
						productIds.add(productRelation.getProductId());
					}
					BigDecimal totalAmount2 = calcTotalAmountByProductId(cartPromotionItemList, productIds);
					if (now.before(endTime) && totalAmount2.intValue() > 0 && totalAmount2.subtract(minPoint).intValue() >= 0) {
						enableList.add(historyDetail);
					} else {
						disableList.add(historyDetail);
					}
					break;
			}
		}
		
		if (type == 1) {
			return enableList;
		} else {
			return disableList;
		}
	}
	
	@Override
	public List<SmsCoupon> listByProduct(Long productId) {
		List<Long> allCouponIds = new ArrayList<>();
		
		//获取指定商品优惠券
		SmsCouponProductRelationExample productRelationExample = new SmsCouponProductRelationExample();
		productRelationExample.createCriteria().andProductIdEqualTo(productId);
		List<SmsCouponProductRelation> couponProductRelationList = couponProductRelationMapper.selectByExample(productRelationExample);
		if (CollUtil.isNotEmpty(couponProductRelationList)) {
			List<Long> couponIdList = couponProductRelationList.stream()
					.map(SmsCouponProductRelation::getCouponId).collect(Collectors.toList());
			allCouponIds.addAll(couponIdList);
		}
		
		//获取指定指定分类商品优惠券
		PmsProduct product = productMapper.selectByPrimaryKey(productId);
		SmsCouponProductCategoryRelationExample categoryRelationExample = new SmsCouponProductCategoryRelationExample();
		categoryRelationExample.createCriteria().andCouponIdEqualTo(product.getProductCategoryId());
		List<SmsCouponProductCategoryRelation> couponProductCategoryRelationList = couponProductCategoryRelationMapper.selectByExample(categoryRelationExample);
		if (CollUtil.isNotEmpty(couponProductCategoryRelationList)) {
			List<Long> couponIdList = couponProductCategoryRelationList.stream()
					.map(SmsCouponProductCategoryRelation::getCouponId).collect(Collectors.toList());
			allCouponIds.addAll(couponIdList);
		}
		
		if (CollUtil.isEmpty(allCouponIds)) {
			return new ArrayList<>();
		}
		
		//获取所有优惠券
		SmsCouponExample example = new SmsCouponExample();
		Date now = new Date();
		example.createCriteria().andEndTimeGreaterThan(now).andStartTimeLessThan(now).andUseTypeEqualTo(0);
		example.or(example.createCriteria().andEndTimeGreaterThan(now).andStartTimeLessThan(now).andUseTypeNotEqualTo(0)
				.andIdIn(allCouponIds));
		
		return couponMapper.selectByExample(example);
	}
	
	private BigDecimal calcTotalAmountByProductId(List<CartPromotionItem> cartPromotionItemList, List<Long> productIds) {
		BigDecimal totalAmount = new BigDecimal(0);
		for (CartPromotionItem cartPromotionItem : cartPromotionItemList) {
			if (productIds.contains(cartPromotionItem.getProductId())) {
				BigDecimal realPrice = cartPromotionItem.getPrice().subtract(cartPromotionItem.getReduceAmount());
				totalAmount = totalAmount.add(realPrice.multiply(new BigDecimal(cartPromotionItem.getQuantity())));
			}
		}
		return totalAmount;
	}
	
	private BigDecimal calcTotalAmountByCateId(List<CartPromotionItem> cartPromotionItemList, List<Long> productCategoryIds) {
		BigDecimal totalAmount = new BigDecimal(0);
		for (CartPromotionItem cartPromotionItem : cartPromotionItemList) {
			if (productCategoryIds.contains(cartPromotionItem.getProductCategoryId())) {
				BigDecimal realPrice = cartPromotionItem.getPrice().subtract(cartPromotionItem.getReduceAmount());
				totalAmount = totalAmount.add(realPrice.multiply(new BigDecimal(cartPromotionItem.getQuantity())));
			}
		}
		return totalAmount;
	}
	
	private BigDecimal calcTotalAmount(List<CartPromotionItem> cartPromotionItemList) {
		BigDecimal totalAmount = new BigDecimal(0);
		for (CartPromotionItem cartPromotionItem : cartPromotionItemList) {
			BigDecimal realPrice = cartPromotionItem.getPrice().subtract(cartPromotionItem.getReduceAmount());
			totalAmount = totalAmount.add(realPrice.multiply(new BigDecimal(cartPromotionItem.getQuantity())));
		}
		return totalAmount;
	}
	
	/* 16位优惠码生成：时间戳后8位+4位随机数+用户id后4位 */
	private String generateCouponCode(Long memberId) {
		
		StringBuilder builder = new StringBuilder();
		Long timeMillis = System.currentTimeMillis();
		final String timeMillisStr = timeMillis.toString();
		builder.append(timeMillisStr.substring(timeMillisStr.length() - 8));
		
		for (int i = 0; i < 4; i++) {
			builder.append(new Random().nextInt(10));
		}
		
		String memberIdStr = memberId.toString();
		if (memberIdStr.length() <= 4) {
			builder.append(String.format("%04d", memberId));
		} else {
			builder.append(memberIdStr.substring(memberIdStr.length() - 4));
		}
		
		return builder.toString();
	}
}
