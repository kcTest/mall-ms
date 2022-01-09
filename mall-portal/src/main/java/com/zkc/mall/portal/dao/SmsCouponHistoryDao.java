package com.zkc.mall.portal.dao;

import com.zkc.mall.mbg.model.SmsCoupon;
import com.zkc.mall.portal.domain.SmsCouponHistoryDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SmsCouponHistoryDao {
	
	List<SmsCoupon> getCounponList(@Param("id") Long id, @Param("useStatus") Integer useStatus);
	
	List<SmsCouponHistoryDetail> getDetailList(@Param("id") Long id);
}
