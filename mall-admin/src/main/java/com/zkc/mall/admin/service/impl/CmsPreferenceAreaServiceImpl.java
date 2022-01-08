package com.zkc.mall.admin.service.impl;

import com.zkc.mall.admin.service.CmsPreferenceAreaService;
import com.zkc.mall.mbg.mapper.CmsPrefrenceAreaMapper;
import com.zkc.mall.mbg.model.CmsPrefrenceArea;
import com.zkc.mall.mbg.model.CmsPrefrenceAreaExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CmsPreferenceAreaServiceImpl implements CmsPreferenceAreaService {
	
	@Resource
	private CmsPrefrenceAreaMapper cmsPrefrenceAreaMapper;
	
	@Override
	public List<CmsPrefrenceArea> listAll() {
		return cmsPrefrenceAreaMapper.selectByExample(new CmsPrefrenceAreaExample());
	}
}
