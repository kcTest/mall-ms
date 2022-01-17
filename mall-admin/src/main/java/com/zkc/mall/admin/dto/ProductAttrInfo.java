package com.zkc.mall.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ProductAttrInfo {
	
	@Schema(description ="商品属性ID")
	private Long attributeId;
	@Schema(description ="商品属性分类ID")
	private Long attributeCategoryId;
}
