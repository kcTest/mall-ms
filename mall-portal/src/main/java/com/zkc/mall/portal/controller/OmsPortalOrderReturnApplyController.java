package com.zkc.mall.portal.controller;

import com.zkc.mall.common.api.CommonResult;
import com.zkc.mall.portal.domain.OmsOrderReturnApplyParam;
import com.zkc.mall.portal.service.OmsPortalOrderReturnApplyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api("退货申请管理")
@RestController
@RequestMapping("/returnApply")
public class OmsPortalOrderReturnApplyController {
	
	@Resource
	private OmsPortalOrderReturnApplyService returnApplyService;
	
	@ApiOperation("申请退货")
	@GetMapping("/create")
	@ResponseBody
	public CommonResult<?> create(@RequestBody OmsOrderReturnApplyParam returnApply) {
		int count = returnApplyService.create(returnApply);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
}
