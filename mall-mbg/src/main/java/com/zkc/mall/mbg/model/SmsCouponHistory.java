package com.zkc.mall.mbg.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

/**
 * sms_coupon_history
 * @author 
 */
@ApiModel(value="com.zkc.mall.mbg.model.SmsCouponHistory优惠券使用、领取历史表")
public class SmsCouponHistory implements Serializable {
    private Long id;

    private Long couponId;

    private Long memberId;

    private String couponCode;

    /**
     * 领取人昵称
     */
    @ApiModelProperty(value="领取人昵称")
    private String memberNickname;

    /**
     * 获取类型：0->后台赠送；1->主动获取
     */
    @ApiModelProperty(value="获取类型：0->后台赠送；1->主动获取")
    private Integer getType;

    private Date createTime;

    /**
     * 使用状态：0->未使用；1->已使用；2->已过期
     */
    @ApiModelProperty(value="使用状态：0->未使用；1->已使用；2->已过期")
    private Integer useStatus;

    /**
     * 使用时间
     */
    @ApiModelProperty(value="使用时间")
    private Date useTime;

    /**
     * 订单编号
     */
    @ApiModelProperty(value="订单编号")
    private Long orderId;

    /**
     * 订单号码
     */
    @ApiModelProperty(value="订单号码")
    private String orderSn;

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

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public String getMemberNickname() {
        return memberNickname;
    }

    public void setMemberNickname(String memberNickname) {
        this.memberNickname = memberNickname;
    }

    public Integer getGetType() {
        return getType;
    }

    public void setGetType(Integer getType) {
        this.getType = getType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getUseStatus() {
        return useStatus;
    }

    public void setUseStatus(Integer useStatus) {
        this.useStatus = useStatus;
    }

    public Date getUseTime() {
        return useTime;
    }

    public void setUseTime(Date useTime) {
        this.useTime = useTime;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }
}