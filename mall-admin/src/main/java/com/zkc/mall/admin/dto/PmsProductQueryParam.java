package com.zkc.mall.admin.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PmsProductQueryParam {
	
	@ApiModelProperty("上架状态")
	private Integer publishStatus;
	
	@ApiModelProperty("审核状态")
	private Integer verifyStatus;
	
	@ApiModelProperty("商品名称")
	private String keyword;
	
	@ApiModelProperty("货号")
	private String productSn;
	
	@ApiModelProperty("商品分类编号")
	private Long productCategoryId;
	
	@ApiModelProperty("商品品牌编号")
	private Long brandId;
}
