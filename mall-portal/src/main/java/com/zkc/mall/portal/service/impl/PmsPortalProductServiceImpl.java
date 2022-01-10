package com.zkc.mall.portal.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.zkc.mall.mbg.mapper.*;
import com.zkc.mall.mbg.model.*;
import com.zkc.mall.portal.dao.PmsPortalProductDao;
import com.zkc.mall.portal.domain.PmsPortalProductDetail;
import com.zkc.mall.portal.domain.PmsProductCategoryNode;
import com.zkc.mall.portal.service.PmsPortalProductService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PmsPortalProductServiceImpl implements PmsPortalProductService {
	
	@Resource
	private PmsProductMapper productMapper;
	@Resource
	private PmsBrandMapper brandMapper;
	@Resource
	private PmsProductCategoryMapper productCategoryMapper;
	@Resource
	private PmsProductAttributeMapper productAttributeMapper;
	@Resource
	private PmsProductAttributeValueMapper productAttributeValueMapper;
	@Resource
	private PmsSkuStockMapper skuStockMapper;
	@Resource
	private PmsProductLadderMapper productLadderMapper;
	@Resource
	private PmsProductFullReductionMapper productFullReductionMapper;
	@Resource
	private PmsPortalProductDao portalProductDao;
	
	
	@Override
	public List<PmsProduct> search(String keyword, Long brandId, Long productCategoryId, Integer sort, Integer pageSize, Integer pageNum) {
		PageHelper.startPage(pageNum, pageSize);
		
		
		PmsProductExample productExample = new PmsProductExample();
		PmsProductExample.Criteria criteria = productExample.createCriteria().andDeleteStatusEqualTo(0);
		if (StrUtil.isNotEmpty(keyword)) {
			criteria.andNameLike("%" + keyword + "%");
		}
		if (brandId != null) {
			criteria.andBrandIdEqualTo(brandId);
		}
		if (productCategoryId != null) {
			criteria.andProductCategoryIdEqualTo(productCategoryId);
		}
		
		switch (sort) {
			case 1:
				productExample.setOrderByClause("id desc");
				break;
			case 2:
				productExample.setOrderByClause("sale desc");
				break;
			case 3:
				productExample.setOrderByClause("price asc");
				break;
			case 4:
				productExample.setOrderByClause("price desc");
				break;
		}
		
		return productMapper.selectByExample(productExample);
	}
	
	@Override
	public List<PmsProductCategoryNode> categoryTreeList() {
		List<PmsProductCategory> categoryList = productCategoryMapper.selectByExample(new PmsProductCategoryExample());
		List<PmsProductCategoryNode> categoryNodeList = categoryList.stream()
				.filter(c -> c.getParentId().equals(0))
				.map(c -> convert(c, categoryList)).collect(Collectors.toList());
		return categoryNodeList;
	}
	
	@Override
	public PmsPortalProductDetail detail(Long productId) {
		PmsPortalProductDetail productDetail = new PmsPortalProductDetail();
		
		//获取商品信息
		PmsProduct product = productMapper.selectByPrimaryKey(productId);
		productDetail.setProduct(product);
		
		//获取品牌信息
		PmsBrand brand = brandMapper.selectByPrimaryKey(product.getBrandId());
		productDetail.setBrand(brand);
		
		//获取商品属性信息
		PmsProductAttributeExample attributeExample = new PmsProductAttributeExample();
		attributeExample.createCriteria().andProductAttributeCategoryIdEqualTo(product.getProductAttributeCategoryId());
		List<PmsProductAttribute> productAttributeList = productAttributeMapper.selectByExample(attributeExample);
		productDetail.setProductAttributeList(productAttributeList);
		
		//获取商品属性值信息
		if (CollUtil.isNotEmpty(productAttributeList)) {
			PmsProductAttributeValueExample attributeValueExample = new PmsProductAttributeValueExample();
			attributeValueExample.createCriteria().andProductAttributeIdIn(productAttributeList.stream()
					.map(a -> a.getId()).collect(Collectors.toList()));
			List<PmsProductAttributeValue> attributeValueList = productAttributeValueMapper.selectByExample(attributeValueExample);
			productDetail.setProductAttributeValueList(attributeValueList);
		}
		
		//获取商品SKU库存信息
		PmsSkuStockExample stockExample = new PmsSkuStockExample();
		stockExample.createCriteria().andProductIdEqualTo(productId);
		List<PmsSkuStock> skuStockList = skuStockMapper.selectByExample(stockExample);
		productDetail.setSkuStockList(skuStockList);
		
		//商品阶梯价格设置
		if (product.getPromotionType() == 3) {
			PmsProductLadderExample ladderExample = new PmsProductLadderExample();
			ladderExample.createCriteria().andProductIdEqualTo(productId);
			List<PmsProductLadder> ladderList = productLadderMapper.selectByExample(ladderExample);
			productDetail.setProductLadderList(ladderList);
		}
		
		//商品满减价格设置
		if (product.getPromotionType() == 4) {
			PmsProductFullReductionExample reductionExample = new PmsProductFullReductionExample();
			reductionExample.createCriteria().andProductIdEqualTo(productId);
			List<PmsProductFullReduction> reductionList = productFullReductionMapper.selectByExample(reductionExample);
			productDetail.setProductFullReductionList(reductionList);
		}
		
		//商品可用优惠券
		List<SmsCoupon> couponList = portalProductDao.getAvailableCouponList(productId, product.getProductCategoryId());
		productDetail.setCouponList(couponList);
		
		return productDetail;
	}
	
	private PmsProductCategoryNode convert(PmsProductCategory parent, List<PmsProductCategory> categoryList) {
		PmsProductCategoryNode parentNode = new PmsProductCategoryNode();
		BeanUtil.copyProperties(parent, parentNode);
		List<PmsProductCategoryNode> nodeList = categoryList.stream()
				.filter(children -> children.getParentId().equals(parent.getId()))
				.map(children -> convert(children, categoryList))
				.collect(Collectors.toList());
		parentNode.setChildren(nodeList);
		return parentNode;
	}
}
