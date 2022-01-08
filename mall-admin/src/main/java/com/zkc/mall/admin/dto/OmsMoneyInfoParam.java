package com.zkc.mall.admin.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OmsMoneyInfoParam {
	
	@ApiModelProperty("订单ID")
	private Long orderId;
	
	@ApiModelProperty("运费金额")
	private BigDecimal freightAmount;
	
	@ApiModelProperty("折扣金额")
	private BigDecimal discountAmount;
	
	@ApiModelProperty("订单状态 0->待付款 1->待发货 2->已发货 3->已完成 4->已关闭 5->无效订单")
	private Integer status;
}
