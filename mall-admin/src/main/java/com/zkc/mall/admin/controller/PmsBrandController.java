package com.zkc.mall.admin.controller;

import com.zkc.mall.admin.dto.PmsBrandParam;
import com.zkc.mall.admin.service.PmsBrandService;
import com.zkc.mall.common.api.CommonPage;
import com.zkc.mall.common.api.CommonResult;
import com.zkc.mall.mbg.model.PmsBrand;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api("商品品牌管理")
@RequestMapping("/brand")
@RestController
public class PmsBrandController {
	
	@Resource
	private PmsBrandService brandService;
	
	@ApiOperation("获取全部品牌列表")
	@GetMapping("/listAll")
	@ResponseBody
	public CommonResult<List<PmsBrand>> getList() {
		List<PmsBrand> brandList = brandService.getList();
		return CommonResult.success(brandList);
	}
	
	@ApiOperation("添加品牌")
	@PostMapping("/create")
	@ResponseBody
	public CommonResult<?> create(@Validated @RequestBody PmsBrandParam brandParam) {
		int count = brandService.create(brandParam);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
	@ApiOperation("更新品牌")
	@PostMapping("/update/{id}")
	@ResponseBody
	public CommonResult<?> update(@PathVariable Long id, @Validated @RequestBody PmsBrandParam brandParam) {
		int count = brandService.update(id, brandParam);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
	@ApiOperation("删除品牌")
	@GetMapping("/delete/{id}")
	@ResponseBody
	public CommonResult<?> delete(@PathVariable Long id) {
		int count = brandService.delete(id);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
	@ApiOperation("根据品牌名称分页获取品牌列表")
	@GetMapping("/list")
	@ResponseBody
	public CommonResult<CommonPage<PmsBrand>> getList(@RequestParam(value = "name") String name,
													  @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
													  @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
		List<PmsBrand> brandList = brandService.getList(name, pageSize, pageNum);
		return CommonResult.success(CommonPage.restPage(brandList));
	}
	
	
	@ApiOperation("查询单个品牌")
	@GetMapping("/{id}")
	@ResponseBody
	public CommonResult<PmsBrand> getItem(@PathVariable Long id) {
		PmsBrand brand = brandService.getItem(id);
		return CommonResult.success(brand);
	}
	
	@ApiOperation("批量删除品牌")
	@RequestMapping("/delete/batch")
	@ResponseBody
	public CommonResult<?> deleteBatch(@RequestParam List<Long> ids) {
		int count = brandService.deleteBatch(ids);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
	@ApiOperation("批量更新显示状态")
	@RequestMapping("/update/showStatus")
	@ResponseBody
	public CommonResult<?> updateShowStatus(@RequestParam("ids") List<Long> ids, @RequestParam("showStatus") Integer showStatus) {
		int count = brandService.updateShowStatus(ids, showStatus);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
	@ApiOperation("批量更新厂家制造商状态")
	@RequestMapping("/update/factoryStatus")
	@ResponseBody
	public CommonResult<?> updateFactoryStatus(@RequestParam("ids") List<Long> ids, @RequestParam("factoryStatus") Integer factoryStatus) {
		int count = brandService.updateFactoryStatus(ids, factoryStatus);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
}
