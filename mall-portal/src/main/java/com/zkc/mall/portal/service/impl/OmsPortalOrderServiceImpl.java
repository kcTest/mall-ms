package com.zkc.mall.portal.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.github.pagehelper.PageHelper;
import com.zkc.mall.common.exception.Asserts;
import com.zkc.mall.common.service.RedisService;
import com.zkc.mall.mbg.mapper.*;
import com.zkc.mall.mbg.model.*;
import com.zkc.mall.portal.component.CancelOrderSender;
import com.zkc.mall.portal.dao.OrderItemDao;
import com.zkc.mall.portal.dao.PortalOrderDao;
import com.zkc.mall.portal.domain.*;
import com.zkc.mall.portal.service.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OmsPortalOrderServiceImpl implements OmsPortalOrderService {
	
	@Autowired
	private UmsMemberService memberService;
	@Autowired
	private UmsMemberCouponService memberCouponService;
	@Autowired
	private OmsCartItemService cartItemService;
	@Autowired
	private UmsMemberReceiveAddressService addressService;
	@Autowired
	private UmsMemberCouponService couponService;
	@Autowired
	private UmsIntegrationConsumeSettingMapper integrationConsumeSettingMapper;
	@Autowired
	private PmsSkuStockMapper skuStockMapper;
	@Autowired
	private OmsOrderSettingMapper orderSettingMapper;
	@Autowired
	private OmsOrderMapper orderMapper;
	@Autowired
	private OrderItemDao orderItemDao;
	@Autowired
	private OmsOrderItemMapper orderItemMapper;
	
	@Value("${redis.database}")
	private String REDIS_DATABASE;
	@Value("${redis.key.orderId}")
	private String REDIS_KEY_ORDER_ID;
	@Autowired
	private RedisService redisService;
	
	@Autowired
	private CancelOrderSender cancelOrderSender;
	
	@Autowired
	private PortalOrderDao portalOrderDao;
	
	@Autowired
	private SmsCouponHistoryMapper couponHistoryMapper;
	
	@Override
	public ConfirmOrderResult generateConfirmOrder(List<Long> cartIds) {
		ConfirmOrderResult result = new ConfirmOrderResult();
		
		//获取购物车信息
		UmsMember member = memberService.getCurrentMember();
		List<CartPromotionItem> cartPromotionItemList = cartItemService.listPromotion(cartIds);
		result.setCartPromotionItemList(cartPromotionItemList);
		
		//获取用户收货地址列表
		List<UmsMemberReceiveAddress> addressList = addressService.list();
		result.setMemberReceiveAddressList(addressList);
		
		//获取用户可用优惠券列表
		List<SmsCouponHistoryDetail> couponHistoryDetails = couponService.listCart(cartPromotionItemList, 1);
		result.setCouponHistoryDetailList(couponHistoryDetails);
		
		//获取用户积分
		result.setMemberIntegration(member.getIntegration());
		//获取积分使用规则
		UmsIntegrationConsumeSetting consumeSetting = integrationConsumeSettingMapper.selectByPrimaryKey(1L);
		result.setIntegrationConsumeSetting(consumeSetting);
		
		//计算总金额、活动优惠、应付金额
		ConfirmOrderResult.CalcAmount calcAmount = calcCartAmount(cartPromotionItemList);
		result.setCalAmount(calcAmount);
		
		return result;
	}
	
	@Override
	public Map<String, Object> generateOrder(OrderParam orderParam) {
		List<OmsOrderItem> orderItemList = new ArrayList<>();
		UmsMember member = memberService.getCurrentMember();
		List<CartPromotionItem> cartPromotionItemList = cartItemService.listPromotion(orderParam.getCartIds());
		for (CartPromotionItem cartPromotionItem : cartPromotionItemList) {
			//生成下单商品信息
			OmsOrderItem orderItem = new OmsOrderItem();
			orderItem.setProductId(cartPromotionItem.getProductId());
			orderItem.setProductName(cartPromotionItem.getProductName());
			orderItem.setProductPic(cartPromotionItem.getProductPic());
			orderItem.setProductAttr(cartPromotionItem.getProductAttr());
			orderItem.setProductBrand(cartPromotionItem.getProductBrand());
			orderItem.setProductSn(cartPromotionItem.getProductSn());
			orderItem.setProductPrice(cartPromotionItem.getPrice());
			orderItem.setProductQuantity(cartPromotionItem.getQuantity());
			orderItem.setProductSkuId(cartPromotionItem.getProductSkuId());
			orderItem.setProductSkuCode(cartPromotionItem.getProductSkuCode());
			orderItem.setProductCategoryId(cartPromotionItem.getProductCategoryId());
			orderItem.setPromotionAmount(cartPromotionItem.getReduceAmount());
			orderItem.setPromotionName(cartPromotionItem.getPromotionMessage());
			orderItem.setGiftIntegration(cartPromotionItem.getIntegration());
			orderItem.setGiftGrowth(cartPromotionItem.getGrowth());
			orderItemList.add(orderItem);
		}
		
		//检查库存
		if (!hasStock(cartPromotionItemList)) {
			Asserts.fail("库存不足，无法下单");
		}
		
		//判断是否使用优惠券
		if (orderParam.getCouponId() == null) {
			for (OmsOrderItem orderItem : orderItemList) {
				orderItem.setCouponAmount(new BigDecimal(0));
			}
		} else {
			SmsCouponHistoryDetail couponHistoryDetail = getUserCoupon(cartPromotionItemList, orderParam.getCouponId());
			if (couponHistoryDetail == null) {
				Asserts.fail("该优惠券不可用");
			}
			//对下单的商品进行优惠处理
			handleCouponAmount(orderItemList, couponHistoryDetail);
		}
		//判断是否使用积分
		if (orderParam.getUseIntegration() == null || orderParam.getUseIntegration().equals(0)) {
			for (OmsOrderItem orderItem : orderItemList) {
				orderItem.setCouponAmount(new BigDecimal(0));
			}
		} else {
			BigDecimal totalAmount = calcTotalAmount(orderItemList);
			BigDecimal integrationAmount = getUserIntegrationAmount(orderParam.getUseIntegration(),
					totalAmount, member, orderParam.getCouponId() != null);
			if (integrationAmount.compareTo(new BigDecimal(0)) == 0) {
				Asserts.fail("积分不可用");
			} else {
				//分摊抵用
				for (OmsOrderItem orderItem : orderItemList) {
					BigDecimal perAmount = orderItem.getProductPrice()
							.divide(totalAmount, 3, RoundingMode.HALF_EVEN)
							.multiply(integrationAmount);
					orderItem.setIntegrationAmount(perAmount);
				}
			}
		}
		
		//计算实付金额
		handleRealAmount(orderItemList);
		//库存锁定
		lockStock(cartPromotionItemList);
		
		//根基商品合计、运费、活动优惠、优惠券、积分计算应付金额
		OmsOrder order = new OmsOrder();
		order.setDiscountAmount(new BigDecimal(0));
		order.setTotalAmount(calcTotalAmount(orderItemList));
		order.setFreightAmount(new BigDecimal(0));
		order.setPromotionAmount(calcPromotionAmount(orderItemList));
		order.setPromotionInfo(getOrderPromotionInfo(orderItemList));
		if (orderParam.getCouponId() == null) {
			order.setCouponAmount(new BigDecimal(0));
		} else {
			order.setCouponId(orderParam.getCouponId());
			order.setCouponAmount(calcCouponAmount(orderItemList));
		}
		if (orderParam.getUseIntegration() == null) {
			order.setIntegration(0);
			order.setIntegrationAmount(new BigDecimal(0));
		} else {
			order.setIntegration(orderParam.getUseIntegration());
			order.setIntegrationAmount(calcIntegrationAmount(orderItemList));
		}
		order.setPayAmount(calcPayAmount(order));
		order.setMemberId(member.getId());
		order.setCreateTime(new Date());
		order.setMemberUsername(member.getUsername());
		order.setPayType(orderParam.getPayType());
		order.setSourceType(1);
		order.setStatus(0);
		order.setOrderType(0);
		UmsMemberReceiveAddress receiveAddress = addressService.getItem(orderParam.getMemberReceiveAddressId());
		order.setReceiverName(receiveAddress.getName());
		order.setReceiverPhone(receiveAddress.getPhoneNumber());
		order.setReceiverPostCode(receiveAddress.getPostCode());
		order.setReceiverProvince(receiveAddress.getProvince());
		order.setReceiverCity(receiveAddress.getCity());
		order.setReceiverRegion(receiveAddress.getRegion());
		order.setReceiverDetailAddress(receiveAddress.getDetailAddress());
		order.setConfirmStatus(0);
		order.setDeleteStatus(0);
		order.setIntegration(calcGiftIntegration(orderItemList));
		order.setGrowth(calGiftGrowth(orderItemList));
		order.setOrderSn(generateOrderSn(order));
		//设置自动收货天数
		List<OmsOrderSetting> orderSettingList = orderSettingMapper.selectByExample(new OmsOrderSettingExample());
		if (CollUtil.isNotEmpty(orderSettingList)) {
			order.setAutoConfirmDay(orderSettingList.get(0).getConfirmOvertime());
		}
		orderMapper.insert(order);
		
		for (OmsOrderItem orderItem : orderItemList) {
			orderItem.setOrderId(order.getId());
			orderItem.setOrderSn(order.getOrderSn());
		}
		orderItemDao.insertList(orderItemList);
		
		//更新优惠券使用状态
		if (orderParam.getUseIntegration() != null) {
			order.setUseIntegration(orderParam.getUseIntegration());
			memberService.updateIntegration(member.getId(), member.getIntegration() - orderParam.getUseIntegration());
		}
		
		//删除购物车中的下单商品
		deleteCartItemList(cartPromotionItemList, member);
		
		//发送延迟消息取消订单
		sendDelayMessageCancelOrder(order.getId());
		
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("order", order);
		resultMap.put("orderItemList", orderItemList);
		return resultMap;
	}
	
	@Override
	public int paySuccess(Long orderId, Integer payType) {
		OmsOrder order = new OmsOrder();
		order.setId(orderId);
		order.setStatus(1);
		order.setPaymentTime(new Date());
		order.setPayType(payType);
		orderMapper.updateByPrimaryKeySelective(order);
		
		//恢复库存锁定 扣减库存
		OmsOrderDetail orderDetail = portalOrderDao.getDetail(orderId);
		int count = portalOrderDao.updateSkuStock(orderDetail.getOrderItemList());
		
		return count;
	}
	
	@Override
	public int cancelTimeOutOrder() {
		Integer count = 0;
		OmsOrderSetting orderSetting = orderSettingMapper.selectByPrimaryKey(1L);
		//查询超时未支付的订单及详情
		List<OmsOrderDetail> timeOutOrderList = portalOrderDao.getTimeOutOrders(orderSetting.getNormalOrderOvertime());
		if (CollUtil.isEmpty(timeOutOrderList)) {
			return count;
		}
		
		//修改订单状态为交易取消
		List<Long> ids = timeOutOrderList.stream().map(o -> o.getId()).collect(Collectors.toList());
		portalOrderDao.updateOrderStatus(ids, 4);
		
		for (OmsOrderDetail orderDetail : timeOutOrderList) {
			//解除商品库存锁定
			portalOrderDao.releaseSkuStockLock(orderDetail.getOrderItemList());
			//修改优惠券使用状态
			updateCouponStatus(orderDetail.getCouponId(), orderDetail.getMemberId(), 0);
			//返回使用积分
			if (orderDetail.getUseIntegration() != null) {
				UmsMember member = memberService.getById(orderDetail.getMemberId());
				memberService.updateIntegration(orderDetail.getMemberId(), member.getIntegration() + orderDetail.getUseIntegration());
			}
		}
		return timeOutOrderList.size();
	}
	
	/**
	 * 将优惠券信息更改为指定状态
	 */
	private void updateCouponStatus(Long couponId, Long memberId, int useStatus) {
		if (couponId == null) {
			return;
		}
		
		SmsCouponHistoryExample historyExample = new SmsCouponHistoryExample();
		historyExample.createCriteria().andMemberIdEqualTo(memberId)
				.andCouponIdEqualTo(couponId).andUseStatusEqualTo(useStatus == 1 ? 0 : 1);
		List<SmsCouponHistory> couponHistoryList = couponHistoryMapper.selectByExample(historyExample);
		if (CollUtil.isNotEmpty(couponHistoryList)) {
			SmsCouponHistory couponHistory = couponHistoryList.get(0);
			couponHistory.setUseTime(new Date());
			couponHistory.setUseStatus(useStatus);
			couponHistoryMapper.updateByPrimaryKeySelective(couponHistory);
		}
	}
	
	@Override
	public void sendDelayMessageCancelOrder(Long orderId) {
		//获取订单超时时间
		OmsOrderSetting orderSetting = orderSettingMapper.selectByPrimaryKey(1L);
		long delayTimes = orderSetting.getNormalOrderOvertime() * 60 * 1000;
		//发送延迟消息
		cancelOrderSender.sendMessage(orderId, delayTimes);
	}
	
	@Override
	public List<OmsOrderDetail> list(Integer status, Integer pageSize, Integer pageNum) {
		if (status == -1) {
			status = null;
		}
		
		UmsMember member = memberService.getCurrentMember();
		
		PageHelper.startPage(pageNum, pageSize);
		
		OmsOrderExample orderExample = new OmsOrderExample();
		OmsOrderExample.Criteria criteria = orderExample.createCriteria();
		criteria.andDeleteStatusEqualTo(0).andMemberIdEqualTo(member.getId());
		if (status != null) {
			criteria.andStatusEqualTo(status);
		}
		orderExample.setOrderByClause("create_time desc");
		List<OmsOrder> orderList = orderMapper.selectByExample(orderExample);
		
		List<OmsOrderDetail> orderDetailList = new ArrayList<>();
		
		if (CollUtil.isEmpty(orderList)) {
			return orderDetailList;
		}
		
		List<Long> orderIdList = orderList.stream().map(OmsOrder::getId).collect(Collectors.toList());
		OmsOrderItemExample itemExample = new OmsOrderItemExample();
		itemExample.createCriteria().andOrderIdIn(orderIdList);
		List<OmsOrderItem> orderItemList = orderItemMapper.selectByExample(itemExample);
		
		for (OmsOrder order : orderList) {
			OmsOrderDetail orderDetail = new OmsOrderDetail();
			BeanUtil.copyProperties(order, orderDetail);
			List<OmsOrderItem> relatedOrderItemList = orderItemList.stream()
					.filter(oi -> oi.getOrderId().equals(orderDetail.getId()))
					.collect(Collectors.toList());
			orderDetail.setOrderItemList(relatedOrderItemList);
			
			orderDetailList.add(orderDetail);
		}
		
		return orderDetailList;
	}
	
	@Override
	public OmsOrderDetail detail(Long orderId) {
		OmsOrderExample orderExample = new OmsOrderExample();
		orderExample.createCriteria().andIdEqualTo(orderId);
		OmsOrder order = orderMapper.selectByPrimaryKey(orderId);
		
		OmsOrderDetail orderDetail = new OmsOrderDetail();
		BeanUtil.copyProperties(order, orderDetail);
		
		OmsOrderItemExample itemExample = new OmsOrderItemExample();
		itemExample.createCriteria().andOrderIdEqualTo(orderId);
		List<OmsOrderItem> orderItemList = orderItemMapper.selectByExample(itemExample);
		
		orderDetail.setOrderItemList(orderItemList);
		
		return orderDetail;
	}
	
	
	@Override
	public void cancelOrder(Long orderId) {
		
		//查询未付款的订单
		OmsOrderExample orderExample = new OmsOrderExample();
		orderExample.createCriteria().andIdEqualTo(orderId).andDeleteStatusEqualTo(0).andStatusEqualTo(0);
		List<OmsOrder> orderList = orderMapper.selectByExample(orderExample);
		if (CollUtil.isEmpty(orderList)) {
			return;
		}
		OmsOrder order = orderList.get(0);
		if (order != null) {
			//修改订单状态为取消
			order.setStatus(4);
			orderMapper.updateByPrimaryKeySelective(order);
			
			OmsOrderItemExample itemExample = new OmsOrderItemExample();
			itemExample.createCriteria().andOrderIdEqualTo(orderId);
			List<OmsOrderItem> orderItemList = orderItemMapper.selectByExample(itemExample);
			//解除商品库存锁定
			if (CollUtil.isNotEmpty(orderItemList)) {
				portalOrderDao.releaseSkuStockLock(orderItemList);
			}
			//修改优惠券使用状态
			updateCouponStatus(order.getId(), order.getMemberId(), 0);
			//返回使用积分
			if (order.getUseIntegration() != null) {
				UmsMember member = memberService.getById(order.getMemberId());
				memberService.updateIntegration(member.getId(), member.getIntegration() + order.getUseIntegration());
			}
		}
		
	}
	
	@Override
	public void confirmReceiveOrder(Long orderId) {
		UmsMember member = memberService.getCurrentMember();
		OmsOrder order = orderMapper.selectByPrimaryKey(orderId);
		if (!order.getMemberId().equals(member.getId())) {
			Asserts.fail("不能确认他人订单");
		}
		if (order.getStatus() != 2) {
			Asserts.fail("订单还未发货");
		}
		order.setStatus(3);
		order.setConfirmStatus(1);
		order.setReceiveTime(new Date());
		orderMapper.updateByPrimaryKeySelective(order);
	}
	
	@Override
	public void deleteOrder(Long orderId) {
		UmsMember currentMember = memberService.getCurrentMember();
		OmsOrder order = orderMapper.selectByPrimaryKey(orderId);
		if (!currentMember.getId().equals(order.getMemberId())) {
			Asserts.fail("不能删除他人订单");
		}
		if (order.getStatus() == 3 || order.getStatus() == 3) {
			order.setDeleteStatus(1);
			orderMapper.updateByPrimaryKeySelective(order);
		} else {
			Asserts.fail("只能删除或关闭已完成的订单");
		}
		
	}
	
	private void deleteCartItemList(List<CartPromotionItem> cartPromotionItemList, UmsMember member) {
		List<Long> cartIdList = cartPromotionItemList.stream().map(c -> c.getId()).collect(Collectors.toList());
		cartItemService.delete(member.getId(), cartIdList);
	}
	
	/**
	 * 生成18位订单编号：8位日期+2位平台号码+2位支付方式+6位以上自增ID
	 */
	private String generateOrderSn(OmsOrder order) {
		StringBuilder sb = new StringBuilder();
		String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
		String key = REDIS_DATABASE + ":" + REDIS_KEY_ORDER_ID + date;
		Long increment = redisService.incr(key, 1);
		sb.append(date);
		sb.append(String.format("%02d", order.getSourceType()));
		sb.append(String.format("02d", order.getPaymentTime()));
		String incrementStr = increment.toString();
		if (incrementStr.length() < 6) {
			sb.append(String.format("%06d", increment));
		} else {
			sb.append(incrementStr);
		}
		return sb.toString();
	}
	
	//计算赠送成长值
	private Integer calGiftGrowth(List<OmsOrderItem> orderItemList) {
		int sum = 0;
		for (OmsOrderItem orderItem : orderItemList) {
			sum += orderItem.getGiftGrowth() * orderItem.getProductQuantity();
		}
		return sum;
	}
	
	//计算赠送积分
	private Integer calcGiftIntegration(List<OmsOrderItem> orderItemList) {
		int sum = 0;
		for (OmsOrderItem orderItem : orderItemList) {
			sum += orderItem.getGiftIntegration() * orderItem.getProductQuantity();
		}
		return sum;
	}
	
	/**
	 * 计算订单应付金额
	 */
	private BigDecimal calcPayAmount(OmsOrder order) {
		//总金额+运费-促销优惠-优惠券优惠-积分抵扣
		BigDecimal payAmount = order.getTotalAmount()
				.add(order.getFreightAmount())
				.subtract(order.getPromotionAmount())
				.subtract(order.getCouponAmount())
				.subtract(order.getIntegrationAmount());
		return payAmount;
	}
	
	/**
	 * 计算订单积分抵用金额
	 */
	private BigDecimal calcIntegrationAmount(List<OmsOrderItem> orderItemList) {
		BigDecimal integrationAmount = new BigDecimal(0);
		for (OmsOrderItem orderItem : orderItemList) {
			if (orderItem.getIntegrationAmount() != null) {
				integrationAmount = integrationAmount.add(orderItem.getIntegrationAmount());
			}
		}
		return integrationAmount;
	}
	
	/**
	 * 计算订单优惠券金额
	 */
	private BigDecimal calcCouponAmount(List<OmsOrderItem> orderItemList) {
		BigDecimal couponAmount = new BigDecimal(0);
		for (OmsOrderItem orderItem : orderItemList) {
			if (orderItem.getCouponAmount() != null) {
				couponAmount = couponAmount.add(orderItem.getCouponAmount());
			}
		}
		return couponAmount;
	}
	
	/**
	 * 计算订单活动优惠
	 */
	private BigDecimal calcPromotionAmount(List<OmsOrderItem> orderItemList) {
		BigDecimal promotionAmount = new BigDecimal(0);
		for (OmsOrderItem orderItem : orderItemList) {
			if (orderItem.getPromotionAmount() != null) {
				promotionAmount = promotionAmount.add(orderItem.getPromotionAmount()
						.multiply(new BigDecimal(orderItem.getProductQuantity())));
			}
		}
		return promotionAmount;
	}
	
	/**
	 * 获取订单促销信息
	 */
	private String getOrderPromotionInfo(List<OmsOrderItem> orderItemList) {
		StringBuilder sb = new StringBuilder();
		for (OmsOrderItem orderItem : orderItemList) {
			sb.append(orderItem.getPromotionName());
			sb.append(";");
		}
		String result = sb.toString();
		if (result.endsWith(";")) {
			result = result.substring(0, result.length() - 1);
		}
		return result;
	}
	
	private void lockStock(List<CartPromotionItem> cartPromotionItemList) {
		for (CartPromotionItem cartPromotionItem : cartPromotionItemList) {
			PmsSkuStock skuStock = skuStockMapper.selectByPrimaryKey(cartPromotionItem.getProductSkuId());
			skuStock.setLowStock(skuStock.getLowStock() + cartPromotionItem.getQuantity());
			skuStockMapper.updateByPrimaryKeySelective(skuStock);
		}
	}
	
	private void handleRealAmount(List<OmsOrderItem> orderItemList) {
		for (OmsOrderItem orderItem : orderItemList) {
			BigDecimal realAmount = orderItem.getProductPrice().subtract(orderItem.getPromotionAmount())
					.subtract(orderItem.getCouponAmount()).subtract(orderItem.getIntegrationAmount());
			orderItem.setRealAmount(realAmount);
		}
	}
	
	/**
	 * 获取积分抵扣金额
	 */
	private BigDecimal getUserIntegrationAmount(Integer useIntegration, BigDecimal totalAmount, UmsMember member, boolean hasCoupon) {
		BigDecimal zeroAmount = new BigDecimal(0);
		if (useIntegration.compareTo(member.getIntegration()) > 0) {
			return zeroAmount;
		}
		//根据积分使用规则判断是否可用
		//是否可与优惠券共用
		UmsIntegrationConsumeSetting integrationConsumeSetting = integrationConsumeSettingMapper.selectByPrimaryKey(1L);
		if (hasCoupon && integrationConsumeSetting.getCouponStatus().equals(0)) {
			return zeroAmount;
		}
		//是否高于 每次使用积分的最小值
		if (useIntegration.compareTo(integrationConsumeSetting.getUseUnit()) < 0) {
			return zeroAmount;
		}
		//是否超出最高抵用百分比
		BigDecimal integrationAmount = new BigDecimal(useIntegration)
				.divide(new BigDecimal(integrationConsumeSetting.getDeductionPerAmount()), 2, RoundingMode.HALF_EVEN);
		BigDecimal maxPercent = new BigDecimal(integrationConsumeSetting.getMaxPercentPerOrder())
				.divide(new BigDecimal(100), 2, RoundingMode.HALF_EVEN);
		if (integrationAmount.compareTo(totalAmount.multiply(maxPercent)) > 0) {
			return zeroAmount;
		}
		
		return integrationAmount;
	}
	
	private BigDecimal calcTotalAmount(List<OmsOrderItem> orderItemList) {
		BigDecimal totalAmount = new BigDecimal(0);
		for (OmsOrderItem orderItem : orderItemList) {
			totalAmount = totalAmount.add(orderItem.getProductPrice().multiply(new BigDecimal(orderItem.getProductQuantity())));
		}
		return totalAmount;
	}
	
	private void handleCouponAmount(List<OmsOrderItem> orderItemList, SmsCouponHistoryDetail couponHistoryDetail) {
		SmsCoupon coupon = couponHistoryDetail.getCoupon();
		Integer useType = coupon.getUseType();
		if (useType.equals(0)) {
			//全场通用
			calcPerCouponAmount(orderItemList, coupon);
		} else if (useType.equals(1)) {
			//指定分类
			List<OmsOrderItem> couponOrderItemList = getCouponOrderItemByRelation(couponHistoryDetail, orderItemList, 0);
			calcPerCouponAmount(couponOrderItemList, coupon);
			
		} else if (useType.equals(2)) {
			//指定商品
			List<OmsOrderItem> couponOrderItemList = getCouponOrderItemByRelation(couponHistoryDetail, orderItemList, 1);
			calcPerCouponAmount(couponOrderItemList, coupon);
		}
	}
	
	/**
	 * 获取与优惠券有关的商品
	 */
	private List<OmsOrderItem> getCouponOrderItemByRelation(SmsCouponHistoryDetail couponHistoryDetail, List<OmsOrderItem> orderItemList, int type) {
		List<OmsOrderItem> result = new ArrayList<>();
		if (type == 0) {
			//指定分类
			List<Long> categoryIdList = new ArrayList<>();
			for (SmsCouponProductCategoryRelation categoryRelation : couponHistoryDetail.getCategoryRelationList()) {
				categoryIdList.add(categoryRelation.getProductCategoryId());
			}
			for (OmsOrderItem orderItem : orderItemList) {
				if (categoryIdList.contains(orderItem.getProductCategoryId())) {
					result.add(orderItem);
				} else {
					orderItem.setCouponAmount(new BigDecimal(0));
				}
			}
		} else if (type == 1) {
			//指定商品
			List<Long> productIdList = new ArrayList<>();
			for (SmsCouponProductRelation productRelation : couponHistoryDetail.getProductRelationList()) {
				productIdList.add(productRelation.getProductId());
			}
			for (OmsOrderItem orderItem : orderItemList) {
				if (productIdList.contains(orderItem.getProductId())) {
					result.add(orderItem);
				} else {
					orderItem.setCouponAmount(new BigDecimal(0));
				}
			}
		}
		return result;
	}
	
	/**
	 * 对每个商品进行优惠券金额分摊计算
	 */
	private void calcPerCouponAmount(List<OmsOrderItem> orderItemList, SmsCoupon coupon) {
		BigDecimal totalAmount = calcTotalAmount(orderItemList);
		for (OmsOrderItem orderItem : orderItemList) {
			//(商品价格*可用商品总价)*优惠券面额
			BigDecimal couponAmount = orderItem.getProductPrice()
					.divide(totalAmount, 3, RoundingMode.HALF_EVEN)
					.multiply(coupon.getAmount());
			orderItem.setCouponAmount(couponAmount);
		}
	}
	
	/**
	 * 获取该用户可使用的优惠券
	 */
	private SmsCouponHistoryDetail getUserCoupon(List<CartPromotionItem> cartPromotionItemList, Long couponId) {
		List<SmsCouponHistoryDetail> couponHistoryDetailList = memberCouponService.listCart(cartPromotionItemList, 1);
		for (SmsCouponHistoryDetail couponHistoryDetail : couponHistoryDetailList) {
			if (couponHistoryDetail.getCouponId().equals(couponId)) {
				return couponHistoryDetail;
			}
		}
		return null;
	}
	
	private boolean hasStock(List<CartPromotionItem> cartPromotionItemList) {
		for (CartPromotionItem cartPromotionItem : cartPromotionItemList) {
			if (cartPromotionItem.getRealStock() == null || cartPromotionItem.getRealStock() <= 0) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 计算购物车中商品价格
	 */
	private ConfirmOrderResult.CalcAmount calcCartAmount(List<CartPromotionItem> cartPromotionItemList) {
		ConfirmOrderResult.CalcAmount calcAmount = new ConfirmOrderResult.CalcAmount();
		calcAmount.setFreightAmount(new BigDecimal(0));
		BigDecimal totalAmount = new BigDecimal(0);
		BigDecimal promotionAmount = new BigDecimal(0);
		for (CartPromotionItem promotionItem : cartPromotionItemList) {
			totalAmount = totalAmount.add(promotionItem.getPrice().multiply(new BigDecimal(promotionItem.getQuantity())));
			promotionAmount = promotionAmount.add(promotionItem.getReduceAmount().multiply(new BigDecimal(promotionItem.getQuantity())));
		}
		calcAmount.setTotalAmount(totalAmount);
		calcAmount.setPromotionAmount(promotionAmount);
		calcAmount.setPayAmount(totalAmount.subtract(promotionAmount));
		
		return calcAmount;
	}
}
