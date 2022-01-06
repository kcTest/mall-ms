package com.zkc.mall.admin.service;

import com.zkc.mall.admin.dto.SmsCouponParam;
import com.zkc.mall.mbg.model.SmsCoupon;
import com.zkc.mall.mbg.model.SmsCouponHistory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SmsCouponHistoryService {
	
	List<SmsCouponHistory> list(Long couponId, Integer useStatus, String orderSn, Integer pageSize, Integer pageNum);
}
