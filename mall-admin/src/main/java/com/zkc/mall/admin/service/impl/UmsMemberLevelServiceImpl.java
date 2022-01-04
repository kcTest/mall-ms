package com.zkc.mall.admin.service.impl;

import com.zkc.mall.admin.service.UmsMemberLevelService;
import com.zkc.mall.mbg.mapper.UmsMemberLevelMapper;
import com.zkc.mall.mbg.model.UmsMemberLevel;
import com.zkc.mall.mbg.model.UmsMemberLevelExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UmsMemberLevelServiceImpl implements UmsMemberLevelService {
	
	@Resource
	private UmsMemberLevelMapper memberLevelMapper;
	
	@Override
	public List<UmsMemberLevel> list(Integer defaultStatus) {
		UmsMemberLevelExample umsMemberLevelExample = new UmsMemberLevelExample();
		umsMemberLevelExample.createCriteria().andDefaultStatusEqualTo(defaultStatus);
		return memberLevelMapper.selectByExample(umsMemberLevelExample);
	}
}
