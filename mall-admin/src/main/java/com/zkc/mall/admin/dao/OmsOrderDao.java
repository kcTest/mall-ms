package com.zkc.mall.admin.dao;

import com.zkc.mall.admin.dto.OmsOrderDeliveryParam;
import com.zkc.mall.admin.dto.OmsOrderDetail;
import com.zkc.mall.admin.dto.OmsOrderQueryParam;
import com.zkc.mall.mbg.model.OmsOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OmsOrderDao {
	
	List<OmsOrder> getList(@Param("orderQueryParam") OmsOrderQueryParam orderQueryParam);
	
	int delivery(@Param("list") List<OmsOrderDeliveryParam> deliveryParamList);
	
	OmsOrderDetail detail(@Param("id") Long id);
}
