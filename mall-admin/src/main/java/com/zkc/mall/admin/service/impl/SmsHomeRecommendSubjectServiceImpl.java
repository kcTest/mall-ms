package com.zkc.mall.admin.service.impl;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.zkc.mall.admin.service.SmsHomeRecommendSubjectService;
import com.zkc.mall.mbg.mapper.SmsHomeRecommendSubjectMapper;
import com.zkc.mall.mbg.model.SmsHomeRecommendSubject;
import com.zkc.mall.mbg.model.SmsHomeRecommendSubjectExample;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@Service
public class SmsHomeRecommendSubjectServiceImpl implements SmsHomeRecommendSubjectService {
	
	@Autowired
	private SmsHomeRecommendSubjectMapper homeRecommendSubjectMapper;
	
	@Override
	public int create(List<SmsHomeRecommendSubject> homeRecommendSubjectList) {
		for (SmsHomeRecommendSubject smsHomeRecommendSubject : homeRecommendSubjectList) {
			smsHomeRecommendSubject.setRecommendStatus(1);
			smsHomeRecommendSubject.setSort(0);
			homeRecommendSubjectMapper.insert(smsHomeRecommendSubject);
		}
		return homeRecommendSubjectList.size();
	}
	
	@Override
	public int updateSort(Long id, Integer sort) {
		SmsHomeRecommendSubject homeRecommendSubject = new SmsHomeRecommendSubject();
		homeRecommendSubject.setId(id);
		homeRecommendSubject.setSort(sort);
		return homeRecommendSubjectMapper.updateByPrimaryKeySelective(homeRecommendSubject);
	}
	
	@Override
	public int delete(List<Long> ids) {
		SmsHomeRecommendSubjectExample smsHomeRecommendSubjectExample = new SmsHomeRecommendSubjectExample();
		smsHomeRecommendSubjectExample.createCriteria().andIdIn(ids);
		return homeRecommendSubjectMapper.deleteByExample(smsHomeRecommendSubjectExample);
	}
	
	@Override
	public int updateRecommendStatus(List<Long> ids, Integer recommendStatus) {
		
		SmsHomeRecommendSubject homeRecommendSubject = new SmsHomeRecommendSubject();
		homeRecommendSubject.setRecommendStatus(recommendStatus);
		
		SmsHomeRecommendSubjectExample smsHomeRecommendSubjectExample = new SmsHomeRecommendSubjectExample();
		smsHomeRecommendSubjectExample.createCriteria().andIdIn(ids);
		return homeRecommendSubjectMapper.updateByExample(homeRecommendSubject, smsHomeRecommendSubjectExample);
	}
	
	@Override
	public List<SmsHomeRecommendSubject> list(String subjectName, Integer recommendStatus, Integer pageSize, Integer pageNum) {
		PageHelper.startPage(pageNum, pageSize);
		SmsHomeRecommendSubjectExample example = new SmsHomeRecommendSubjectExample();
		SmsHomeRecommendSubjectExample.Criteria criteria = example.createCriteria();
		if (!StrUtil.isEmpty(subjectName)) {
			criteria.andSubjectNameLike("%" + subjectName + "%");
		}
		if (recommendStatus != null) {
			criteria.andRecommendStatusEqualTo(recommendStatus);
		}
		example.setOrderByClause("sort desc");
		return homeRecommendSubjectMapper.selectByExample(example);
	}
	
}
