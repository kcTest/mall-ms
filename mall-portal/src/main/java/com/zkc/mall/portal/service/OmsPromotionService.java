package com.zkc.mall.portal.service;

import com.zkc.mall.mbg.model.OmsCartItem;
import com.zkc.mall.portal.domain.CartPromotionItem;

import java.util.List;

public interface OmsPromotionService {
	
	/**
	 * 计算购物车中的促销信息
	 */
	List<CartPromotionItem> calcCartPromotion(List<OmsCartItem> cartItemList);
}
