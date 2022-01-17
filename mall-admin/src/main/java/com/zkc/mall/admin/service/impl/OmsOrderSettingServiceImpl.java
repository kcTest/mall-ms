package com.zkc.mall.admin.service.impl;

import com.zkc.mall.admin.service.OmsOrderSettingService;
import com.zkc.mall.mbg.mapper.OmsOrderSettingMapper;
import com.zkc.mall.mbg.model.OmsOrderSetting;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class OmsOrderSettingServiceImpl implements OmsOrderSettingService {
	
	@Autowired
	private OmsOrderSettingMapper orderSettingMapper;
	
	@Override
	public OmsOrderSetting getItem(Long id) {
		return orderSettingMapper.selectByPrimaryKey(id);
	}
	
	@Override
	public int update(Long id, OmsOrderSetting omsOrderSet) {
		omsOrderSet.setId(id);
		return orderSettingMapper.updateByPrimaryKey(omsOrderSet);
	}
}
