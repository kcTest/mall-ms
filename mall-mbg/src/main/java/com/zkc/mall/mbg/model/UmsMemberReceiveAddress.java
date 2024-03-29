package com.zkc.mall.mbg.model;


import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;

/**
 * ums_member_receive_address
 * @author 
 */
@Schema(description="com.zkc.mall.mbg.model.UmsMemberReceiveAddress会员收货地址表")
public class UmsMemberReceiveAddress implements Serializable {
    private Long id;

    private Long memberId;

    /**
     * 收货人名称
     */
   @Schema(description="收货人名称")
    private String name;

    private String phoneNumber;

    /**
     * 是否为默认
     */
   @Schema(description="是否为默认")
    private Integer defaultStatus;

    /**
     * 邮政编码
     */
   @Schema(description="邮政编码")
    private String postCode;

    /**
     * 省份/直辖市
     */
   @Schema(description="省份/直辖市")
    private String province;

    /**
     * 城市
     */
   @Schema(description="城市")
    private String city;

    /**
     * 区
     */
   @Schema(description="区")
    private String region;

    /**
     * 详细地址(街道)
     */
   @Schema(description="详细地址(街道)")
    private String detailAddress;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getDefaultStatus() {
        return defaultStatus;
    }

    public void setDefaultStatus(Integer defaultStatus) {
        this.defaultStatus = defaultStatus;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
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