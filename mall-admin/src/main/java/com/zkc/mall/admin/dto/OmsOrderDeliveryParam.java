package com.zkc.mall.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OmsOrderDeliveryParam {
	
	@Schema(description ="订单ID")
	private Long orderId;
	
	@Schema(description ="物流公司")
	private String deliveryCompany;
	
	@Schema(description ="单号")
	private String deliverySn;
}
