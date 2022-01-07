package com.zkc.mall.admin.controller;

import com.zkc.mall.admin.dto.PmsProductAttributeParam;
import com.zkc.mall.admin.dto.ProductAttrInfo;
import com.zkc.mall.admin.service.PmsProductAttributeService;
import com.zkc.mall.common.api.CommonPage;
import com.zkc.mall.common.api.CommonResult;
import com.zkc.mall.mbg.model.PmsProductAttribute;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api("商品属性管理")
@RestController
@RequestMapping(value = "/productAttribute")
public class PmsProductAttributeController {
	
	@Resource
	private PmsProductAttributeService productAttributeService;
	
	@ApiOperation("根据分类查询属性或参数列表")
	@GetMapping("/list/{cid}")
	@ResponseBody
	public CommonResult<CommonPage<PmsProductAttribute>> getList(@PathVariable Long cid,
																 @RequestParam(value = "type") Integer type,
																 @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
																 @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
		List<PmsProductAttribute> productAttributeList = productAttributeService.getList(cid, type, pageSize, pageNum);
		return CommonResult.success(CommonPage.restPage(productAttributeList));
	}
	
	@ApiOperation("添加商品属性信息")
	@PostMapping("/create")
	@ResponseBody
	public CommonResult<?> create(@Validated @RequestBody PmsProductAttributeParam productAttributeParam) {
		int count = productAttributeService.create(productAttributeParam);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
	@ApiOperation("修改商品属性信息")
	@PostMapping("/update/{id}")
	@ResponseBody
	public CommonResult<?> update(@PathVariable Long id, @Validated @RequestBody PmsProductAttributeParam productAttributeParam) {
		int count = productAttributeService.update(id, productAttributeParam);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
	@ApiOperation("查询单个商品属性")
	@GetMapping("/{id}")
	@ResponseBody
	public CommonResult<PmsProductAttribute> getItem(@PathVariable Long id) {
		PmsProductAttribute productAttribute = productAttributeService.getItem(id);
		return CommonResult.success(productAttribute);
	}
	
	
	@ApiOperation("批量删除商品属性")
	@PostMapping("/delete")
	@ResponseBody
	public CommonResult<?> delete(@RequestParam("ids") List<Long> ids) {
		int count = productAttributeService.delete(ids);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
	@ApiOperation("根据商品分类ID 查询 商品属性及商品属性分类")
	@GetMapping("/attrInfo/{productCategoryId}")
	@ResponseBody
	public CommonResult<List<ProductAttrInfo>> getAttrInfo(@PathVariable Long productCategoryId) {
		List<ProductAttrInfo> productAttrInfoList = productAttributeService.getAttrInfo(productCategoryId);
		return CommonResult.success(productAttrInfoList);
	}
	
}
