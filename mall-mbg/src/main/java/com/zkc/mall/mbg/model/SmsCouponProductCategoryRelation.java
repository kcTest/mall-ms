package com.zkc.mall.mbg.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

/**
 * sms_coupon_product_category_relation
 * @author 
 */
@ApiModel(value="com.zkc.mall.mbg.model.SmsCouponProductCategoryRelation优惠券和产品分类关系表")
public class SmsCouponProductCategoryRelation implements Serializable {
    private Long id;

    private Long couponId;

    private Long productCategoryId;

    /**
     * 产品分类名称
     */
    @ApiModelProperty(value="产品分类名称")
    private String productCategoryName;

    /**
     * 父分类名称
     */
    @ApiModelProperty(value="父分类名称")
    private String parentCategoryName;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    public Long getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(Long productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public String getProductCategoryName() {
        return productCategoryName;
    }

    public void setProductCategoryName(String productCategoryName) {
        this.productCategoryName = productCategoryName;
    }

    public String getParentCategoryName() {
        return parentCategoryName;
    }

    public void setParentCategoryName(String parentCategoryName) {
        this.parentCategoryName = parentCategoryName;
    }
}