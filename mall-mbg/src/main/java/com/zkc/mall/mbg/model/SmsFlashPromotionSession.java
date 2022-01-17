package com.zkc.mall.mbg.model;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.util.Date;

/**
 * sms_flash_promotion_session
 * @author 
 */
@Schema(description="com.zkc.mall.mbg.model.SmsFlashPromotionSession限时购场次表")
public class SmsFlashPromotionSession implements Serializable {
    /**
     * 编号
     */
   @Schema(description="编号")
    private Long id;

    /**
     * 场次名称
     */
   @Schema(description="场次名称")
    private String name;

    /**
     * 每日开始时间
     */
   @Schema(description="每日开始时间")
    private Date startTime;

    /**
     * 每日结束时间
     */
   @Schema(description="每日结束时间")
    private Date endTime;

    /**
     * 启用状态：0->不启用；1->启用
     */
   @Schema(description="启用状态：0->不启用；1->启用")
    private Integer status;

    /**
     * 创建时间
     */
   @Schema(description="创建时间")
    private Date createTime;

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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}