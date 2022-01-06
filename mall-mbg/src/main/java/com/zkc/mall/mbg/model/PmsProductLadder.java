package com.zkc.mall.mbg.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * pms_product_ladder
 * @author 
 */
@ApiModel(value="com.zkc.mall.mbg.model.PmsProductLadder产品阶梯价格表(只针对同商品)")
public class PmsProductLadder implements Serializable {
    private Long id;

    private Long productId;

    /**
     * 满足的商品数量
     */
    @ApiModelProperty(value="满足的商品数量")
    private Integer count;

    /**
     * 折扣
     */
    @ApiModelProperty(value="折扣")
    private BigDecimal discount;

    /**
     * 折后价格
     */
    @ApiModelProperty(value="折后价格")
    private BigDecimal price;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}