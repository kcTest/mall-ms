package com.zkc.mall.admin.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UmsAdminRegistryParam {
	
	@NotEmpty
	@ApiModelProperty(value = "用户名", required = true)
	private String username;
	
	@NotEmpty
	@ApiModelProperty(value = "密码", required = true)
	private String password;
}