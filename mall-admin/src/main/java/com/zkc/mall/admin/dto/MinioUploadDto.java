package com.zkc.mall.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class MinioUploadDto {
	
	@Schema(description ="文件访问URL")
	private String url;
	
	@Schema(description ="文件名称")
	private String name;
}
