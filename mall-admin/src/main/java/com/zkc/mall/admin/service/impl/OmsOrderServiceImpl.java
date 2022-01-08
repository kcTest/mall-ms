package com.zkc.mall.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.zkc.mall.admin.dao.OmsOrderDao;
import com.zkc.mall.admin.dao.OmsOrderOperateHistoryDao;
import com.zkc.mall.admin.dto.*;
import com.zkc.mall.admin.service.OmsOrderService;
import com.zkc.mall.mbg.mapper.OmsOrderMapper;
import com.zkc.mall.mbg.mapper.OmsOrderOperateHistoryMapper;
import com.zkc.mall.mbg.model.OmsOrder;
import com.zkc.mall.mbg.model.OmsOrderExample;
import com.zkc.mall.mbg.model.OmsOrderOperateHistory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OmsOrderServiceImpl implements OmsOrderService {
	
	@Resource
	private OmsOrderMapper orderMapper;
	@Resource
	private OmsOrderDao orderDao;
	@Resource
	private OmsOrderOperateHistoryDao orderOperateHistoryDao;
	@Resource
	private OmsOrderOperateHistoryMapper orderOperateHistoryMapper;
	
	@Override
	public List<OmsOrder> list(OmsOrderQueryParam orderQueryParam, Integer pageSize, Integer pageNum) {
		PageHelper.startPage(pageNum, pageSize);
		return orderDao.getList(orderQueryParam);
	}
	
	@Override
	public int delivery(List<OmsOrderDeliveryParam> deliveryParamList) {
		int count = orderDao.delivery(deliveryParamList);
		//操作记录
		List<OmsOrderOperateHistory> historyList = deliveryParamList.stream().map(d -> {
			OmsOrderOperateHistory operateHistory = new OmsOrderOperateHistory();
			operateHistory.setOrderId(d.getOrderId());
			operateHistory.setCreateTime(new Date());
			operateHistory.setOperateMan("TODO");
			operateHistory.setOrderStatus(2);
			operateHistory.setNote("完成发货");
			return operateHistory;
		}).collect(Collectors.toList());
		
		orderOperateHistoryDao.insertList(historyList);
		return count;
	}
	
	@Override
	public int close(List<Long> ids, String note) {
		OmsOrder order = new OmsOrder();
		order.setStatus(4);
		OmsOrderExample example = new OmsOrderExample();
		example.createCriteria().andIdIn(ids);
		int count = orderMapper.updateByExampleSelective(order, example);
		
		//操作记录
		List<OmsOrderOperateHistory> historyList = ids.stream().map(id -> {
			OmsOrderOperateHistory operateHistory = new OmsOrderOperateHistory();
			operateHistory.setOrderId(id);
			operateHistory.setCreateTime(new Date());
			operateHistory.setOperateMan("TODO");
			operateHistory.setOrderStatus(4);
			operateHistory.setNote("订单关闭:" + note);
			return operateHistory;
		}).collect(Collectors.toList());
		orderOperateHistoryDao.insertList(historyList);
		return count;
	}
	
	@Override
	public int delete(List<Long> ids) {
		OmsOrder order = new OmsOrder();
		order.setDeleteStatus(1);
		OmsOrderExample example = new OmsOrderExample();
		example.createCriteria().andIdIn(ids).andDeleteStatusEqualTo(0);
		return orderMapper.updateByExampleSelective(order, example);
	}
	
	@Override
	public OmsOrderDetail detail(Long id) {
		return orderDao.detail(id);
	}
	
	@Override
	public int updateReceiverInfo(OmsReceiverInfoParam receiverInfoParam) {
		OmsOrder order = new OmsOrder();
		order.setId(receiverInfoParam.getOrderId());
		order.setReceiverName(receiverInfoParam.getReceiverName());
		order.setReceiverPhone(receiverInfoParam.getReceiverPhone());
		order.setReceiverPostCode(receiverInfoParam.getReceiverPostCode());
		order.setReceiverDetailAddress(receiverInfoParam.getReceiverDetailAddress());
		order.setReceiverProvince(receiverInfoParam.getReceiverProvince());
		order.setReceiverCity(receiverInfoParam.getReceiverCity());
		order.setReceiverRegion(receiverInfoParam.getReceiverRegion());
		order.setModifyTime(new Date());
		int count = orderMapper.updateByPrimaryKeySelective(order);
		
		OmsOrderOperateHistory history = new OmsOrderOperateHistory();
		history.setOrderId(receiverInfoParam.getOrderId());
		history.setCreateTime(new Date());
		history.setOperateMan("TODO");
		history.setOrderStatus(receiverInfoParam.getStatus());
		history.setNote("修改收货人信息");
		orderOperateHistoryMapper.insertSelective(history);
		
		return count;
	}
	
	@Override
	public int updateMoneyInfo(OmsMoneyInfoParam moneyInfoParam) {
		OmsOrder order = new OmsOrder();
		order.setId(moneyInfoParam.getOrderId());
		order.setFreightAmount(moneyInfoParam.getFreightAmount());
		order.setDiscountAmount(moneyInfoParam.getDiscountAmount());
		order.setModifyTime(new Date());
		int count = orderMapper.updateByPrimaryKeySelective(order);
		
		OmsOrderOperateHistory history = new OmsOrderOperateHistory();
		history.setOrderId(moneyInfoParam.getOrderId());
		history.setCreateTime(new Date());
		history.setOperateMan("TODO");
		history.setOrderStatus(moneyInfoParam.getStatus());
		history.setNote("修改费用信息");
		orderOperateHistoryMapper.insertSelective(history);
		
		return count;
	}
	
	@Override
	public int updateNode(Long id, String note, Integer status) {
		OmsOrder order = new OmsOrder();
		order.setId(id);
		order.setNote(note);
		order.setModifyTime(new Date());
		int count = orderMapper.updateByPrimaryKeySelective(order);
		
		OmsOrderOperateHistory history = new OmsOrderOperateHistory();
		history.setOrderId(id);
		history.setCreateTime(new Date());
		history.setOperateMan("TODO");
		history.setOrderStatus(status);
		history.setNote("修改备注信息" + note);
		orderOperateHistoryMapper.insert(history);
		return count;
	}
}
