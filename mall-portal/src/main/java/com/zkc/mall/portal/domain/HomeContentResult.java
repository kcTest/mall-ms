package com.zkc.mall.portal.domain;

import com.zkc.mall.mbg.model.CmsSubject;
import com.zkc.mall.mbg.model.PmsBrand;
import com.zkc.mall.mbg.model.PmsProduct;
import com.zkc.mall.mbg.model.SmsHomeAdvertise;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class HomeContentResult {
	
	/**
	 * 轮播广告
	 */
	private List<SmsHomeAdvertise> advertiseList;
	
	/**
	 * 推荐品牌
	 */
	private List<PmsBrand> brandList;
	
	/**
	 * 当前秒杀场次
	 */
	private HomeFlashPromotion homeFlashPromotion;
	
	/**
	 * 新品推荐
	 */
	private List<PmsProduct> newProductList;
	
	/**
	 * 人气推荐
	 */
	private List<PmsProduct> hotProductList;
	
	/**
	 * 推荐专题
	 */
	private List<CmsSubject> subjectList;	
}
