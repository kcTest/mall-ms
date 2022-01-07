package com.zkc.mall.admin.controller;

import com.zkc.mall.admin.dto.OssCallbackResult;
import com.zkc.mall.admin.dto.OssPolicyResult;
import com.zkc.mall.admin.service.OssService;
import com.zkc.mall.common.api.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Api("Oss管理")
@RestController
@RequestMapping("/aliyun/oss")
public class OssController {
	
	@Resource
	private OssService oSSService;
	
	@ApiOperation("oss上传签名生成")
	@GetMapping("/policy")
	@ResponseBody
	public CommonResult<OssPolicyResult> policy() {
		OssPolicyResult result = oSSService.policy();
		return CommonResult.success(result);
	}
	
	@ApiOperation("oss上传成功回调")
	@PostMapping("/callback")
	@ResponseBody
	public CommonResult<OssCallbackResult> callback(HttpServletRequest request) {
		OssCallbackResult result = oSSService.callback(request);
		return CommonResult.success(result);
	}
}
