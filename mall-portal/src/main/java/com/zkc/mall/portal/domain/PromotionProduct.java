package com.zkc.mall.portal.domain;

import com.zkc.mall.mbg.model.PmsProduct;
import com.zkc.mall.mbg.model.PmsProductFullReduction;
import com.zkc.mall.mbg.model.PmsProductLadder;
import com.zkc.mall.mbg.model.PmsSkuStock;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


public class PromotionProduct extends PmsProduct {
	
	/**
	 * 商品库存信息
	 */
	@Getter
	@Setter
	private List<PmsSkuStock> skuStockList;
	
	/**
	 * 商品打折信息
	 */
	@Getter
	@Setter
	private List<PmsProductLadder> productLadderList;
	
	/**
	 * 商品满减信息
	 */
	@Getter
	@Setter
	private List<PmsProductFullReduction> productFullReductionList;
}
