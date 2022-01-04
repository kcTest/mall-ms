package com.zkc.mall.admin.service;

import com.zkc.mall.admin.dto.SmsCouponParam;
import com.zkc.mall.mbg.model.SmsCoupon;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SmsCouponService {
	
	@Transactional
	int create(SmsCouponParam flashPromotion);
	
	@Transactional
	int delete(Long id);
	
	@Transactional
	int update(Long id, SmsCouponParam couponParam);
	
	List<SmsCoupon> list(String name, Integer type, Integer pageSize, Integer pageNum);
	
	SmsCouponParam getItem(Long id);
}
