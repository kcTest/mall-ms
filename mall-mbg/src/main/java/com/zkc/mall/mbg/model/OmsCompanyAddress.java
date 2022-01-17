package com.zkc.mall.mbg.model;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

/**
 * oms_company_address
 * @author 
 */
@Schema(description="com.zkc.mall.mbg.model.OmsCompanyAddress公司收发货地址表")
public class OmsCompanyAddress implements Serializable {
    private Long id;

    /**
     * 地址名称
     */
    @Schema(description="地址名称")
    private String addressName;

    /**
     * 默认发货地址：0->否；1->是
     */
    @Schema(description="默认发货地址：0->否；1->是")
    private Integer sendStatus;

    /**
     * 是否默认收货地址：0->否；1->是
     */
    @Schema(description="是否默认收货地址：0->否；1->是")
    private Integer receiveStatus;

    /**
     * 收发货人姓名
     */
    @Schema(description="收发货人姓名")
    private String name;

    /**
     * 收货人电话
     */
    @Schema(description="收货人电话")
    private String phone;

    /**
     * 省/直辖市
     */
    @Schema(description="省/直辖市")
    private String province;

    /**
     * 市
     */
    @Schema(description="市")
    private String city;

    /**
     * 区
     */
    @Schema(description="区")
    private String region;

    /**
     * 详细地址
     */
    @Schema(description="详细地址")
    private String detailAddress;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public Integer getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(Integer sendStatus) {
        this.sendStatus = sendStatus;
    }

    public Integer getReceiveStatus() {
        return receiveStatus;
    }

    public void setReceiveStatus(Integer receiveStatus) {
        this.receiveStatus = receiveStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }
}