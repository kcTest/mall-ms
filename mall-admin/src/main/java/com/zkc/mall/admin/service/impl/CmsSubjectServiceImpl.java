package com.zkc.mall.admin.service.impl;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.zkc.mall.admin.service.CmsSubjectService;
import com.zkc.mall.mbg.mapper.CmsSubjectMapper;
import com.zkc.mall.mbg.model.CmsSubject;
import com.zkc.mall.mbg.model.CmsSubjectExample;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@Service
public class CmsSubjectServiceImpl implements CmsSubjectService {
	
	@Autowired
	private CmsSubjectMapper subjectMapper;
	
	@Override
	public List<CmsSubject> listAll() {
		return subjectMapper.selectByExample(new CmsSubjectExample());
	}
	
	@Override
	public List<CmsSubject> list(String keyword, Integer pageSize, Integer pageNum) {
		PageHelper.startPage(pageNum, pageSize);
		CmsSubjectExample example = new CmsSubjectExample();
		if (!StrUtil.isEmpty(keyword)) {
			example.createCriteria().andTitleLike("%" + keyword + "%");
		}
		
		return subjectMapper.selectByExample(example);
	}
}
