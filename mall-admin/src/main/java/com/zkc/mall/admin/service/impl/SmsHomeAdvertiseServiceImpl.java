package com.zkc.mall.admin.service.impl;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.zkc.mall.admin.service.SmsHomeAdvertiseService;
import com.zkc.mall.mbg.mapper.SmsHomeAdvertiseMapper;
import com.zkc.mall.mbg.model.SmsHomeAdvertise;
import com.zkc.mall.mbg.model.SmsHomeAdvertiseExample;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.Date;
import java.util.List;

@Service
public class SmsHomeAdvertiseServiceImpl implements SmsHomeAdvertiseService {
	
	@Autowired
	private SmsHomeAdvertiseMapper homeAdvertiseMapper;
	
	@Override
	public int create(SmsHomeAdvertise homeAdvertise) {
		homeAdvertise.setClickCount(0);
		homeAdvertise.setOrderCount(0);
		return homeAdvertiseMapper.insert(homeAdvertise);
	}
	
	@Override
	public int updateSort(Long id, Integer sort) {
		SmsHomeAdvertise homeAdvertise = new SmsHomeAdvertise();
		homeAdvertise.setId(id);
		homeAdvertise.setSort(sort);
		return homeAdvertiseMapper.updateByPrimaryKeySelective(homeAdvertise);
	}
	
	@Override
	public int delete(List<Long> ids) {
		SmsHomeAdvertiseExample smsHomeAdvertiseExample = new SmsHomeAdvertiseExample();
		smsHomeAdvertiseExample.createCriteria().andIdIn(ids);
		return homeAdvertiseMapper.deleteByExample(smsHomeAdvertiseExample);
	}
	
	@Override
	public int updateStatus(Long id, Integer status) {
		SmsHomeAdvertise homeAdvertise = new SmsHomeAdvertise();
		homeAdvertise.setId(id);
		homeAdvertise.setStatus(status);
		return homeAdvertiseMapper.updateByPrimaryKeySelective(homeAdvertise);
	}
	
	@Override
	public SmsHomeAdvertise getItem(Long id) {
		return homeAdvertiseMapper.selectByPrimaryKey(id);
	}
	
	@Override
	public int update(Long id, SmsHomeAdvertise homeAdvertise) {
		homeAdvertise.setId(id);
		return homeAdvertiseMapper.updateByPrimaryKeySelective(homeAdvertise);
	}
	
	@Override
	public List<SmsHomeAdvertise> list(String name, Integer type, String endTime, Integer pageSize, Integer pageNum) {
		PageHelper.startPage(pageNum, pageSize);
		SmsHomeAdvertiseExample example = new SmsHomeAdvertiseExample();
		SmsHomeAdvertiseExample.Criteria criteria = example.createCriteria();
		if (!StrUtil.isEmpty(name)) {
			criteria.andNameLike("%" + name + "%");
		}
		if (type != null) {
			criteria.andTypeEqualTo(type);
		}
		if (!StrUtil.isEmpty(endTime)) {
			Date start = DateUtil.parse(endTime);
			Date end = DateUtil.offset(start, DateField.DAY_OF_YEAR, 1);
			criteria.andEndTimeGreaterThanOrEqualTo(start);
			criteria.andEndTimeLessThan(end);
		}
		example.setOrderByClause("sort desc");
		return homeAdvertiseMapper.selectByExample(example);
	}
	
}
