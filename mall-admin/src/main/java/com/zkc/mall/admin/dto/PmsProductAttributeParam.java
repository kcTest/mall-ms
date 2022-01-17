package com.zkc.mall.admin.dto;

import com.zkc.mall.admin.validator.FlagValidator;
import com.zkc.mall.mbg.model.PmsProductAttribute;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class PmsProductAttributeParam extends PmsProductAttribute {
	
	@NotEmpty
	@Schema(description ="属性分类ID")
	private Long productAttributeCategoryId;
	
	@NotEmpty
	@Schema(description ="属性名称")
	private String name;
	
	/**
	 * 属性选择类型：0->唯一；1->单选；2->多选
	 */
	@FlagValidator({"0", "1", "2"})
	@Schema(description= "属性选择类型：0->唯一；1->单选；2->多选")
	private Integer selectType;
	
	/**
	 * 属性录入方式：0->手工录入；1->从列表中选取
	 */
	@FlagValidator({"0", "1"})
	@Schema(description= "属性录入方式：0->手工录入；1->从列表中选取")
	private Integer inputType;
	
	/**
	 * 可选值列表，以逗号隔开
	 */
	@Schema(description= "可选值列表，以逗号隔开")
	private String inputList;
	
	/**
	 * 排序字段：最高的可以单独上传图片
	 */
	@Schema(description= "排序字段：最高的可以单独上传图片")
	private Integer sort;
	
	/**
	 * 分类筛选样式：1->普通；1->颜色
	 */
	@FlagValidator({"0", "1"})
	@Schema(description= "分类筛选样式：0->普通；1->颜色")
	private Integer filterType;
	
	/**
	 * 检索类型；0->不需要进行检索；1->关键字检索；2->范围检索
	 */
	@FlagValidator({"0", "1", "2"})
	@Schema(description= "检索类型；0->不需要进行检索；1->关键字检索；2->范围检索")
	private Integer searchType;
	
	/**
	 * 相同属性产品是否关联；0->不关联；1->关联
	 */
	@FlagValidator({"0", "1"})
	@Schema(description= "相同属性产品是否关联；0->不关联；1->关联")
	private Integer relatedStatus;
	
	/**
	 * 是否支持手动新增；0->不支持；1->支持
	 */
	@FlagValidator({"0", "1"})
	@Schema(description= "是否支持手动新增；0->不支持；1->支持")
	private Integer handAddStatus;
	
	/**
	 * 属性的类型；0->规格；1->参数
	 */
	@FlagValidator({"0", "1"})
	@Schema(description= "属性的类型；0->规格；1->参数")
	private Integer type;
}
