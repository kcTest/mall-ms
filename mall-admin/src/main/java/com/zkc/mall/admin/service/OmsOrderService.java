package com.zkc.mall.admin.service;

import com.zkc.mall.admin.dto.*;
import com.zkc.mall.mbg.model.OmsOrder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OmsOrderService {
	
	List<OmsOrder> list(OmsOrderQueryParam orderQueryParam, Integer pageSize, Integer pageNum);
	
	@Transactional
	int delivery(List<OmsOrderDeliveryParam> deliveryParamList);
	
	@Transactional
	int close(List<Long> ids, String note);
	
	int delete(List<Long> ids);
	
	OmsOrderDetail detail(Long id);
	
	@Transactional
	int updateReceiverInfo(OmsReceiverInfoParam receiverInfoParam);
	
	@Transactional
	int updateMoneyInfo(OmsMoneyInfoParam moneyInfoParam);
	
	@Transactional
	int updateNode(Long id, String note, Integer status);
}
