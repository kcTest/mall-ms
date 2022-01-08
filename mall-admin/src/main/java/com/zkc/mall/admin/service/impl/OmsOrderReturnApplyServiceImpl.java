package com.zkc.mall.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.zkc.mall.admin.dao.OmsOrderReturnApplyDao;
import com.zkc.mall.admin.dto.OmsOrderReturnApplyQueryParam;
import com.zkc.mall.admin.dto.OmsUpdateStatusParam;
import com.zkc.mall.admin.service.OmsOrderReturnApplyService;
import com.zkc.mall.mbg.mapper.OmsOrderReturnApplyMapper;
import com.zkc.mall.mbg.model.OmsOrderReturnApply;
import com.zkc.mall.mbg.model.OmsOrderReturnApplyExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class OmsOrderReturnApplyServiceImpl implements OmsOrderReturnApplyService {
	
	@Resource
	private OmsOrderReturnApplyDao returnApplyDao;
	
	@Resource
	private OmsOrderReturnApplyMapper returnApplyMapper;
	
	@Override
	public List<OmsOrderReturnApply> list(OmsOrderReturnApplyQueryParam returnApplyParam, Integer pageSize, Integer pageNum) {
		PageHelper.startPage(pageNum, pageSize);
		return returnApplyDao.getList(returnApplyParam);
	}
	
	@Override
	public int delete(List<Long> ids) {
		OmsOrderReturnApplyExample example = new OmsOrderReturnApplyExample();
		example.createCriteria().andIdIn(ids);
		return returnApplyMapper.deleteByExample(example);
	}
	
	@Override
	public OmsOrderReturnApply getItem(Long id) {
		return returnApplyMapper.selectByPrimaryKey(id);
	}
	
	@Override
	public int updateStatus(Long id, OmsUpdateStatusParam statusParam) {
		Integer status = statusParam.getStatus();
		OmsOrderReturnApply returnApply = new OmsOrderReturnApply();
		switch (status) {
			case 1:
				//确认退货
				returnApply.setId(id);
				returnApply.setStatus(1);
				returnApply.setReturnAmount(statusParam.getReturnAmount());
				returnApply.setCompanyAddressId(statusParam.getCompanyAddressId());
				returnApply.setHandleTime(new Date());
				returnApply.setHandleMan(statusParam.getHandleMan());
				returnApply.setHandleNote(statusParam.getHandleNote());
				break;
			case 2:
				//完成退货
				returnApply.setId(id);
				returnApply.setStatus(2);
				returnApply.setReceiveTime(new Date());
				returnApply.setReceiveMan(statusParam.getReceiveMan());
				returnApply.setReceiveNote(statusParam.getReceiveNode());
				break;
			case 3:
				//拒绝退货
				returnApply.setId(id);
				returnApply.setStatus(3);
				returnApply.setHandleTime(new Date());
				returnApply.setHandleMan(statusParam.getHandleMan());
				returnApply.setHandleNote(statusParam.getHandleNote());
				break;
			default:
				return 0;
			
		}
		
		return returnApplyMapper.updateByPrimaryKeySelective(returnApply);
	}
}
