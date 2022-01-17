package com.zkc.mall.portal.domain;

import com.zkc.mall.mbg.model.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PmsPortalProductDetail {
	
	@Schema(description ="商品信息")
	private PmsProduct product;
	
	@Schema(description ="商品品牌")
	private PmsBrand brand;
	
	@Schema(description ="商品属性与参数")
	private List<PmsProductAttribute> productAttributeList;
	
	@Schema(description ="手动录入的商品属性与参数的值")
	private List<PmsProductAttributeValue> productAttributeValueList;
	
	@Schema(description ="商品库存信息")
	private List<PmsSkuStock> skuStockList;
	
	@Schema(description ="商品阶梯价格设置")
	private List<PmsProductLadder> productLadderList;
	
	@Schema(description ="商品满减价格设置")
	private List<PmsProductFullReduction> productFullReductionList;
	
	@Schema(description ="商品可用优惠券")
	private List<SmsCoupon> couponList;
}
