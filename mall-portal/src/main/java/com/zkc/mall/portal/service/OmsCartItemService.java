package com.zkc.mall.portal.service;

import com.zkc.mall.mbg.model.OmsCartItem;
import com.zkc.mall.portal.domain.CartProduct;
import com.zkc.mall.portal.domain.CartPromotionItem;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OmsCartItemService {
	
	/**
	 * 获取包含促销活动信息的购物车列表
	 */
	List<CartPromotionItem> listPromotion(List<Long> cartId);
	
	int delete(Long memberId, List<Long> cartIdList);
	
	@Transactional
	int add(OmsCartItem cartItem);
	
	List<OmsCartItem> list(Long id);
	
	int updateQuantity(Long id, Long memberId, Integer quantity);
	
	CartProduct getCartProduct(Long productId);
	
	@Transactional
	int updateAttr(OmsCartItem cartItem);
	
	int clear(Long memberId);
}
