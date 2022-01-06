package com.zkc.mall.admin.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

public class PmsProductResult extends PmsProductParam {
	@Getter
	@Setter
	@ApiModelProperty("商品分类的父ID")
	private Long cateParentId;
}
