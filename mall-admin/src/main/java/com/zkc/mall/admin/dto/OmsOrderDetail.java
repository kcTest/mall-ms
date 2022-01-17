package com.zkc.mall.admin.dto;

import com.zkc.mall.mbg.model.OmsOrder;
import com.zkc.mall.mbg.model.OmsOrderItem;
import com.zkc.mall.mbg.model.OmsOrderOperateHistory;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class OmsOrderDetail extends OmsOrder {
	
	@Getter
	@Setter
	@Schema(description ="订单商品列表")
	private List<OmsOrderItem> orderItemList;
	
	@Getter
	@Setter
	@Schema(description ="订单操作记录")
	private List<OmsOrderOperateHistory> historyList;
}
