package com.zkc.mall.mbg.model;


import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * sms_flash_promotion_product_relation
 * @author 
 */
@Schema(description="com.zkc.mall.mbg.model.SmsFlashPromotionProductRelation商品限时购与商品关系表")
public class SmsFlashPromotionProductRelation implements Serializable {
    /**
     * 编号
     */
   @Schema(description="编号")
    private Long id;

    private Long flashPromotionId;

    /**
     * 编号
     */
   @Schema(description="编号")
    private Long flashPromotionSessionId;

    private Long productId;

    /**
     * 限时购价格
     */
   @Schema(description="限时购价格")
    private BigDecimal flashPromotionPrice;

    /**
     * 限时购数量
     */
   @Schema(description="限时购数量")
    private Integer flashPromotionCount;

    /**
     * 每人限购数量
     */
   @Schema(description="每人限购数量")
    private Integer flashPromotionLimit;

    /**
     * 排序
     */
   @Schema(description="排序")
    private Integer sort;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFlashPromotionId() {
        return flashPromotionId;
    }

    public void setFlashPromotionId(Long flashPromotionId) {
        this.flashPromotionId = flashPromotionId;
    }

    public Long getFlashPromotionSessionId() {
        return flashPromotionSessionId;
    }

    public void setFlashPromotionSessionId(Long flashPromotionSessionId) {
        this.flashPromotionSessionId = flashPromotionSessionId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public BigDecimal getFlashPromotionPrice() {
        return flashPromotionPrice;
    }

    public void setFlashPromotionPrice(BigDecimal flashPromotionPrice) {
        this.flashPromotionPrice = flashPromotionPrice;
    }

    public Integer getFlashPromotionCount() {
        return flashPromotionCount;
    }

    public void setFlashPromotionCount(Integer flashPromotionCount) {
        this.flashPromotionCount = flashPromotionCount;
    }

    public Integer getFlashPromotionLimit() {
        return flashPromotionLimit;
    }

    public void setFlashPromotionLimit(Integer flashPromotionLimit) {
        this.flashPromotionLimit = flashPromotionLimit;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}