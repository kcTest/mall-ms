package com.zkc.mall.portal.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OmsOrderReturnApplyParam {
	
	/**
	 * 订单id
	 */
	@ApiModelProperty(value = "订单id")
	private Long orderId;
	/**
	 * 退货商品id
	 */
	@ApiModelProperty(value = "退货商品id")
	private Long productId;
	/**
	 * 订单编号
	 */
	@ApiModelProperty(value = "订单编号")
	private String orderSn;
	/**
	 * 会员用户名
	 */
	@ApiModelProperty(value = "会员用户名")
	private String memberUsername;
	/**
	 * 退货人姓名
	 */
	@ApiModelProperty(value = "退货人姓名")
	private String returnName;
	/**
	 * 退货人电话
	 */
	@ApiModelProperty(value = "退货人电话")
	private String returnPhone;
	/**
	 * 商品图片
	 */
	@ApiModelProperty(value = "商品图片")
	private String productPic;
	/**
	 * 商品名称
	 */
	@ApiModelProperty(value = "商品名称")
	private String productName;
	/**
	 * 商品品牌
	 */
	@ApiModelProperty(value = "商品品牌")
	private String productBrand;
	/**
	 * 商品销售属性：颜色：红色；尺码：xl;
	 */
	@ApiModelProperty(value = "商品销售属性：颜色：红色；尺码：xl;")
	private String productAttr;
	/**
	 * 退货数量
	 */
	@ApiModelProperty(value = "退货数量")
	private Integer productCount;
	/**
	 * 商品单价
	 */
	@ApiModelProperty(value = "商品单价")
	private BigDecimal productPrice;
	/**
	 * 商品实际支付单价
	 */
	@ApiModelProperty(value = "商品实际支付单价")
	private BigDecimal productRealPrice;
	/**
	 * 原因
	 */
	@ApiModelProperty(value = "原因")
	private String reason;
	/**
	 * 描述
	 */
	@ApiModelProperty(value = "描述")
	private String description;
	
	/**
	 * 凭证图片，以逗号隔开
	 */
	@ApiModelProperty(value = "凭证图片，以逗号隔开")
	private String proofPics;
	
}
