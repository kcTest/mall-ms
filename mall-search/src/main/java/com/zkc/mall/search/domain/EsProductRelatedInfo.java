package com.zkc.mall.search.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 搜索商品的品牌、分类、属性
 */
@Getter
@Setter
public class EsProductRelatedInfo {
	
	private List<String> brandNames;
	private List<String> productCategoryNames;
	private List<ProductAttr> productAttrs;
	
	@Getter
	@Setter
	public static class ProductAttr {
		private Long attrId;
		private String attrName;
		private List<String> attrValues;
	}
}
