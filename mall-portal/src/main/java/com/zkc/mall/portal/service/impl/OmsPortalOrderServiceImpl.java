package com.zkc.mall.portal.service.impl;

import com.zkc.mall.common.exception.Asserts;
import com.zkc.mall.mbg.mapper.UmsIntegrationConsumeSettingMapper;
import com.zkc.mall.mbg.model.*;
import com.zkc.mall.portal.domain.CartPromotionItem;
import com.zkc.mall.portal.domain.ConfirmOrderResult;
import com.zkc.mall.portal.domain.OrderParam;
import com.zkc.mall.portal.domain.SmsCouponHistoryDetail;
import com.zkc.mall.portal.service.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class OmsPortalOrderServiceImpl implements OmsPortalOrderService {
	
	@Resource
	private UmsMemberService memberService;
	@Resource
	private UmsMemberCouponService memberCouponService;
	@Resource
	private OmsCartItemService cartItemService;
	@Resource
	private UmsMemberReceiveAddressService addressService;
	@Resource
	private UmsMemberCouponService couponService;
	@Resource
	private UmsIntegrationConsumeSettingMapper integrationConsumeSettingMapper;
	
	@Override
	public ConfirmOrderResult generateConfirmOrder(List<Long> cartIds) {
		ConfirmOrderResult result = new ConfirmOrderResult();
		
		//获取购物车信息
		UmsMember member = memberService.getCurrentMember();
		List<CartPromotionItem> cartPromotionItemList = cartItemService.listPromotion(cartIds);
		result.setCartPromotionItemList(cartPromotionItemList);
		
		//获取用户收货地址列表
		List<UmsMemberReceiveAddress> addressList = addressService.list();
		result.setMemberReceiveAddressList(addressList);
		
		//获取用户可用优惠券列表
		List<SmsCouponHistoryDetail> couponHistoryDetails = couponService.listCart(cartPromotionItemList, 1);
		result.setCouponHistoryDetailList(couponHistoryDetails);
		
		//获取用户积分
		result.setMemberIntegration(member.getIntegration());
		//获取积分使用规则
		UmsIntegrationConsumeSetting consumeSetting = integrationConsumeSettingMapper.selectByPrimaryKey(1L);
		result.setIntegrationConsumeSetting(consumeSetting);
		
		//计算总金额、活动优惠、应付金额
		ConfirmOrderResult.CalcAmount calcAmount = calcCartAmount(cartPromotionItemList);
		result.setCalAmount(calcAmount);
		
		return result;
	}
	
	@Override
	public Map<String, Object> generateOrder(OrderParam orderParam) {
		List<OmsOrderItem> orderItemList = new ArrayList<>();
		List<CartPromotionItem> cartPromotionItemList = cartItemService.listPromotion(orderParam.getCartIds());
		for (CartPromotionItem cartPromotionItem : cartPromotionItemList) {
			//生成下单商品信息
			OmsOrderItem orderItem = new OmsOrderItem();
			orderItem.setProductId(cartPromotionItem.getProductId());
			orderItem.setProductName(cartPromotionItem.getProductName());
			orderItem.setProductPic(cartPromotionItem.getProductPic());
			orderItem.setProductAttr(cartPromotionItem.getProductAttr());
			orderItem.setProductBrand(cartPromotionItem.getProductBrand());
			orderItem.setProductSn(cartPromotionItem.getProductSn());
			orderItem.setProductPrice(cartPromotionItem.getPrice());
			orderItem.setProductQuantity(cartPromotionItem.getQuantity());
			orderItem.setProductSkuId(cartPromotionItem.getProductSkuId());
			orderItem.setProductSkuCode(cartPromotionItem.getProductSkuCode());
			orderItem.setProductCategoryId(cartPromotionItem.getProductCategoryId());
			orderItem.setPromotionAmount(cartPromotionItem.getReduceAmount());
			orderItem.setPromotionName(cartPromotionItem.getPromotionMessage());
			orderItem.setGiftIntegration(cartPromotionItem.getIntegration());
			orderItem.setGiftGrowth(cartPromotionItem.getGrowth());
			orderItemList.add(orderItem);
		}
		
		//检查库存
		if (!hasStock(cartPromotionItemList)) {
			Asserts.fail("库存不足，无法下单");
		}
		
		//判断是否使用优惠券
		if (orderParam.getCouponId() == null) {
			for (OmsOrderItem orderItem : orderItemList) {
				orderItem.setCouponAmount(new BigDecimal(0));
			}
		} else {
			SmsCouponHistoryDetail couponHistoryDetail = getUserCoupon(cartPromotionItemList, orderParam.getCouponId());
			if (couponHistoryDetail == null) {
				Asserts.fail("该优惠券不可用");
			}
			//对下单的商品进行优惠处理
			handleCouponAmount(orderItemList, couponHistoryDetail);
		}
		
		return null;
	}
	
	private void handleCouponAmount(List<OmsOrderItem> orderItemList, SmsCouponHistoryDetail couponHistoryDetail) {
		SmsCoupon coupon = couponHistoryDetail.getCoupon();
		Integer useType = coupon.getUseType();
		if (useType.equals(0)) {
			//全场通用
			calcPerCouponAmount(orderItemList, coupon);
		} else if (useType.equals(1)) {
			//指定分类
			List<OmsOrderItem> couponOrderItemList = getCouponOrderItemByRelation(couponHistoryDetail, orderItemList, 0);
			calcPerCouponAmount(couponOrderItemList, coupon);
			
		} else if (useType.equals(2)) {
			//指定商品
			List<OmsOrderItem> couponOrderItemList = getCouponOrderItemByRelation(couponHistoryDetail, orderItemList, 1);
			calcPerCouponAmount(couponOrderItemList, coupon);
		}
	}
	
	private List<OmsOrderItem> getCouponOrderItemByRelation(SmsCouponHistoryDetail couponHistoryDetail, List<OmsOrderItem> orderItemList, int i) {
		return null;
	}
	
	private void calcPerCouponAmount(List<OmsOrderItem> orderItemList, SmsCoupon coupon) {
		
	}
	
	/**
	 * 获取该用户可使用的优惠券
	 */
	private SmsCouponHistoryDetail getUserCoupon(List<CartPromotionItem> cartPromotionItemList, Long couponId) {
		List<SmsCouponHistoryDetail> couponHistoryDetailList = memberCouponService.listCart(cartPromotionItemList, 1);
		for (SmsCouponHistoryDetail couponHistoryDetail : couponHistoryDetailList) {
			if (couponHistoryDetail.getCouponId().equals(couponId)) {
				return couponHistoryDetail;
			}
		}
		return null;
	}
	
	private boolean hasStock(List<CartPromotionItem> cartPromotionItemList) {
		for (CartPromotionItem cartPromotionItem : cartPromotionItemList) {
			if (cartPromotionItem.getRealStock() == null || cartPromotionItem.getRealStock() <= 0) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 计算购物车中商品价格
	 */
	private ConfirmOrderResult.CalcAmount calcCartAmount(List<CartPromotionItem> cartPromotionItemList) {
		ConfirmOrderResult.CalcAmount calcAmount = new ConfirmOrderResult.CalcAmount();
		calcAmount.setFreightAmount(new BigDecimal(0));
		BigDecimal totalAmount = new BigDecimal(0);
		BigDecimal promotionAmount = new BigDecimal(0);
		for (CartPromotionItem promotionItem : cartPromotionItemList) {
			totalAmount = totalAmount.add(promotionItem.getPrice().multiply(new BigDecimal(promotionItem.getQuantity())));
			promotionAmount = promotionAmount.add(promotionItem.getReduceAmount().multiply(new BigDecimal(promotionItem.getQuantity())));
		}
		calcAmount.setTotalAmount(totalAmount);
		calcAmount.setPromotionAmount(promotionAmount);
		calcAmount.setPayAmount(totalAmount.subtract(promotionAmount));
		
		return calcAmount;
	}
}
