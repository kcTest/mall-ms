package com.zkc.mall.mbg.model;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.util.Date;

/**
 * oms_order_operate_history
 * @author 
 */
@Schema(description="com.zkc.mall.mbg.model.OmsOrderOperateHistory订单操作历史记录")
public class OmsOrderOperateHistory implements Serializable {
    private Long id;

    /**
     * 订单id
     */
   @Schema(description="订单id")
    private Long orderId;

    /**
     * 操作人：用户；系统；后台管理员
     */
   @Schema(description="操作人：用户；系统；后台管理员")
    private String operateMan;

    /**
     * 操作时间
     */
   @Schema(description="操作时间")
    private Date createTime;

    /**
     * 订单状态：0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单
     */
   @Schema(description="订单状态：0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单")
    private Integer orderStatus;

    /**
     * 备注
     */
   @Schema(description="备注")
    private String note;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getOperateMan() {
        return operateMan;
    }

    public void setOperateMan(String operateMan) {
        this.operateMan = operateMan;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}