package com.zkc.mall.admin.service;

import com.zkc.mall.mbg.model.SmsHomeNewProduct;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SmsHomeNewProductService {
	
	@Transactional
	int create(List<SmsHomeNewProduct> homeNewProductList);
	
	int updateSort(Long id, Integer sort);
	
	int delete(List<Long> ids);
	
	int updateRecommendStatus(List<Long> ids, Integer recommendStatus);
	
	List<SmsHomeNewProduct> list(String subjectName, Integer recommendStatus, Integer pageSize, Integer pageNum);
}
