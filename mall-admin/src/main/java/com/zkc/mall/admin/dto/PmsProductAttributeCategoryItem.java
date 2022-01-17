package com.zkc.mall.admin.dto;

import com.zkc.mall.mbg.model.PmsProductAttribute;
import com.zkc.mall.mbg.model.PmsProductAttributeCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class PmsProductAttributeCategoryItem extends PmsProductAttributeCategory {
	
	@Getter
	@Setter
	@Schema(description ="商品属性列表")
	private List<PmsProductAttribute> productAttributeList;
}
