package com.zkc.mall.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class UpdatePasswordParam {
	
	@Schema(description= "用户id", required = true)
	@NotEmpty
	private Long id;
	
	@Schema(description= "旧密码", required = true)
	@NotEmpty
	private String oldPassword;
	
	@Schema(description= "新密码", required = true)
	@NotEmpty
	private String newPassword;
	
	
}
