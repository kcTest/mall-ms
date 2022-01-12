package com.zkc.mall.portal.service;

import com.zkc.mall.portal.domain.MemberProductCollection;

import java.util.List;

public interface MemberCollectionService {
	
	int add(MemberProductCollection productCollection);
	
	int delete(Long productId);
	
	List<MemberProductCollection> list(Integer pageSize, Integer pageNum);
	
	MemberProductCollection detail(Long productId);
}
