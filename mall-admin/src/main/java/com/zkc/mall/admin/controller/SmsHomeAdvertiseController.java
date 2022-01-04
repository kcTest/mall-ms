package com.zkc.mall.admin.controller;

import com.zkc.mall.admin.service.SmsHomeAdvertiseService;
import com.zkc.mall.common.api.CommonPage;
import com.zkc.mall.common.api.CommonResult;
import com.zkc.mall.mbg.model.SmsHomeAdvertise;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@Api(tags = "首页轮播广告管理")
@RequestMapping(value = "/home/advertise")
@RestController
public class SmsHomeAdvertiseController {
	
	@Resource
	private SmsHomeAdvertiseService homeAdvertiseService;
	
	@ApiOperation("添加广告")
	@PostMapping("/create")
	@ResponseBody
	public CommonResult<?> create(@RequestBody SmsHomeAdvertise advertise) {
		int count = homeAdvertiseService.create(advertise);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
	@ApiOperation("删除广告")
	@PostMapping("/delete")
	@ResponseBody
	public CommonResult<?> delete(@RequestParam("ids") List<Long> ids) {
		int count = homeAdvertiseService.delete(ids);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
	@ApiOperation("修改上下线状态")
	@PostMapping("/update/status/{id}")
	@ResponseBody
	public CommonResult<?> updateStatus(@PathVariable Long id, @RequestParam("status") Integer status) {
		int count = homeAdvertiseService.updateStatus(id, status);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
	@ApiOperation("获取广告详情")
	@GetMapping("/{id}")
	@ResponseBody
	public CommonResult<SmsHomeAdvertise> getItem(@PathVariable Long id) {
		SmsHomeAdvertise shomeAdvertise = homeAdvertiseService.getItem(id);
		return CommonResult.success(shomeAdvertise);
	}
	
	@ApiOperation("修改广告")
	@PostMapping("/update/{id}")
	@ResponseBody
	public CommonResult<?> update(@PathVariable Long id, @RequestBody SmsHomeAdvertise homeAdvertise) {
		int count = homeAdvertiseService.update(id, homeAdvertise);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
	@ApiOperation("分页查询广告")
	@GetMapping("/list")
	@ResponseBody
	public CommonResult<CommonPage<SmsHomeAdvertise>> list(
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "type", required = false) Integer type,
			@RequestParam(value = "endTime", required = false) String endTime,
			@RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
			@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
		List<SmsHomeAdvertise> homeAdvertiseList = homeAdvertiseService.list(name, type, endTime, pageSize, pageNum);
		return CommonResult.success(CommonPage.restPage(homeAdvertiseList));
	}
	
}
	
	
	