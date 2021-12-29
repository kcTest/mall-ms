package com.zkc.mall.exception;

import com.zkc.mall.common.api.CommonResult;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalOauthExceptionHandler {
	
	@ResponseBody
	@ExceptionHandler(value = OAuth2Exception.class)
	public CommonResult<?> handleOauth(OAuth2Exception e) {
		return CommonResult.failed(e.getMessage());
	}
}
