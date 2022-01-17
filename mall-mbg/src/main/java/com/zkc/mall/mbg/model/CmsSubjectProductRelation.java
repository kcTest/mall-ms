package com.zkc.mall.mbg.model;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

/**
 * cms_subject_product_relation
 *
 * @author
 */
@Schema(description = "com.zkc.mall.mbg.model.CmsSubjectProductRelation专题商品关系表")
public class CmsSubjectProductRelation implements Serializable {
	private Long id;
	
	private Long subjectId;
	
	private Long productId;
	
	private static final long serialVersionUID = 1L;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getSubjectId() {
		return subjectId;
	}
	
	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}
	
	public Long getProductId() {
		return productId;
	}
	
	public void setProductId(Long productId) {
		this.productId = productId;
	}
}