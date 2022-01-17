package com.zkc.mall.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.zkc.mall.admin.service.OmsOrderReturnReasonService;
import com.zkc.mall.mbg.mapper.OmsOrderReturnReasonMapper;
import com.zkc.mall.mbg.model.OmsOrderReturnReason;
import com.zkc.mall.mbg.model.OmsOrderReturnReasonExample;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.Date;
import java.util.List;

@Service
public class OmsOrderReturnReasonServiceImpl implements OmsOrderReturnReasonService {
	
	@Autowired
	private OmsOrderReturnReasonMapper returnReasonMapper;
	
	@Override
	public int create(OmsOrderReturnReason returnReason) {
		returnReason.setCreateTime(new Date());
		return returnReasonMapper.insert(returnReason);
	}
	
	@Override
	public int update(Long id, OmsOrderReturnReason returnReason) {
		returnReason.setId(id);
		return returnReasonMapper.updateByPrimaryKeySelective(returnReason);
	}
	
	@Override
	public int delete(List<Long> ids) {
		OmsOrderReturnReasonExample example = new OmsOrderReturnReasonExample();
		example.createCriteria().andIdIn(ids);
		return returnReasonMapper.deleteByExample(example);
	}
	
	@Override
	public List<OmsOrderReturnReason> list(Integer pageSize, Integer pageNum) {
		PageHelper.startPage(pageNum, pageSize);
		OmsOrderReturnReasonExample example = new OmsOrderReturnReasonExample();
		example.setOrderByClause("sort desc");
		return returnReasonMapper.selectByExample(example);
	}
	
	@Override
	public OmsOrderReturnReason getItem(Long id) {
		return returnReasonMapper.selectByPrimaryKey(id);
	}
	
	@Override
	public int updateStatus(List<Long> ids, Integer status) {
		if (!status.equals(0) && !status.equals(1)) {
			return 0;
		}
		OmsOrderReturnReasonExample example = new OmsOrderReturnReasonExample();
		example.createCriteria().andIdIn(ids);
		OmsOrderReturnReason returnReason = new OmsOrderReturnReason();
		returnReason.setStatus(status);
		return returnReasonMapper.updateByExampleSelective(returnReason, example);
	}
}
