package com.zkc.mall.admin.controller;

import com.zkc.mall.admin.service.PmsSkuStockService;
import com.zkc.mall.common.api.CommonResult;
import com.zkc.mall.mbg.model.PmsSkuStock;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@Tag(name = "PmsSkuStockController", description = "sku商品库存管理")

@RestController
@RequestMapping(value = "/sku")
public class PmsSkuStockController {
	
	@Autowired
	private PmsSkuStockService skuStockService;
	
	@Operation(summary = "根据商品编号和货号查询sku库存")
	@GetMapping("/{pid}")
	@ResponseBody
	public CommonResult<List<PmsSkuStock>> getList(@PathVariable Long pid, @RequestParam(value = "keyword", required = false) String keyword) {
		List<PmsSkuStock> skuStockList = skuStockService.getList(pid, keyword);
		return CommonResult.success(skuStockList);
	}
	
	@Operation(summary ="批量更新库存信息")
	@PostMapping("/update")
	@ResponseBody
	public CommonResult<?> update(@RequestBody List<PmsSkuStock> skuStockList) {
		int count = skuStockService.update(skuStockList);
		return count > 0 ? CommonResult.success(count) : CommonResult.failed();
	}
	
}
