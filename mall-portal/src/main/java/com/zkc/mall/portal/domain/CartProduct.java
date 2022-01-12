package com.zkc.mall.portal.domain;

import com.zkc.mall.mbg.model.PmsProduct;
import com.zkc.mall.mbg.model.PmsProductAttribute;
import com.zkc.mall.mbg.model.PmsSkuStock;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class CartProduct extends PmsProduct {
	
	@Getter
	@Setter
	private List<PmsProductAttribute> productAttributeList;
	@Getter
	@Setter
	private List<PmsSkuStock> skuStockList;
}
