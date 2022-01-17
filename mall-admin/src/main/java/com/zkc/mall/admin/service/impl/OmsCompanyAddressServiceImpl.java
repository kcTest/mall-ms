package com.zkc.mall.admin.service.impl;

import com.zkc.mall.admin.service.OmsCompanyAddressService;
import com.zkc.mall.mbg.mapper.OmsCompanyAddressMapper;
import com.zkc.mall.mbg.model.OmsCompanyAddress;
import com.zkc.mall.mbg.model.OmsCompanyAddressExample;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@Service
public class OmsCompanyAddressServiceImpl implements OmsCompanyAddressService {
	
	@Autowired
	private OmsCompanyAddressMapper companyAddressMapper;
	
	@Override
	public List<OmsCompanyAddress> list() {
		return companyAddressMapper.selectByExample(new OmsCompanyAddressExample());
	}
}
