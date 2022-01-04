package com.zkc.mall.admin.controller;

import com.zkc.mall.admin.service.SmsHomeRecommendSubjectService;
import com.zkc.mall.common.api.CommonPage;
import com.zkc.mall.common.api.CommonResult;
import com.zkc.mall.mbg.model.SmsHomeRecommendSubject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "首页专题推荐管理")
@RequestMapping(value = "/home/recommendSubject")
@RestController
public class SmsHomeRecommendSubjectController {
	
	@Resource
	private SmsHomeRecommendSubjectService homeRecommendSubjectService;
	
	@ApiOperation("添加首页推荐专题")
	@PostMapping("/create")
	@ResponseBody
	public CommonResult<?> create(@RequestBody List<SmsHomeRecommendSubject> homeRecommendSubject) {
		int count = homeRecommendSubjectService.create(homeRecommendSubject);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
	@ApiOperation("修改推荐排序")
	@PostMapping("/update/sort/{id}")
	@ResponseBody
	public CommonResult<?> updateSort(@PathVariable Long id, @RequestParam("sort") Integer sort) {
		int count = homeRecommendSubjectService.updateSort(id, sort);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
	@ApiOperation("批量删除推荐排序")
	@PostMapping("/delete")
	@ResponseBody
	public CommonResult<?> delete(@RequestParam("ids") List<Long> ids) {
		int count = homeRecommendSubjectService.delete(ids);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
	@ApiOperation("批量修改推荐状态")
	@PostMapping("/update/recommendStatus")
	@ResponseBody
	public CommonResult<?> updateRecommendStatus(@RequestParam("ids") List<Long> ids, @RequestParam("status") Integer recommendStatus) {
		int count = homeRecommendSubjectService.updateRecommendStatus(ids, recommendStatus);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
	@ApiOperation("分页查询专题推荐")
	@GetMapping("/list")
	@ResponseBody
	public CommonResult<CommonPage<SmsHomeRecommendSubject>> list(
			@RequestParam(value = "subjectName", required = false) String subjectName,
			@RequestParam(value = "recommendStatus", required = false) Integer recommendStatus,
			@RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
			@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
		List<SmsHomeRecommendSubject> HotRecommendSubjectList = homeRecommendSubjectService.list(subjectName, recommendStatus, pageSize, pageNum);
		return CommonResult.success(CommonPage.restPage(HotRecommendSubjectList));
	}
	
}
	
	
	