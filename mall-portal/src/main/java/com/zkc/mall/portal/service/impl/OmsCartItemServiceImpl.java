package com.zkc.mall.portal.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.zkc.mall.mbg.mapper.OmsCartItemMapper;
import com.zkc.mall.mbg.model.OmsCartItem;
import com.zkc.mall.mbg.model.OmsCartItemExample;
import com.zkc.mall.mbg.model.UmsMember;
import com.zkc.mall.portal.dao.PortalProductDao;
import com.zkc.mall.portal.domain.CartProduct;
import com.zkc.mall.portal.domain.CartPromotionItem;
import com.zkc.mall.portal.service.OmsCartItemService;
import com.zkc.mall.portal.service.OmsPromotionService;
import com.zkc.mall.portal.service.UmsMemberService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OmsCartItemServiceImpl implements OmsCartItemService {
	
	@Resource
	private OmsCartItemMapper cartItemMapper;
	@Resource
	private UmsMemberService memberService;
	@Resource
	private OmsPromotionService promotionService;
	@Resource
	private PortalProductDao portalProductDao;
	
	@Override
	public List<CartPromotionItem> listPromotion(List<Long> cartIds) {
		UmsMember member = memberService.getCurrentMember();
		List<OmsCartItem> cartItemList = list(member.getId());
		if (!CollectionUtil.isEmpty(cartIds)) {
			cartItemList = cartItemList.stream().filter(c -> cartIds.contains(c.getId())).collect(Collectors.toList());
		}
		
		List<CartPromotionItem> cartPromotionItemList = new ArrayList<>();
		if (!CollectionUtil.isEmpty(cartItemList)) {
			cartPromotionItemList = promotionService.calcCartPromotion(cartItemList);
		}
		
		return cartPromotionItemList;
	}
	
	@Override
	public int delete(Long memberId, List<Long> cartIdList) {
		OmsCartItemExample example = new OmsCartItemExample();
		example.createCriteria().andIdIn(cartIdList).andMemberIdEqualTo(memberId);
		OmsCartItem cartItem = new OmsCartItem();
		cartItem.setDeleteStatus(1);
		return cartItemMapper.updateByExampleSelective(cartItem, example);
	}
	
	@Override
	public int add(OmsCartItem cartItem) {
		int count;
		UmsMember currentMember = memberService.getCurrentMember();
		cartItem.setMemberId(currentMember.getId());
		cartItem.setMemberNickname(currentMember.getNickname());
		cartItem.setDeleteStatus(0);
		OmsCartItem existCartItem = getCartItem(cartItem);
		if (existCartItem != null) {
			cartItem.setModifyDate(new Date());
			existCartItem.setQuantity(existCartItem.getQuantity() + cartItem.getQuantity());
			count = cartItemMapper.updateByPrimaryKey(existCartItem);
		} else {
			cartItem.setCreateDate(new Date());
			count = cartItemMapper.insert(cartItem);
		}
		
		return count;
	}
	
	/**
	 * 根据会员ID 商品id和规格获取购物车中的商品
	 */
	private OmsCartItem getCartItem(OmsCartItem cartItem) {
		OmsCartItemExample example = new OmsCartItemExample();
		OmsCartItemExample.Criteria criteria = example.createCriteria()
				.andMemberIdEqualTo(cartItem.getMemberId())
				.andProductIdEqualTo(cartItem.getProductId())
				.andDeleteStatusEqualTo(0);
		if ((StrUtil.isNotEmpty(cartItem.getProductSkuId().toString()))) {
			criteria.andProductSkuIdEqualTo(cartItem.getProductSkuId());
		}
		List<OmsCartItem> cartItemList = cartItemMapper.selectByExample(example);
		if (CollUtil.isNotEmpty(cartItemList)) {
			return cartItemList.get(0);
		}
		return null;
	}
	
	@Override
	public List<OmsCartItem> list(Long memberId) {
		OmsCartItemExample example = new OmsCartItemExample();
		example.createCriteria().andMemberIdEqualTo(memberId).andDeleteStatusEqualTo(0);
		return cartItemMapper.selectByExample(example);
	}
	
	@Override
	public int updateQuantity(Long id, Long memberId, Integer quantity) {
		OmsCartItem cartItem = new OmsCartItem();
		cartItem.setQuantity(quantity);
		OmsCartItemExample example = new OmsCartItemExample();
		example.createCriteria().andMemberIdEqualTo(memberId).andIdEqualTo(id).andDeleteStatusEqualTo(0);
		return cartItemMapper.updateByExampleSelective(cartItem, example);
	}
	
	@Override
	public CartProduct getCartProduct(Long productId) {
		return portalProductDao.getCartProduct(productId);
	}
	
	@Override
	public int updateAttr(OmsCartItem cartItem) {
		//删除原购物车信息
		OmsCartItem item = new OmsCartItem();
		item.setId(cartItem.getId());
		item.setModifyDate(new Date());
		item.setDeleteStatus(1);
		cartItemMapper.updateByPrimaryKeySelective(item);
		
		cartItem.setId(null);
		add(item);
		return 1;
	}
	
	@Override
	public int clear(Long memberId) {
		OmsCartItem item = new OmsCartItem();
		item.setDeleteStatus(1);
		OmsCartItemExample example = new OmsCartItemExample();
		example.createCriteria().andMemberIdEqualTo(memberId);
		return cartItemMapper.updateByExampleSelective(item, example);
	}
}
