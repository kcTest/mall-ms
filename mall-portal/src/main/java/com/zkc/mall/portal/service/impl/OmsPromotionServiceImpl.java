package com.zkc.mall.portal.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.zkc.mall.mbg.model.OmsCartItem;
import com.zkc.mall.mbg.model.PmsProductFullReduction;
import com.zkc.mall.mbg.model.PmsProductLadder;
import com.zkc.mall.mbg.model.PmsSkuStock;
import com.zkc.mall.portal.dao.PortalProductDao;
import com.zkc.mall.portal.domain.CartPromotionItem;
import com.zkc.mall.portal.domain.PromotionProduct;
import com.zkc.mall.portal.service.OmsPromotionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OmsPromotionServiceImpl implements OmsPromotionService {
	
	@Resource
	private PortalProductDao portalProductDao;
	
	@Override
	public List<CartPromotionItem>  calcCartPromotion(List<OmsCartItem> cartItemList) {
		
		//1先根据productId对cartItem进行分组
		Map<Long, List<OmsCartItem>> productCartMap = cartItemList.stream()
				.collect(Collectors.groupingBy(OmsCartItem::getId));
		
		//2查询所有商品的优惠相关信息
		List<PromotionProduct> promotionProductList = getPromotionProductList(cartItemList);
		
		//3根据商品促销类型计算促销的价格
		List<CartPromotionItem> cartPromotionItemList = new ArrayList<>();
		for (Map.Entry<Long, List<OmsCartItem>> entry : productCartMap.entrySet()) {
			Long productId = entry.getKey();
			List<OmsCartItem> itemList = entry.getValue();
			
			PromotionProduct promotionProduct = promotionProductList.stream()
					.filter(l -> l.getId().equals(productId)).findFirst().orElse(null);
			Integer promotionType = promotionProduct.getPromotionType();
			switch (promotionType) {
				case 1:
					//使用促销价
					for (OmsCartItem item : itemList) {
						CartPromotionItem cartPromotionItem = new CartPromotionItem();
						BeanUtil.copyProperties(item, cartPromotionItem);
						cartPromotionItem.setPromotionMessage("单品促销");
						//获取原价
						PmsSkuStock skuStock = promotionProduct.getSkuStockList().stream()
								.filter(s -> s.getId().equals(item.getProductSkuId())).findFirst().orElse(null);
						BigDecimal oriPrice = skuStock.getPrice();
						//单品促销使用原价
						cartPromotionItem.setPrice(oriPrice);
						//商品原价-促销价
						cartPromotionItem.setReduceAmount(oriPrice.subtract(skuStock.getPromotionPrice()));
						cartPromotionItem.setRealStock(skuStock.getStock() - skuStock.getLockStock());
						cartPromotionItem.setIntegration(promotionProduct.getGiftGrowth());
						cartPromotionItem.setGrowth(promotionProduct.getGiftGrowth());
						
						cartPromotionItemList.add(cartPromotionItem);
					}
					break;
				case 3:
					//使用阶梯价格
					long count = itemList.stream().collect(Collectors.summingInt(i -> i.getQuantity()));
					PmsProductLadder ladder = getProductLadder(count, promotionProduct.getProductLadderList());
					if (ladder != null) {
						for (OmsCartItem item : itemList) {
							CartPromotionItem cartPromotionItem = new CartPromotionItem();
							BeanUtil.copyProperties(item, cartPromotionItem);
							String message = getLadderPromotionMessage(ladder);
							cartPromotionItem.setPromotionMessage(message);
							//获取原价
							PmsSkuStock skuStock = promotionProduct.getSkuStockList().stream()
									.filter(s -> s.getId().equals(item.getProductSkuId())).findFirst().orElse(null);
							BigDecimal oriPrice = skuStock.getPrice();
							//原价-打折后价格
							cartPromotionItem.setReduceAmount(oriPrice.subtract(ladder.getDiscount().multiply(oriPrice)));
							cartPromotionItem.setRealStock(skuStock.getStock() - skuStock.getLockStock());
							cartPromotionItem.setIntegration(promotionProduct.getGiftGrowth());
							cartPromotionItem.setIntegration(promotionProduct.getGiftPoint());
							
							cartPromotionItemList.add(cartPromotionItem);
						}
					} else {
						handleNoReduce(cartPromotionItemList, itemList, promotionProduct);
					}
					break;
				case 4:
					//使用满减价格
					//计算其中所有商品的总价
					BigDecimal totalAmount = getCartItemAmount(itemList, promotionProductList);
					PmsProductFullReduction fullReduction = getProductFullReduction(totalAmount, promotionProduct.getProductFullReductionList());
					if (fullReduction != null) {
						for (OmsCartItem item : itemList) {
							CartPromotionItem cartPromotionItem = new CartPromotionItem();
							BeanUtil.copyProperties(item, cartPromotionItem);
							String message = getFullReductionPromotionMessage(fullReduction);
							cartPromotionItem.setPromotionMessage(message);
							//获取原价
							PmsSkuStock skuStock = promotionProduct.getSkuStockList().stream()
									.filter(s -> s.getId().equals(item.getProductSkuId())).findFirst().orElse(null);
							BigDecimal oriPrice = skuStock.getPrice();
							//分配满减金额
							BigDecimal reduceAmount = oriPrice.divide(totalAmount, RoundingMode.HALF_EVEN)
									.multiply(fullReduction.getReducePrice());
							cartPromotionItem.setReduceAmount(reduceAmount);
							cartPromotionItem.setRealStock(skuStock.getStock() - skuStock.getLockStock());
							cartPromotionItem.setIntegration(promotionProduct.getGiftPoint());
							cartPromotionItem.setGrowth(promotionProduct.getGiftGrowth());
							
							cartPromotionItemList.add(cartPromotionItem);
						}
					} else {
						handleNoReduce(cartPromotionItemList, itemList, promotionProduct);
					}
					break;
				default:
					handleNoReduce(cartPromotionItemList, itemList, promotionProduct);
			}
		}
		
		return cartPromotionItemList;
	}
	
	private String getFullReductionPromotionMessage(PmsProductFullReduction fullReduction) {
		StringBuilder sb = new StringBuilder();
		sb.append("满减优惠：");
		sb.append("满");
		sb.append(fullReduction.getFullPrice());
		sb.append("元，");
		sb.append("减");
		sb.append(fullReduction.getReducePrice());
		sb.append("元");
		return sb.toString();
	}
	
	/**
	 * 对没有满足优惠条件的商品处理
	 */
	private void handleNoReduce(List<CartPromotionItem> cartPromotionItemList, List<OmsCartItem> itemList, PromotionProduct promotionProduct) {
		for (OmsCartItem item : itemList) {
			CartPromotionItem cartPromotionItem = new CartPromotionItem();
			BeanUtil.copyProperties(item, cartPromotionItem);
			cartPromotionItem.setPromotionMessage("无优惠");
			cartPromotionItem.setReduceAmount(new BigDecimal(0));
			PmsSkuStock skuStock = promotionProduct.getSkuStockList().stream()
					.filter(s -> s.getId().equals(item.getProductSkuId())).findFirst().orElse(null);
			if (skuStock != null) {
				cartPromotionItem.setRealStock(skuStock.getStock() - skuStock.getLockStock());
			}
			cartPromotionItem.setGrowth(promotionProduct.getGiftGrowth());
			cartPromotionItem.setIntegration(promotionProduct.getGiftPoint());
			cartPromotionItemList.add(cartPromotionItem);
		}
	}
	
	private PmsProductFullReduction getProductFullReduction(BigDecimal totalAmount, List<PmsProductFullReduction> productFullReductionList) {
		//从高到低排序
		productFullReductionList = productFullReductionList.stream()
				.sorted(Comparator.comparing(PmsProductFullReduction::getFullPrice).reversed()).collect(Collectors.toList());
		for (PmsProductFullReduction fullReduction : productFullReductionList) {
			if (totalAmount.subtract(fullReduction.getFullPrice()).intValue() >= 0) {
				return fullReduction;
			}
		}
		return null;
	}
	
	private BigDecimal getCartItemAmount(List<OmsCartItem> itemList, List<PromotionProduct> promotionProductList) {
		BigDecimal totalAmount = new BigDecimal(0);
		for (OmsCartItem item : itemList) {
			//获取原价
			PromotionProduct promotionProduct = promotionProductList.stream()
					.filter(l -> l.getId().equals(item.getProductId())).findFirst().orElse(null);
			PmsSkuStock skuStock = promotionProduct.getSkuStockList().stream()
					.filter(s -> s.getId().equals(item.getProductSkuId())).findFirst().orElse(null);
			//原价*数量
			totalAmount = totalAmount.add(skuStock.getPrice().multiply(new BigDecimal(item.getQuantity())));
		}
		return totalAmount;
	}
	
	private String getLadderPromotionMessage(PmsProductLadder ladder) {
		StringBuilder sb = new StringBuilder();
		sb.append("打折优惠：");
		sb.append("满");
		sb.append(ladder.getCount());
		sb.append("件");
		sb.append("打");
		sb.append(ladder.getDiscount().multiply(new BigDecimal(10)));
		sb.append("折");
		return sb.toString();
	}
	
	/**
	 * 根据数量获取满足条件的优惠策略
	 */
	private PmsProductLadder getProductLadder(long count, List<PmsProductLadder> productLadderList) {
		productLadderList = productLadderList.stream()
				.sorted(Comparator.comparing(PmsProductLadder::getCount).reversed()).collect(Collectors.toList());
		for (PmsProductLadder ladder : productLadderList) {
			if (count >= ladder.getCount()) {
				return ladder;
			}
		}
		return null;
	}
	
	
	private List<PromotionProduct> getPromotionProductList(List<OmsCartItem> cartItemList) {
		List<Long> productIdList = cartItemList.stream().map(c -> c.getProductId()).collect(Collectors.toList());
		return portalProductDao.getPromotionProductList(productIdList);
	}
	
}
