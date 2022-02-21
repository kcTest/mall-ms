package com.zkc.mall.admin.dto;

import com.zkc.mall.mbg.model.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class PmsProductParam extends PmsProduct {
	
	@Schema(description ="商品阶梯价格设置")
	private List<PmsProductLadder> productLadderList;
	@Schema(description ="商品满减价格设置")
	private List<PmsProductFullReduction> productFullReductionList;
	@Schema(description ="商品会员价格设置")
	private List<PmsMemberPrice> memberPriceList;
	@Schema(description ="商品库存")
	private List<PmsSkuStock> skuStockList;
	@Schema(description ="商品参数及自定义规格属性")
	private List<PmsProductAttributeValue> productAttributeValueList;
	@Schema(description ="专题和商品的关系")
	private List<CmsSubjectProductRelation> subjectProductRelationList;
	@Schema(description ="优选专区和商品的关系")
	private List<CmsPreferenceAreaProductRelation> preferenceAreaProductRelationList;
}
