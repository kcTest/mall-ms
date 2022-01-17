package com.zkc.mall.admin.dto;

import com.zkc.mall.admin.validator.FlagValidator;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class PmsProductCategoryParam {
	
	/**
	 * 上机分类的编号：0表示一级分类
	 */
	@Schema(description= "上机分类的编号：0表示一级分类")
	private Long parentId;
	
	/**
	 * 商品分类名称
	 */
	@NotEmpty
	@Schema(description= "商品分类名称")
	private String name;
	
	/**
	 * 分类单位
	 */
	@Schema(description= "分类单位")
	private String productUnit;
	
	/**
	 * 是否显示在导航栏：0->不显示；1->显示
	 */
	@FlagValidator(value = {"0", "1"}, message = "状态只能为0或1")
	@Schema(description= "是否显示在导航栏：0->不显示；1->显示")
	private Integer navStatus;
	
	/**
	 * 显示状态：0->不显示；1->显示
	 */
	@FlagValidator(value = {"0", "1"}, message = "状态只能为0或1")
	@Schema(description= "显示状态：0->不显示；1->显示")
	private Integer showStatus;
	
	/**
	 * 排序
	 */
	@Min(value = 0)
	@Schema(description= "排序")
	private Integer sort;
	
	/**
	 * 图标
	 */
	@Schema(description= "图标")
	private String icon;
	
	/**
	 * 关键字
	 */
	@Schema(description= "关键字")
	private String keywords;
	
	/**
	 * 描述
	 */
	@Schema(description= "描述")
	private String description;
	
	@Schema(description= "产品相关筛选属性集合")
	private List<Long> productAttributeIdList;
}
