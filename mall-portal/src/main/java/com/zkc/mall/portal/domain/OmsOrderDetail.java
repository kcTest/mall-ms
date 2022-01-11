package com.zkc.mall.portal.domain;

import com.zkc.mall.mbg.model.OmsOrder;
import com.zkc.mall.mbg.model.OmsOrderItem;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


public class OmsOrderDetail extends OmsOrder {
	
	@Getter
	@Setter
	private List<OmsOrderItem> orderItemList;
}
