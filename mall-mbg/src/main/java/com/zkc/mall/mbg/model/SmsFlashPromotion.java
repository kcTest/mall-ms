package com.zkc.mall.mbg.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

/**
 * sms_flash_promotion
 * @author 
 */
@ApiModel(value="com.zkc.mall.mbg.model.SmsFlashPromotion限时购表")
public class SmsFlashPromotion implements Serializable {
    private Long id;

    private String title;

    /**
     * 开始日期
     */
    @ApiModelProperty(value="开始日期")
    private Date startDate;

    /**
     * 结束日期
     */
    @ApiModelProperty(value="结束日期")
    private Date endDate;

    /**
     * 上下线状态
     */
    @ApiModelProperty(value="上下线状态")
    private Integer status;

    /**
     * 秒杀时间段名称
     */
    @ApiModelProperty(value="秒杀时间段名称")
    private Date createTime;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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