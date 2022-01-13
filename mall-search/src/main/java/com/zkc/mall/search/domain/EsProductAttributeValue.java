package com.zkc.mall.search.domain;

import lombok.Getter;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import java.io.Serializable;

/**
 * 商品属性信息
 */
@Getter
@Setting
public class EsProductAttributeValue implements Serializable {
	
	private static final long serialVersionUID = -1L;
	
	private Long id;
	private Long productAttributeId;
	
	@Field(type = FieldType.Keyword)
	private String value;
	
	private Integer type;
	
	@Field(type = FieldType.Keyword)
	private String name;
}
