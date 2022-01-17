package com.zkc.mall.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OmsMoneyInfoParam {
	
	@Schema(description ="订单ID")
	private Long orderId;
	
	@Schema(description ="运费金额")
	private BigDecimal freightAmount;
	
	@Schema(description ="折扣金额")
	private BigDecimal discountAmount;
	
	@Schema(description ="订单状态 0->待付款 1->待发货 2->已发货 3->已完成 4->已关闭 5->无效订单")
	private Integer status;
}
