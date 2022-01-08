package com.zkc.mall.admin.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OmsOrderDeliveryParam {
	
	@ApiModelProperty("订单ID")
	private Long orderId;
	
	@ApiModelProperty("物流公司")
	private String deliveryCompany;
	
	@ApiModelProperty("单号")
	private String deliverySn;
}
