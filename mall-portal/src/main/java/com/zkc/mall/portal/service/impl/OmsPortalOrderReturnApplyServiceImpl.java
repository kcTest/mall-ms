package com.zkc.mall.portal.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.zkc.mall.mbg.mapper.OmsOrderReturnApplyMapper;
import com.zkc.mall.mbg.model.OmsOrderReturnApply;
import com.zkc.mall.portal.domain.OmsOrderReturnApplyParam;
import com.zkc.mall.portal.service.OmsPortalOrderReturnApplyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class OmsPortalOrderReturnApplyServiceImpl implements OmsPortalOrderReturnApplyService {
	
	@Resource
	private OmsOrderReturnApplyMapper returnApplyMapper;
	
	@Override
	public int create(OmsOrderReturnApplyParam returnApplyParam) {
		OmsOrderReturnApply returnApply = new OmsOrderReturnApply();
		BeanUtil.copyProperties(returnApplyParam, returnApply);
		returnApply.setCreateTime(new Date());
		returnApply.setStatus(0);
		return returnApplyMapper.insertSelective(returnApply);
	}
}
