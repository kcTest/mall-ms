package com.zkc.mall.admin.controller;

import com.zkc.mall.admin.dto.OssCallbackResult;
import com.zkc.mall.admin.dto.OssPolicyResult;
import com.zkc.mall.admin.service.OssService;
import com.zkc.mall.common.api.CommonResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import javax.servlet.http.HttpServletRequest;

@Tag(name = "OssController", description = "Oss管理")
@CrossOrigin
@RestController
@RequestMapping("/aliyun/oss")
public class OssController {
	
	@Autowired
	private OssService oSSService;
	
	@Operation(summary ="oss上传签名生成")
	@GetMapping("/policy")
	@ResponseBody
	public CommonResult<OssPolicyResult> policy() {
		OssPolicyResult result = oSSService.policy();
		return CommonResult.success(result);
	}
	
	@Operation(summary ="oss上传成功回调")
	@PostMapping("/callback")
	@ResponseBody
	public CommonResult<OssCallbackResult> callback(HttpServletRequest request) {
		OssCallbackResult result = oSSService.callback(request);
		return CommonResult.success(result);
	}
}
