package com.zkc.mall.mbg.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

/**
 * oms_order_return_reason
 * @author 
 */
@ApiModel(value="com.zkc.mall.mbg.model.OmsOrderReturnReason退货原因表")
public class OmsOrderReturnReason implements Serializable {
    private Long id;

    /**
     * 退货类型
     */
    @ApiModelProperty(value="退货类型")
    private String name;

    private Integer sort;

    /**
     * 状态：0->不启用；1->启用
     */
    @ApiModelProperty(value="状态：0->不启用；1->启用")
    private Integer status;

    /**
     * 添加时间
     */
    @ApiModelProperty(value="添加时间")
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

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
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