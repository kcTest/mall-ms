package com.zkc.mall.admin.service;

import com.zkc.mall.mbg.model.SmsHomeAdvertise;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SmsHomeAdvertiseService {
	
	@Transactional
	int create(SmsHomeAdvertise homeAdvertiseList);
	
	int updateSort(Long id, Integer sort);
	
	int delete(List<Long> ids);
	
	int updateStatus(Long id, Integer recommendStatus);
	
	SmsHomeAdvertise getItem(Long id);
	
	int update(Long id, SmsHomeAdvertise homeAdvertise);
	
	List<SmsHomeAdvertise> list(String name, Integer type, String endTime, Integer pageSize, Integer pageNum);
	
}
