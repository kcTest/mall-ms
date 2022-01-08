package com.zkc.mall.admin.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MinioUploadDto {
	
	@ApiModelProperty("文件访问URL")
	private String url;
	
	@ApiModelProperty("文件名称")
	private String name;
}
