package com.zkc.mall.mbg.model;


import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * pms_sku_stock
 * @author 
 */
@Schema(description="com.zkc.mall.mbg.model.PmsSkuStock sku的库存")
public class PmsSkuStock implements Serializable {
    private Long id;

    private Long productId;

    /**
     * sku编码
     */
   @Schema(description="sku编码")
    private String skuCode;

    private BigDecimal price;

    /**
     * 库存
     */
   @Schema(description="库存")
    private Integer stock;

    /**
     * 预警库存
     */
   @Schema(description="预警库存")
    private Integer lowStock;

    /**
     * 展示图片
     */
   @Schema(description="展示图片")
    private String pic;

    /**
     * 销量
     */
   @Schema(description="销量")
    private Integer sale;

    /**
     * 单品促销价格
     */
   @Schema(description="单品促销价格")
    private BigDecimal promotionPrice;

    /**
     * 锁定库存
     */
   @Schema(description="锁定库存")
    private Integer lockStock;

    /**
     * 商品销售属性，json格式
     */
   @Schema(description="商品销售属性，json格式")
    private String spData;

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

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getLowStock() {
        return lowStock;
    }

    public void setLowStock(Integer lowStock) {
        this.lowStock = lowStock;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public Integer getSale() {
        return sale;
    }

    public void setSale(Integer sale) {
        this.sale = sale;
    }

    public BigDecimal getPromotionPrice() {
        return promotionPrice;
    }

    public void setPromotionPrice(BigDecimal promotionPrice) {
        this.promotionPrice = promotionPrice;
    }

    public Integer getLockStock() {
        return lockStock;
    }

    public void setLockStock(Integer lockStock) {
        this.lockStock = lockStock;
    }

    public String getSpData() {
        return spData;
    }

    public void setSpData(String spData) {
        this.spData = spData;
    }
}