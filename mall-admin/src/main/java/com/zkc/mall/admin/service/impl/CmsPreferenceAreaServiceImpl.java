package com.zkc.mall.admin.service.impl;

import com.zkc.mall.admin.service.CmsPreferenceAreaService;
import com.zkc.mall.mbg.mapper.CmsPreferenceAreaMapper;
import com.zkc.mall.mbg.model.CmsPreferenceArea;
import com.zkc.mall.mbg.model.CmsPreferenceAreaExample;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@Service
public class CmsPreferenceAreaServiceImpl implements CmsPreferenceAreaService {
	
	@Autowired
	private CmsPreferenceAreaMapper cmsPreferenceAreaMapper;
	
	@Override
	public List<CmsPreferenceArea> listAll() {
		return cmsPreferenceAreaMapper.selectByExample(new CmsPreferenceAreaExample());
	}
}
