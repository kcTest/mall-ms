package com.zkc.mall.portal.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OmsOrderReturnApplyParam {
	
	/**
	 * 订单id
	 */
	@Schema(description = "订单id")
	private Long orderId;
	/**
	 * 退货商品id
	 */
	@Schema(description = "退货商品id")
	private Long productId;
	/**
	 * 订单编号
	 */
	@Schema(description = "订单编号")
	private String orderSn;
	/**
	 * 会员用户名
	 */
	@Schema(description = "会员用户名")
	private String memberUsername;
	/**
	 * 退货人姓名
	 */
	@Schema(description = "退货人姓名")
	private String returnName;
	/**
	 * 退货人电话
	 */
	@Schema(description = "退货人电话")
	private String returnPhone;
	/**
	 * 商品图片
	 */
	@Schema(description = "商品图片")
	private String productPic;
	/**
	 * 商品名称
	 */
	@Schema(description = "商品名称")
	private String productName;
	/**
	 * 商品品牌
	 */
	@Schema(description = "商品品牌")
	private String productBrand;
	/**
	 * 商品销售属性：颜色：红色；尺码：xl;
	 */
	@Schema(description = "商品销售属性：颜色：红色；尺码：xl;")
	private String productAttr;
	/**
	 * 退货数量
	 */
	@Schema(description = "退货数量")
	private Integer productCount;
	/**
	 * 商品单价
	 */
	@Schema(description = "商品单价")
	private BigDecimal productPrice;
	/**
	 * 商品实际支付单价
	 */
	@Schema(description = "商品实际支付单价")
	private BigDecimal productRealPrice;
	/**
	 * 原因
	 */
	@Schema(description = "原因")
	private String reason;
	/**
	 * 描述
	 */
	@Schema(description = "描述")
	private String description;
	
	/**
	 * 凭证图片，以逗号隔开
	 */
	@Schema(description = "凭证图片，以逗号隔开")
	private String proofPics;
	
}
