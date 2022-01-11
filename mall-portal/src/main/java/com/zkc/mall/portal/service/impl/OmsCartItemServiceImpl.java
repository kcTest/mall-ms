package com.zkc.mall.portal.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.zkc.mall.mbg.mapper.OmsCartItemMapper;
import com.zkc.mall.mbg.model.OmsCartItem;
import com.zkc.mall.mbg.model.OmsCartItemExample;
import com.zkc.mall.mbg.model.UmsMember;
import com.zkc.mall.portal.domain.CartPromotionItem;
import com.zkc.mall.portal.service.OmsCartItemService;
import com.zkc.mall.portal.service.OmsPromotionService;
import com.zkc.mall.portal.service.UmsMemberService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
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
	
	private List<OmsCartItem> list(Long memberId) {
		OmsCartItemExample example = new OmsCartItemExample();
		example.createCriteria().andMemberIdEqualTo(memberId).andDeleteStatusEqualTo(0);
		return cartItemMapper.selectByExample(example);
	}
}
