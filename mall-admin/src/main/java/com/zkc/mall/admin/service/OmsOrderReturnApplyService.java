package com.zkc.mall.admin.service;

import com.zkc.mall.admin.dto.OmsOrderReturnApplyQueryParam;
import com.zkc.mall.admin.dto.OmsUpdateStatusParam;
import com.zkc.mall.mbg.model.OmsOrderReturnApply;

import java.util.List;

public interface OmsOrderReturnApplyService {
	
	List<OmsOrderReturnApply> list(OmsOrderReturnApplyQueryParam returnApplyParam, Integer pageSize, Integer pageNum);
	
	int delete(List<Long> ids);
	
	OmsOrderReturnApply getItem(Long id);
	
	int updateStatus(Long id, OmsUpdateStatusParam statusParam);
}
