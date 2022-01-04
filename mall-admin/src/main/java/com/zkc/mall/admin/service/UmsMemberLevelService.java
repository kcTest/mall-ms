package com.zkc.mall.admin.service;

import com.zkc.mall.mbg.model.UmsMemberLevel;

import java.util.List;

public interface UmsMemberLevelService {
	
	List<UmsMemberLevel> list(Integer defaultStatus);
}
