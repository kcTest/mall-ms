package com.zkc.mall.portal.domain;

import com.zkc.mall.mbg.model.UmsIntegrationConsumeSetting;
import com.zkc.mall.mbg.model.UmsMemberReceiveAddress;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * 确认订单信息
 */
@Getter
@Setter
public class ConfirmOrderResult {
	
	/**
	 * 包含优惠信息的购物车信息
	 */
	private List<CartPromotionItem> cartPromotionItemList;
	/**
	 * 用户收货地址列表
	 */
	private List<UmsMemberReceiveAddress> memberReceiveAddressList;
	/**
	 * 用户可用优惠券列表
	 */
	private List<SmsCouponHistoryDetail> couponHistoryDetailList;
	/**
	 * 积分使用规则
	 */
	private UmsIntegrationConsumeSetting integrationConsumeSetting;
	/**
	 * 会员持有积分
	 */
	private Integer memberIntegration;
	/**
	 * 金额信息
	 */
	private CalcAmount calAmount;
	
	@Getter
	@Setter
	public static class CalcAmount {
		
		/**
		 * 订单商品总金额
		 */
		private BigDecimal totalAmount;
		/**
		 * 运费
		 */
		private BigDecimal freightAmount;
		/**
		 * 活动优惠
		 */
		private BigDecimal promotionAmount;
		/**
		 * 应付金额
		 */
		private BigDecimal payAmount;
	}
}
