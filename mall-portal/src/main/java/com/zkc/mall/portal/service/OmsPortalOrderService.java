package com.zkc.mall.portal.service;

import com.zkc.mall.portal.domain.ConfirmOrderResult;
import com.zkc.mall.portal.domain.OrderParam;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface OmsPortalOrderService {
	
	ConfirmOrderResult generateConfirmOrder(List<Long> cartIds);
	
	@Transactional
	Map<String, Object> generateOrder(OrderParam orderParam);
}
