package com.zkc.mall.portal.config;

import com.zkc.mall.portal.domain.QueueEnum;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 消息队列相关配置
 */
@Configuration
public class RabbitMqConfig {
	
	/**
	 * 订单消息的队列所绑定的交换机
	 */
	@Bean
	DirectExchange orderDirect() {
		return ExchangeBuilder.directExchange(QueueEnum.QUEUE_ORDER_CANCEL.getExchange())
				.durable(true)
				.build();
	}
	
	/**
	 * 延迟消息队列所绑定的交换机
	 */
	@Bean
	DirectExchange orderTtlDirect() {
		return ExchangeBuilder.directExchange(QueueEnum.QUEUE_TTL_ORDER_CANCEL.getExchange())
				.durable(true)
				.build();
	}
	
	/**
	 * 订单消息队列
	 */
	@Bean
	public Queue orderQueue() {
		return new Queue(QueueEnum.QUEUE_ORDER_CANCEL.getQueueName());
	}
	
	
	/**
	 * 延迟消息队列（死信队列）
	 */
	@Bean
	public Queue orderTtlQueue() {
		return QueueBuilder.durable(QueueEnum.QUEUE_TTL_ORDER_CANCEL.getQueueName())
				//到期后转发消息到指定交换机
				.withArgument("x-dead-letter-exchange", QueueEnum.QUEUE_TTL_ORDER_CANCEL.getExchange())
				.withArgument("x-dead-letter-routing-key", QueueEnum.QUEUE_TTL_ORDER_CANCEL.getRoutingKey())
				.build();
	}
	
	/**
	 * 将订单消息队列绑定到交换机
	 */
	@Bean
	Binding orderBinding(DirectExchange orderDirect, Queue orderQueue) {
		return BindingBuilder.bind(orderQueue).to(orderDirect).with(QueueEnum.QUEUE_ORDER_CANCEL.getRoutingKey());
	}
	
	/**
	 * 将延迟消息队列绑定到交换机
	 */
	@Bean
	Binding orderTtlBinding(DirectExchange orderTtlDirect, Queue orderTtlQueue) {
		return BindingBuilder.bind(orderTtlQueue).to(orderTtlDirect).with(QueueEnum.QUEUE_ORDER_CANCEL.getRoutingKey());
	}
	
	
}
