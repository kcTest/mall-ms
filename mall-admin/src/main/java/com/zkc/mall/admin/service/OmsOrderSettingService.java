package com.zkc.mall.admin.service;

import com.zkc.mall.mbg.model.OmsOrderSetting;

public interface OmsOrderSettingService {
	
	OmsOrderSetting getItem(Long id);
	
	int update(Long id, OmsOrderSetting omsOrderSet);
}
