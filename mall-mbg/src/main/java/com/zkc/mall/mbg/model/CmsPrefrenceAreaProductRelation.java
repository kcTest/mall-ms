package com.zkc.mall.mbg.model;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

/**
 * cms_prefrence_area_product_relation
 *
 * @author
 */
@Schema(description = "com.zkc.mall.mbg.model.CmsPrefrenceAreaProductRelation优选专区和产品关系表")
public class CmsPrefrenceAreaProductRelation implements Serializable {
	private Long id;
	
	private Long prefrenceAreaId;
	
	private Long productId;
	
	private static final long serialVersionUID = 1L;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getPrefrenceAreaId() {
		return prefrenceAreaId;
	}
	
	public void setPrefrenceAreaId(Long prefrenceAreaId) {
		this.prefrenceAreaId = prefrenceAreaId;
	}
	
	public Long getProductId() {
		return productId;
	}
	
	public void setProductId(Long productId) {
		this.productId = productId;
	}
}