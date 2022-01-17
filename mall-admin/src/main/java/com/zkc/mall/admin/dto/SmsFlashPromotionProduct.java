package com.zkc.mall.admin.dto;

import com.zkc.mall.mbg.model.PmsProduct;
import com.zkc.mall.mbg.model.SmsFlashPromotionProductRelation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;


public class SmsFlashPromotionProduct extends SmsFlashPromotionProductRelation {
	
	@Getter
	@Setter
	@Schema
	private PmsProduct product;
}
