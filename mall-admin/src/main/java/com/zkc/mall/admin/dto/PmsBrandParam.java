package com.zkc.mall.admin.dto;

import com.zkc.mall.admin.validator.FlagValidator;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Data
public class PmsBrandParam {
	
	@NotEmpty
	@ApiModelProperty("品牌名称")
	private String name;
	
	/**
	 * 首字母
	 */
	@ApiModelProperty(value = "品牌首字母")
	private String firstLetter;
	
	@Min(0)
	@ApiModelProperty("排序字段")
	private Integer sort;
	
	/**
	 * 是否为品牌制造商：0->不是；1->是
	 */
	@FlagValidator(value = {"0", "1"}, message = "厂家状态不正确")
	@ApiModelProperty(value = "是否为品牌制造商：0->不是；1->是")
	private Integer factoryStatus;
	
	
	@FlagValidator(value = {"0", "1"}, message = "显示状态不正确")
	@ApiModelProperty(value = "是否进行显示")
	private Integer showStatus;
	
	/**
	 * 品牌logo
	 */
	@NotEmpty
	@ApiModelProperty(value = "品牌logo", required = true)
	private String logo;
	
	/**
	 * 专区大图
	 */
	@ApiModelProperty(value = "专区大图")
	private String bigPic;
	
	/**
	 * 品牌故事
	 */
	@ApiModelProperty(value = "品牌故事")
	private String brandStory;
}
