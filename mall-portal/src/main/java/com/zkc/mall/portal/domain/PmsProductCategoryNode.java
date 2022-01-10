package com.zkc.mall.portal.domain;

import com.zkc.mall.mbg.model.PmsProductCategory;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PmsProductCategoryNode extends PmsProductCategory {
	
	private List<PmsProductCategoryNode> children;
}
