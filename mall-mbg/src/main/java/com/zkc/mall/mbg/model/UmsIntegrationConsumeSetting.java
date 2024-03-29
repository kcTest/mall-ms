package com.zkc.mall.mbg.model;


import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;

/**
 * ums_integration_consume_setting
 * @author 
 */
@Schema(description="com.zkc.mall.mbg.model.UmsIntegrationConsumeSetting积分消费设置")
public class UmsIntegrationConsumeSetting implements Serializable {
    private Long id;

    /**
     * 每一元需要抵扣的积分数量
     */
   @Schema(description="每一元需要抵扣的积分数量")
    private Integer deductionPerAmount;

    /**
     * 每笔订单最高抵用百分比
     */
   @Schema(description="每笔订单最高抵用百分比")
    private Integer maxPercentPerOrder;

    /**
     * 每次使用积分最小单位100
     */
   @Schema(description="每次使用积分最小单位100")
    private Integer useUnit;

    /**
     * 是否可以和优惠券同用；0->不可以；1->可以
     */
   @Schema(description="是否可以和优惠券同用；0->不可以；1->可以")
    private Integer couponStatus;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDeductionPerAmount() {
        return deductionPerAmount;
    }

    public void setDeductionPerAmount(Integer deductionPerAmount) {
        this.deductionPerAmount = deductionPerAmount;
    }

    public Integer getMaxPercentPerOrder() {
        return maxPercentPerOrder;
    }

    public void setMaxPercentPerOrder(Integer maxPercentPerOrder) {
        this.maxPercentPerOrder = maxPercentPerOrder;
    }

    public Integer getUseUnit() {
        return useUnit;
    }

    public void setUseUnit(Integer useUnit) {
        this.useUnit = useUnit;
    }

    public Integer getCouponStatus() {
        return couponStatus;
    }

    public void setCouponStatus(Integer couponStatus) {
        this.couponStatus = couponStatus;
    }
}