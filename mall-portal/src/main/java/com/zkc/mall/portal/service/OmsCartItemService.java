package com.zkc.mall.portal.service;

import com.zkc.mall.portal.domain.CartPromotionItem;

import java.util.List;

public interface OmsCartItemService {
	
	/**
	 * 获取包含促销活动信息的购物车列表
	 */
	List<CartPromotionItem> listPromotion(List<Long> cartId);
	
	int delete(Long memberId, List<Long> cartIdList);
}
