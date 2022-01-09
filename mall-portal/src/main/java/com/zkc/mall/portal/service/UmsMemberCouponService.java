package com.zkc.mall.portal.service;

import com.zkc.mall.mbg.model.SmsCoupon;
import com.zkc.mall.mbg.model.SmsCouponHistory;
import com.zkc.mall.portal.domain.CartPromotionItem;
import com.zkc.mall.portal.domain.SmsCouponHistoryDetail;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UmsMemberCouponService {
	
	@Transactional
	void add(Long couponId);
	
	List<SmsCouponHistory> listHistory(Integer useStatus);
	
	List<SmsCoupon> list(Integer useStatus);
	
	/**
	 * 根据购物车信息获取可用优惠券
	 */
	List<SmsCouponHistoryDetail> listCart(List<CartPromotionItem> cartPromotionItemList, Integer type);
	
	List<SmsCoupon> listByProduct(Long productId);
	
}
