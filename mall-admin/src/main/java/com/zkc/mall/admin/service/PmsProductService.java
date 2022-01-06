package com.zkc.mall.admin.service;

import com.zkc.mall.admin.dto.PmsProductParam;
import com.zkc.mall.admin.dto.PmsProductQueryParam;
import com.zkc.mall.admin.dto.PmsProductResult;
import com.zkc.mall.mbg.model.PmsProduct;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PmsProductService {
	
	@Transactional
	int create(PmsProductParam productParam);
	
	PmsProductResult getUpdateInfo(Long pid);
	
	@Transactional
	int update(Long id, PmsProductParam productParam);
	
	List<PmsProduct> list(PmsProductQueryParam productQueryParam, Integer pageSize, Integer pageNum);
	
	List<PmsProduct> getList(String keyword);
	
	@Transactional
	int updateVerifyStatus(List<Long> ids, Integer verifyStatus, String detail);
	
	int updatePublishStatus(List<Long> ids, Integer publishStatus);
	
	int updateRecommendStatus(List<Long> ids, Integer recommendStatus);
	
	int updateNewStatus(List<Long> ids, Integer newStatus);
	
	int updateDeleteStatus(List<Long> ids, Integer deleteStatus);
}
