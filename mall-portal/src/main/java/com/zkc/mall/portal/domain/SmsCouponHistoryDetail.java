package com.zkc.mall.portal.domain;

import com.zkc.mall.mbg.model.SmsCoupon;
import com.zkc.mall.mbg.model.SmsCouponHistory;
import com.zkc.mall.mbg.model.SmsCouponProductCategoryRelation;
import com.zkc.mall.mbg.model.SmsCouponProductRelation;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SmsCouponHistoryDetail extends SmsCouponHistory {
	
	/**
	 * 优惠券信息
	 */
	private SmsCoupon coupon;
	
	/**
	 * 优惠券关联商品
	 */
	private List<SmsCouponProductRelation> productRelationList;
	
	/**
	 * 优惠券关联商品分类
	 */
	private List<SmsCouponProductCategoryRelation> categoryRelationList;
	
	
}
