package com.zkc.mall.portal.domain;

import lombok.Getter;

/**
 * 消息队列配置
 */
@Getter
public enum QueueEnum {
	
	/**
	 * 消息通知队列
	 */
	QUEUE_ORDER_CANCEL("mall.order.direct", "mall.order.cancel", "mall.order.cancel"),
	/**
	 * 消息通知TTL队列
	 */
	QUEUE_TTL_ORDER_CANCEL("mall.order.direct.ttl", "mall.order.cancel.ttl", "mall.order.cancel.ttl");
	
	
	/**
	 * 交换机名称
	 */
	private String exchange;
	/**
	 * 队列名称
	 */
	private String name;
	/**
	 * 绑定键
	 */
	private String routingKey;
	
	QueueEnum(String exchange, String name, String routingKey) {
		this.exchange = exchange;
		this.name = name;
		this.routingKey = routingKey;
	}
}
