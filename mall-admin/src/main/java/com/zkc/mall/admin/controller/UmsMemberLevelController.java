package com.zkc.mall.admin.controller;

import com.zkc.mall.admin.service.UmsMemberLevelService;
import com.zkc.mall.common.api.CommonResult;
import com.zkc.mall.mbg.model.UmsMemberLevel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api("会员等级管理")
@RequestMapping("/memberLevel")
@RestController
public class UmsMemberLevelController {
	
	@Resource
	private UmsMemberLevelService memberLevelService;
	
	@ApiOperation("查询所有会员等级")
	@GetMapping("/list")
	@ResponseBody
	public CommonResult<List<UmsMemberLevel>> list(@RequestParam("defaultStatus") Integer defaultStatus) {
		List<UmsMemberLevel> memberLevelList = memberLevelService.list(defaultStatus);
		return CommonResult.success(memberLevelList);
	}
}
