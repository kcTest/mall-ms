package com.zkc.mall.admin.dto;

import com.zkc.mall.mbg.model.PmsProduct;
import com.zkc.mall.mbg.model.SmsFlashPromotionProductRelation;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


public class SmsFlashPromotionProduct extends SmsFlashPromotionProductRelation {
	
	@Getter
	@Setter
	@ApiModelProperty
	private PmsProduct product;
}
