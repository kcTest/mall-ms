package com.zkc.mall.admin.service.impl;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.zkc.mall.admin.service.SmsCouponHistoryService;
import com.zkc.mall.mbg.mapper.SmsCouponHistoryMapper;
import com.zkc.mall.mbg.model.*;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@Service
public class SmsCouponHistoryServiceImpl implements SmsCouponHistoryService {
	
	@Autowired
	private SmsCouponHistoryMapper couponHistoryMapper;
	
	@Override
	public List<SmsCouponHistory> list(Long couponId, Integer useStatus, String orderSn, Integer pageSize, Integer pageNum) {
		PageHelper.startPage(pageNum, pageSize);
		SmsCouponHistoryExample example = new SmsCouponHistoryExample();
		if (couponId != null) {
			example.createCriteria().andCouponIdEqualTo(couponId);
		}
		if (useStatus != null) {
			example.createCriteria().andUseStatusEqualTo(useStatus);
		}
		if (StrUtil.isNotEmpty(orderSn)) {
			example.createCriteria().andOrderSnLike("%" + orderSn + "%");
		}
		
		return couponHistoryMapper.selectByExample(example);
	}
}
