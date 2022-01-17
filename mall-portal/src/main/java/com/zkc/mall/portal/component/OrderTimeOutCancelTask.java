package com.zkc.mall.portal.component;

import com.zkc.mall.portal.service.OmsPortalOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class OrderTimeOutCancelTask {
	
	private Logger LOGGER = LoggerFactory.getLogger(OrderTimeOutCancelTask.class);
	
	@Autowired
	private OmsPortalOrderService PortalOrderService;
	
	/**
	 * 每十分钟扫描一次 扫描设定超时时间之前下的单 如果没有支付则取消订单
	 */
	@Scheduled(cron = "0 0/10 * ? * ?")
	private void cancelTimeOutOrder() {
		int count = PortalOrderService.cancelTimeOutOrder();
		LOGGER.info("取消订单 并根据sku编号释放锁定的库存，取消订单数量:{}", count);
	}
}
