package com.zkc.mall.admin.service.impl;

import com.zkc.mall.admin.service.UmsMemberLevelService;
import com.zkc.mall.mbg.mapper.UmsMemberLevelMapper;
import com.zkc.mall.mbg.model.UmsMemberLevel;
import com.zkc.mall.mbg.model.UmsMemberLevelExample;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@Service
public class UmsMemberLevelServiceImpl implements UmsMemberLevelService {
	
	@Autowired
	private UmsMemberLevelMapper memberLevelMapper;
	
	@Override
	public List<UmsMemberLevel> list(Integer defaultStatus) {
		UmsMemberLevelExample umsMemberLevelExample = new UmsMemberLevelExample();
		umsMemberLevelExample.createCriteria().andDefaultStatusEqualTo(defaultStatus);
		return memberLevelMapper.selectByExample(umsMemberLevelExample);
	}
}
