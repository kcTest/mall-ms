package com.zkc.mall.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

public class PmsProductResult extends PmsProductParam {
	@Getter
	@Setter
	@Schema(description ="商品分类的父ID")
	private Long cateParentId;
}
