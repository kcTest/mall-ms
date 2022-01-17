package com.zkc.mall.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class PmsProductQueryParam {
	
	@Schema(description ="上架状态")
	private Integer publishStatus;
	
	@Schema(description ="审核状态")
	private Integer verifyStatus;
	
	@Schema(description ="商品名称")
	private String keyword;
	
	@Schema(description ="货号")
	private String productSn;
	
	@Schema(description ="商品分类编号")
	private Long productCategoryId;
	
	@Schema(description ="商品品牌编号")
	private Long brandId;
}
