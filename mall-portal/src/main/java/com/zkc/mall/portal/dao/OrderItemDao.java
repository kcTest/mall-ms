package com.zkc.mall.portal.dao;

import com.zkc.mall.mbg.model.OmsOrderItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderItemDao {
	
	void insertList(@Param("list") List<OmsOrderItem> orderItemList);
}
