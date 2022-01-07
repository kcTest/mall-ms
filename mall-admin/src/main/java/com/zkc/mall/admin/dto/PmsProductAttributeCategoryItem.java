package com.zkc.mall.admin.dto;

import com.zkc.mall.mbg.model.PmsProductAttribute;
import com.zkc.mall.mbg.model.PmsProductAttributeCategory;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class PmsProductAttributeCategoryItem extends PmsProductAttributeCategory {
	
	@Getter
	@Setter
	@ApiModelProperty("商品属性列表")
	private List<PmsProductAttribute> productAttributeList;
}
