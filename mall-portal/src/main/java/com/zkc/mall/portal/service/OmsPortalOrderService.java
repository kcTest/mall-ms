package com.zkc.mall.portal.service;

import com.zkc.mall.portal.domain.ConfirmOrderResult;
import com.zkc.mall.portal.domain.OmsOrderDetail;
import com.zkc.mall.portal.domain.OrderParam;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface OmsPortalOrderService {
	
	ConfirmOrderResult generateConfirmOrder(List<Long> cartIds);
	
	@Transactional
	Map<String, Object> generateOrder(OrderParam orderParam);
	
	@Transactional
	int paySuccess(Long orderId, Integer payType);
	
	@Transactional
	int cancelTimeOutOrder();
	
	void sendDelayMessageCancelOrder(Long orderId);
	
	List<OmsOrderDetail> list(Integer status, Integer pageSize, Integer pageNum);
	
	OmsOrderDetail detail(Long orderId);
	
	/**
	 * 取消单个超时订单
	 */
	@Transactional
	void cancelOrder(Long orderId);
	
	void confirmReceiveOrder(Long orderId);
	
	void deleteOrder(Long orderId);
}
