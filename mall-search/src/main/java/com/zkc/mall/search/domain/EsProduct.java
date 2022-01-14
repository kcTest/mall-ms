package com.zkc.mall.search.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 搜索商品的信息
 */
@Getter
@Setter
@Setting(replicas = 0)
@Document(indexName = "pms")
public class EsProduct implements Serializable {
	
	private static final long serialVersionUID = -1L;
	
	@Id
	private Long id;
	
	@Field(type = FieldType.Keyword)
	private String productSn;
	
	private Long brandId;
	
	@Field(type = FieldType.Keyword)
	private String brandName;
	
	private Long productCategoryId;
	
	@Field(type = FieldType.Keyword)
	private String productCategoryName;
	
	private String pic;
	
	@Field(analyzer = "ik_max_word", type = FieldType.Text)
	private String name;
	
	@Field(analyzer = "ik_max_word", type = FieldType.Text)
	private String subTitle;
	
	@Field(analyzer = "ik_max_word", type = FieldType.Text)
	private String keywords;
	
	private BigDecimal price;
	
	private Integer sale;
	private Integer newStatus;
	private Integer recommendStatus;
	private Integer stock;
	private Integer promotionType;
	private Integer sort;
	
	@Field(type = FieldType.Nested)
	private List<EsProductAttributeValue> attrValueList;
	
}