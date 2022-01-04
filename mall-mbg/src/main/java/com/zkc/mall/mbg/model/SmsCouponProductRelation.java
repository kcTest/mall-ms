package com.zkc.mall.mbg.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

/**
 * sms_coupon_product_relation
 * @author 
 */
@ApiModel(value="com.zkc.mall.mbg.model.SmsCouponProductRelation优惠券和产品的关系表")
public class SmsCouponProductRelation implements Serializable {
    private Long id;

    private Long couponId;

    private Long productId;

    /**
     * 商品名称
     */
    @ApiModelProperty(value="商品名称")
    private String productName;

    /**
     * 商品编码
     */
    @ApiModelProperty(value="商品编码")
    private String productSn;

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

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductSn() {
        return productSn;
    }

    public void setProductSn(String productSn) {
        this.productSn = productSn;
    }
}