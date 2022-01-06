package com.zkc.mall.admin.dto;

import com.zkc.mall.admin.validator.FlagValidator;
import com.zkc.mall.mbg.model.PmsProductCategory;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class PmsProductCategoryParam {
	
	/**
	 * 上机分类的编号：0表示一级分类
	 */
	@ApiModelProperty(value = "上机分类的编号：0表示一级分类")
	private Long parentId;
	
	/**
	 * 商品分类名称
	 */
	@NotEmpty
	@ApiModelProperty(value = "商品分类名称")
	private String name;
	
	/**
	 * 分类单位
	 */
	@ApiModelProperty(value = "分类单位")
	private String productUnit;
	
	/**
	 * 是否显示在导航栏：0->不显示；1->显示
	 */
	@FlagValidator(value = {"0", "1"}, message = "状态只能为0或1")
	@ApiModelProperty(value = "是否显示在导航栏：0->不显示；1->显示")
	private Integer navStatus;
	
	/**
	 * 显示状态：0->不显示；1->显示
	 */
	@FlagValidator(value = {"0", "1"}, message = "状态只能为0或1")
	@ApiModelProperty(value = "显示状态：0->不显示；1->显示")
	private Integer showStatus;
	
	/**
	 * 排序
	 */
	@Min(value = 0)
	@ApiModelProperty(value = "排序")
	private Integer sort;
	
	/**
	 * 图标
	 */
	@ApiModelProperty(value = "图标")
	private String icon;
	
	/**
	 * 关键字
	 */
	@ApiModelProperty(value = "关键字")
	private String keywords;
	
	/**
	 * 描述
	 */
	@ApiModelProperty(value = "描述")
	private String description;
	
	@ApiModelProperty(value = "产品相关筛选属性集合")
	private List<Long> productAttributeIdList;
}
