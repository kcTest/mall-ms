package com.zkc.mall.admin.service.impl;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.zkc.mall.admin.service.SmsHomeBrandService;
import com.zkc.mall.mbg.mapper.SmsHomeBrandMapper;
import com.zkc.mall.mbg.model.SmsHomeBrand;
import com.zkc.mall.mbg.model.SmsHomeBrandExample;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@Service
public class SmsHomeBrandServiceImpl implements SmsHomeBrandService {
	
	@Autowired
	private SmsHomeBrandMapper homeBrandMapper;
	
	@Override
	public int create(List<SmsHomeBrand> homeBrandList) {
		for (SmsHomeBrand smsHomeBrand : homeBrandList) {
			smsHomeBrand.setRecommendStatus(1);
			smsHomeBrand.setSort(0);
			homeBrandMapper.insert(smsHomeBrand);
		}
		return homeBrandList.size();
	}
	
	@Override
	public int updateSort(Long id, Integer sort) {
		SmsHomeBrand homeBrand = new SmsHomeBrand();
		homeBrand.setId(id);
		homeBrand.setSort(sort);
		return homeBrandMapper.updateByPrimaryKeySelective(homeBrand);
	}
	
	@Override
	public int delete(List<Long> ids) {
		SmsHomeBrandExample smsHomeBrandExample = new SmsHomeBrandExample();
		smsHomeBrandExample.createCriteria().andIdIn(ids);
		return homeBrandMapper.deleteByExample(smsHomeBrandExample);
	}
	
	@Override
	public int updateRecommendStatus(List<Long> ids, Integer recommendStatus) {
		
		SmsHomeBrand homeBrand = new SmsHomeBrand();
		homeBrand.setRecommendStatus(recommendStatus);
		
		SmsHomeBrandExample smsHomeBrandExample = new SmsHomeBrandExample();
		smsHomeBrandExample.createCriteria().andIdIn(ids);
		return homeBrandMapper.updateByExample(homeBrand, smsHomeBrandExample);
	}
	
	@Override
	public List<SmsHomeBrand> list(String ProductName, Integer recommendStatus, Integer pageSize, Integer pageNum) {
		PageHelper.startPage(pageNum, pageSize);
		SmsHomeBrandExample example = new SmsHomeBrandExample();
		SmsHomeBrandExample.Criteria criteria = example.createCriteria();
		if (!StrUtil.isEmpty(ProductName)) {
			criteria.andBrandNameLike("%" + ProductName + "%");
		}
		if (recommendStatus != null) {
			criteria.andRecommendStatusEqualTo(recommendStatus);
		}
		example.setOrderByClause("sort desc");
		return homeBrandMapper.selectByExample(example);
	}
	
}
