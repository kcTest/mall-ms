package com.zkc.mall.admin.service;

import com.zkc.mall.mbg.model.SmsHomeRecommendSubject;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SmsHomeRecommendSubjectService {
	
	@Transactional
	int create(List<SmsHomeRecommendSubject> homeRecommendSubject);
	
	int updateSort(Long id, Integer sort);
	
	int delete(List<Long> ids);
	
	int updateRecommendStatus(List<Long> ids, Integer recommendStatus);
	
	List<SmsHomeRecommendSubject> list(String subjectName, Integer recommendStatus, Integer pageSize, Integer pageNum);
}
