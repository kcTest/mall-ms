package com.zkc.mall.admin.service;

import com.zkc.mall.mbg.model.SmsHomeBrand;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SmsHomeBrandService {
	
	@Transactional
	int create(List<SmsHomeBrand> homeBrandList);
	
	int updateSort(Long id, Integer sort);
	
	int delete(List<Long> ids);
	
	int updateRecommendStatus(List<Long> ids, Integer recommendStatus);
	
	List<SmsHomeBrand> list(String brandName, Integer recommendStatus, Integer pageSize, Integer pageNum);
}
