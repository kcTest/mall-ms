package com.zkc.mall.portal.component;

import com.zkc.mall.portal.domain.QueueEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.stereotype.Component;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * 发送订单取消消息
 */
@Component
public class CancelOrderSender {
	
	private static Logger LOGGER = LoggerFactory.getLogger(CancelOrderSender.class);
	
	@Autowired
	private AmqpTemplate amqpTemplate;
	
	public void sendMessage(Long orderId, final long delayTimes) {
		//给延迟队列发送消息
		amqpTemplate.convertAndSend(
				QueueEnum.QUEUE_TTL_ORDER_CANCEL.getExchange(),
				QueueEnum.QUEUE_TTL_ORDER_CANCEL.getRoutingKey(),
				orderId,
				new MessagePostProcessor() {
					@Override
					public Message postProcessMessage(Message message) throws AmqpException {
						//给延迟消息设置延迟毫秒值
						message.getMessageProperties().setExpiration(String.valueOf(delayTimes));
						return message;
					}
				});
		
		LOGGER.info("send orderId:{}", orderId);
	}
}
