package com.zkc.mall.portal.dao;

import com.zkc.mall.mbg.model.OmsOrderItem;
import com.zkc.mall.portal.domain.OmsOrderDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PortalOrderDao {
	
	OmsOrderDetail getDetail(@Param("orderId") Long orderId);
	
	int updateSkuStock(@Param("list") List<OmsOrderItem> orderItemList);
	
	List<OmsOrderDetail> getTimeOutOrders(@Param("minite") Integer minite);
	
	int updateOrderStatus(@Param("ids") List<Long> ids, @Param("status") int status);
	
	void releaseSkuStockLock(@Param("orderItemList") List<OmsOrderItem> orderItemList);
}
