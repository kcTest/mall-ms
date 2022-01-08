package com.zkc.mall.admin.service;

import com.zkc.mall.mbg.model.CmsSubject;

import java.util.List;

public interface CmsSubjectService {
	
	List<CmsSubject> listAll();
	
	List<CmsSubject> list(String keyword, Integer pageSize, Integer pageNum);
}
