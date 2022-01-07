package com.zkc.mall.admin.service;

import com.zkc.mall.mbg.model.OmsOrderReturnReason;

import java.util.List;

public interface OmsOrderReturnReasonService {
	
	int create(OmsOrderReturnReason returnReason);
	
	int update(Long id, OmsOrderReturnReason returnReason);
	
	int delete(List<Long> ids);
	
	List<OmsOrderReturnReason> list(Integer pageSize, Integer pageNum);
	
	OmsOrderReturnReason getItem(Long id);
	
	int updateStatus(List<Long> ids, Integer status);
}
