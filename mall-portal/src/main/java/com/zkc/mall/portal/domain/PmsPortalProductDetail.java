package com.zkc.mall.portal.domain;

import com.zkc.mall.mbg.model.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PmsPortalProductDetail {
	
	@ApiModelProperty("商品信息")
	private PmsProduct product;
	
	@ApiModelProperty("商品品牌")
	private PmsBrand brand;
	
	@ApiModelProperty("商品属性与参数")
	private List<PmsProductAttribute> productAttributeList;
	
	@ApiModelProperty("手动录入的商品属性与参数的值")
	private List<PmsProductAttributeValue> productAttributeValueList;
	
	@ApiModelProperty("商品库存信息")
	private List<PmsSkuStock> skuStockList;
	
	@ApiModelProperty("商品阶梯价格设置")
	private List<PmsProductLadder> productLadderList;
	
	@ApiModelProperty("商品满减价格设置")
	private List<PmsProductFullReduction> productFullReductionList;
	
	@ApiModelProperty("商品可用优惠券")
	private List<SmsCoupon> couponList;
}