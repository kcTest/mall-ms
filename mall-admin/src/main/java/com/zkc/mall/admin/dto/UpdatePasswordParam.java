package com.zkc.mall.admin.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class UpdatePasswordParam {
	
	@ApiModelProperty(value = "用户id", required = true)
	@NotEmpty
	private Long id;
	
	@ApiModelProperty(value = "旧密码", required = true)
	@NotEmpty
	private String oldPassword;
	
	@ApiModelProperty(value = "新密码", required = true)
	@NotEmpty
	private String newPassword;
	
	
}
