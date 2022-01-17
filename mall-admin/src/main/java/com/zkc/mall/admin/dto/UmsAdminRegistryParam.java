package com.zkc.mall.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UmsAdminRegistryParam {
	
	@NotEmpty
	@Schema(description= "用户名", required = true)
	private String username;
	
	@NotEmpty
	@Schema(description= "密码", required = true)
	private String password;
}
