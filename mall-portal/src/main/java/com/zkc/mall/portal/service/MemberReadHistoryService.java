package com.zkc.mall.portal.service;

import com.zkc.mall.portal.domain.MemberReadHistory;

import java.util.List;

public interface MemberReadHistoryService {
	
	int create(MemberReadHistory memberReadHistory);
	
	int delete(List<String> ids);
	
	void clear();
	
	List<MemberReadHistory> list(Integer pageSize, Integer pageNum);
}
