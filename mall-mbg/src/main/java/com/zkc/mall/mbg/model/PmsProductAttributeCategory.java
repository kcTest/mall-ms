package com.zkc.mall.mbg.model;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

/**
 * pms_product_attribute_category
 * @author 
 */
@Schema(description="com.zkc.mall.mbg.model.PmsProductAttributeCategory产品属性分类表")
public class PmsProductAttributeCategory implements Serializable {
    private Long id;

    private String name;

    /**
     * 属性数量
     */
   @Schema(description="属性数量")
    private Integer attributeCount;

    /**
     * 参数数量
     */
   @Schema(description="参数数量")
    private Integer paramCount;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAttributeCount() {
        return attributeCount;
    }

    public void setAttributeCount(Integer attributeCount) {
        this.attributeCount = attributeCount;
    }

    public Integer getParamCount() {
        return paramCount;
    }

    public void setParamCount(Integer paramCount) {
        this.paramCount = paramCount;
    }
}