package com.zkc.mall.admin.dto;

import com.zkc.mall.mbg.model.SmsCoupon;
import com.zkc.mall.mbg.model.SmsCouponProductCategoryRelation;
import com.zkc.mall.mbg.model.SmsCouponProductRelation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class SmsCouponParam extends SmsCoupon {
	
	@Getter
	@Setter
	@Schema(description = "优惠券绑定的商品")
	private List<SmsCouponProductRelation> productRelationList;
	
	@Getter
	@Setter
	@Schema(description ="优惠券绑定的商品分类")
	private List<SmsCouponProductCategoryRelation> productCategoryRelationList;
	
}
