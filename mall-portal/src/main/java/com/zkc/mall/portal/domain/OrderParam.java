package com.zkc.mall.portal.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 生成订单时传入
 */
@Data
public class OrderParam {
	
	@ApiModelProperty("收货地址ID")
	private Long memberReceiveAddressId;
	
	@ApiModelProperty("优惠券ID")
	private Long couponId;
	
	@ApiModelProperty("使用的积分数")
	private Integer useIntegration;
	
	@ApiModelProperty("支付方式")
	private Integer payType;
	
	@ApiModelProperty("被选中的购物车商品ID")
	private List<Long> cartIds;
}
