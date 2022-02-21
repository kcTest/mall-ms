package com.zkc.mall.mbg.model;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

/**
 * cms_preference_area
 *
 * @author
 */
@Schema(description = "com.zkc.mall.mbg.model.CmsPreferenceArea优选专区")
public class CmsPreferenceArea implements Serializable {
	private Long id;
	
	private String name;
	
	private String subTitle;
	
	private Integer sort;
	
	private Integer showStatus;
	
	/**
	 * 展示图片
	 */
	@Schema(description = "展示图片")
	private byte[] pic;
	
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
	
	public String getSubTitle() {
		return subTitle;
	}
	
	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}
	
	public Integer getSort() {
		return sort;
	}
	
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	public Integer getShowStatus() {
		return showStatus;
	}
	
	public void setShowStatus(Integer showStatus) {
		this.showStatus = showStatus;
	}
	
	public byte[] getPic() {
		return pic;
	}
	
	public void setPic(byte[] pic) {
		this.pic = pic;
	}
}