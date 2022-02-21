package com.zkc.mall.mbg.model;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

/**
 * cms_preference_area_product_relation
 *
 * @author
 */
@Schema(description = "com.zkc.mall.mbg.model.CmsPreferenceAreaProductRelation优选专区和产品关系表")
public class CmsPreferenceAreaProductRelation implements Serializable {
	private Long id;
	
	private Long preferenceAreaId;
	
	private Long productId;
	
	private static final long serialVersionUID = 1L;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getPreferenceAreaId() {
		return preferenceAreaId;
	}
	
	public void setPreferenceAreaId(Long preferenceAreaId) {
		this.preferenceAreaId = preferenceAreaId;
	}
	
	public Long getProductId() {
		return productId;
	}
	
	public void setProductId(Long productId) {
		this.productId = productId;
	}
}