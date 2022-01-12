package com.zkc.mall.portal.service;

import com.zkc.mall.portal.domain.MemberBrandAttention;

import java.util.List;

public interface MemberAttentionService {
	
	int add(MemberBrandAttention productCollection);
	
	int delete(Long brandId);
	
	List<MemberBrandAttention> list(Integer pageSize, Integer pageNum);
	
	MemberBrandAttention detail(Long brandId);
	
	void clear();
}
