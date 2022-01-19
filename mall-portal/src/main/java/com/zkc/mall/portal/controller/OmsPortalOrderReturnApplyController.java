package com.zkc.mall.portal.controller;

import com.zkc.mall.common.api.CommonResult;
import com.zkc.mall.portal.domain.OmsOrderReturnApplyParam;
import com.zkc.mall.portal.service.OmsPortalOrderReturnApplyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
@Tag(name = "OmsPortalOrderReturnApplyController", description = "退货申请管理")

@RestController
@RequestMapping("/returnApply")
public class OmsPortalOrderReturnApplyController {
	
	@Autowired
	private OmsPortalOrderReturnApplyService returnApplyService;
	
	@Operation(summary ="申请退货")
	@GetMapping("/create")
	@ResponseBody
	public CommonResult<?> create(@RequestBody OmsOrderReturnApplyParam returnApply) {
		int count = returnApplyService.create(returnApply);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
}
