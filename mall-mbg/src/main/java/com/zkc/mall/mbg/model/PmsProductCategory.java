package com.zkc.mall.mbg.model;


import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;

/**
 * pms_product_category
 * @author 
 */
@Schema(description="com.zkc.mall.mbg.model.PmsProductCategory产品分类")
public class PmsProductCategory implements Serializable {
    private Long id;

    /**
     * 上机分类的编号：0表示一级分类
     */
   @Schema(description="上机分类的编号：0表示一级分类")
    private Long parentId;

    /**
     * 商品分类名称
     */
   @Schema(description="商品分类名称")
    private String name;

    /**
     * 分类级别：0->1级；1->2级
     */
   @Schema(description="分类级别：0->1级；1->2级")
    private Integer level;

    private Integer productCount;

    /**
     * 分类单位
     */
   @Schema(description="分类单位")
    private String productUnit;

    /**
     * 是否显示在导航栏：0->不显示；1->显示
     */
   @Schema(description="是否显示在导航栏：0->不显示；1->显示")
    private Integer navStatus;

    /**
     * 显示状态：0->不显示；1->显示
     */
   @Schema(description="显示状态：0->不显示；1->显示")
    private Integer showStatus;

    /**
     * 排序
     */
   @Schema(description="排序")
    private Integer sort;

    /**
     * 图标
     */
   @Schema(description="图标")
    private String icon;

    /**
     * 关键字
     */
   @Schema(description="关键字")
    private String keywords;

    /**
     * 描述
     */
   @Schema(description="描述")
    private String description;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getProductCount() {
        return productCount;
    }

    public void setProductCount(Integer productCount) {
        this.productCount = productCount;
    }

    public String getProductUnit() {
        return productUnit;
    }

    public void setProductUnit(String productUnit) {
        this.productUnit = productUnit;
    }

    public Integer getNavStatus() {
        return navStatus;
    }

    public void setNavStatus(Integer navStatus) {
        this.navStatus = navStatus;
    }

    public Integer getShowStatus() {
        return showStatus;
    }

    public void setShowStatus(Integer showStatus) {
        this.showStatus = showStatus;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}