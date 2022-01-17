package com.zkc.mall.admin.dto;

import com.zkc.mall.admin.validator.FlagValidator;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Data
public class PmsBrandParam {
	
	@NotEmpty
	@Schema(description ="品牌名称")
	private String name;
	
	/**
	 * 首字母
	 */
	@Schema(description= "品牌首字母")
	private String firstLetter;
	
	@Min(0)
	@Schema(description ="排序字段")
	private Integer sort;
	
	/**
	 * 是否为品牌制造商：0->不是；1->是
	 */
	@FlagValidator(value = {"0", "1"}, message = "厂家状态不正确")
	@Schema(description= "是否为品牌制造商：0->不是；1->是")
	private Integer factoryStatus;
	
	
	@FlagValidator(value = {"0", "1"}, message = "显示状态不正确")
	@Schema(description= "是否进行显示")
	private Integer showStatus;
	
	/**
	 * 品牌logo
	 */
	@NotEmpty
	@Schema(description= "品牌logo", required = true)
	private String logo;
	
	/**
	 * 专区大图
	 */
	@Schema(description= "专区大图")
	private String bigPic;
	
	/**
	 * 品牌故事
	 */
	@Schema(description= "品牌故事")
	private String brandStory;
}
