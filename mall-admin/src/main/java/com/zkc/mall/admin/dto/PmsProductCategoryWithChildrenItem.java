package com.zkc.mall.admin.dto;

import com.zkc.mall.mbg.model.PmsProductCategory;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class PmsProductCategoryWithChildrenItem extends PmsProductCategory {
	
	@Getter
	@Setter
	@ApiModelProperty("子分类")
	private List<PmsProductCategory> children;
}
