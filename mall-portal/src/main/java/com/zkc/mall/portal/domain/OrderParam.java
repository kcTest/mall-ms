package com.zkc.mall.portal.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 生成订单时传入
 */
@Data
public class OrderParam {
	
	@Schema(description ="收货地址ID")
	private Long memberReceiveAddressId;
	
	@Schema(description ="优惠券ID")
	private Long couponId;
	
	@Schema(description ="使用的积分数")
	private Integer useIntegration;
	
	@Schema(description ="支付方式")
	private Integer payType;
	
	@Schema(description ="被选中的购物车商品ID")
	private List<Long> cartIds;
}
