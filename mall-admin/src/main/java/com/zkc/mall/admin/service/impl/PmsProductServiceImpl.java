package com.zkc.mall.admin.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.zkc.mall.admin.dao.*;
import com.zkc.mall.admin.dto.PmsProductParam;
import com.zkc.mall.admin.dto.PmsProductQueryParam;
import com.zkc.mall.admin.dto.PmsProductResult;
import com.zkc.mall.admin.dao.PmsProductFullReductionDao;
import com.zkc.mall.admin.service.PmsProductService;
import com.zkc.mall.mbg.mapper.*;
import com.zkc.mall.mbg.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PmsProductServiceImpl implements PmsProductService {
	
	private static final Logger Logger = LoggerFactory.getLogger(PmsProductServiceImpl.class);
	
	@Autowired
	private PmsProductMapper productMapper;
	@Autowired
	private PmsProductVertifyRecordDao productVertifyRecordDao;
	@Autowired
	private PmsMemberPriceDao memberPriceDao;
	@Autowired
	private PmsMemberPriceMapper memberPriceMapper;
	@Autowired
	private PmsProductLadderDao productLadderDao;
	@Autowired
	private PmsProductLadderMapper productLadderMapper;
	@Autowired
	private PmsProductFullReductionDao productFullReductionDao;
	@Autowired
	private PmsProductFullReductionMapper productFullReductionMapper;
	@Autowired
	private PmsSkuStockDao skuStockDao;
	@Autowired
	private PmsSkuStockMapper skuStockMapper;
	@Autowired
	private PmsProductAttributeValueDao productAttributeValueDao;
	@Autowired
	private PmsProductAttributeValueMapper productAttributeValueMapper;
	@Autowired
	private CmsSubjectProductRelationDao subjectProductRelationDao;
	@Autowired
	private CmsSubjectProductRelationMapper subjectProductRelationMapper;
	@Autowired
	private CmsPreferenceAreaProductRelationDao preferenceAreaProductRelationDao;
	@Autowired
	private CmsPreferenceAreaProductRelationMapper preferenceAreaProductRelationMapper;
	@Autowired
	private PmsProductDao productDao;
	
	@Override
	public int create(PmsProductParam productParam) {
		
		PmsProduct product = productParam;
		product.setId(null);
		int count = productMapper.insertSelective(product);
		Long productId = product.getId();
		
		//商品阶梯价格设置
		insertRelationList(productLadderDao, productParam.getProductLadderList(), productId);
		//商品满减价格设置
		insertRelationList(productFullReductionDao, productParam.getProductFullReductionList(), productId);
		//商品会员价格设置
		insertRelationList(memberPriceDao, productParam.getMemberPriceList(), productId);
		//处理sku编码 添加商品库存
		handleSkuStockCode(productParam.getSkuStockList(), productId);
		insertRelationList(skuStockDao, productParam.getSkuStockList(), productId);
		//商品参数规格
		insertRelationList(productAttributeValueDao, productParam.getProductAttributeValueList(), productId);
		//专题和商品的关系
		insertRelationList(subjectProductRelationDao, productParam.getSubjectProductRelationList(), productId);
		//优选专区和商品的关系
		insertRelationList(preferenceAreaProductRelationDao, productParam.getPreferenceAreaProductRelationList(), productId);
		
		return count;
	}
	
	@Override
	public PmsProductResult getUpdateInfo(Long id) {
		return productDao.getUpdateInfo(id);
	}
	
	@Override
	public int update(Long id, PmsProductParam productParam) {
		
		PmsProduct product = productParam;
		product.setId(id);
		int count = productMapper.updateByPrimaryKeySelective(product);
		Long productId = productParam.getId();
		//商品阶梯价格设置
		PmsProductLadderExample ladderExample = new PmsProductLadderExample();
		ladderExample.createCriteria().andProductIdEqualTo(productId);
		productLadderMapper.deleteByExample(ladderExample);
		insertRelationList(productLadderDao, productParam.getProductLadderList(), productId);
		//商品满减价格设置
		PmsProductFullReductionExample reductionExample = new PmsProductFullReductionExample();
		reductionExample.createCriteria().andProductIdEqualTo(productId);
		productFullReductionMapper.deleteByExample(reductionExample);
		insertRelationList(productFullReductionDao, productParam.getProductFullReductionList(), productId);
		//商品会员价格设置
		PmsMemberPriceExample priceExample = new PmsMemberPriceExample();
		priceExample.createCriteria().andProductIdEqualTo(productId);
		memberPriceMapper.deleteByExample(priceExample);
		insertRelationList(memberPriceDao, productParam.getMemberPriceList(), productId);
		//修改商品库存
		handleUpdateSkuStockList(id, productParam);
		//商品参数规格
		PmsProductAttributeValueExample attributeValueExample = new PmsProductAttributeValueExample();
		attributeValueExample.createCriteria().andProductIdEqualTo(productId);
		productAttributeValueMapper.deleteByExample(attributeValueExample);
		insertRelationList(productAttributeValueDao, productParam.getProductAttributeValueList(), productId);
		//专题和商品的关系
		CmsSubjectProductRelationExample productRelationExample = new CmsSubjectProductRelationExample();
		productRelationExample.createCriteria().andProductIdEqualTo(productId);
		subjectProductRelationMapper.deleteByExample(productRelationExample);
		insertRelationList(subjectProductRelationDao, productParam.getSubjectProductRelationList(), productId);
		//优选专区和商品的关系
		CmsPreferenceAreaProductRelationExample preferenceAreaProductRelationExample = new CmsPreferenceAreaProductRelationExample();
		preferenceAreaProductRelationExample.createCriteria().andProductIdEqualTo(productId);
		preferenceAreaProductRelationMapper.deleteByExample(preferenceAreaProductRelationExample);
		insertRelationList(preferenceAreaProductRelationDao, productParam.getPreferenceAreaProductRelationList(), productId);
		
		return count;
	}
	
	@Override
	public List<PmsProduct> list(PmsProductQueryParam productQueryParam, Integer pageSize, Integer pageNum) {
		PageHelper.startPage(pageNum, pageSize);
		Logger.info("pageNum{} pageSize{}", pageNum, pageSize);
		PmsProductExample example = new PmsProductExample();
		PmsProductExample.Criteria criteria = example.createCriteria();
		criteria.andDeleteStatusEqualTo(0);
		
		if (productQueryParam.getPublishStatus() != null) {
			criteria.andPublishStatusEqualTo(productQueryParam.getPublishStatus());
		}
		if (productQueryParam.getVerifyStatus() != null) {
			criteria.andVerifyStatusEqualTo(productQueryParam.getVerifyStatus());
		}
		if (StrUtil.isNotEmpty(productQueryParam.getKeyword())) {
			criteria.andNameLike("%" + productQueryParam.getKeyword() + "%");
		}
		if (StrUtil.isNotEmpty(productQueryParam.getProductSn())) {
			criteria.andProductSnEqualTo(productQueryParam.getProductSn());
		}
		if (productQueryParam.getProductCategoryId() != null) {
			criteria.andProductCategoryIdEqualTo(productQueryParam.getProductCategoryId());
		}
		if (productQueryParam.getBrandId() != null) {
			criteria.andBrandIdEqualTo(productQueryParam.getBrandId());
		}
		return productMapper.selectByExample(example);
	}
	
	@Override
	public List<PmsProduct> getList(String keyword) {
		PmsProductExample example = new PmsProductExample();
		PmsProductExample.Criteria criteria = example.createCriteria();
		criteria.andDeleteStatusEqualTo(0);
		if (StrUtil.isNotEmpty(keyword)) {
			criteria.andNameLike("%" + keyword + "%");
			example.or().andDeleteStatusEqualTo(0).andProductSnLike("%" + keyword + "%");
		}
		
		return productMapper.selectByExample(example);
	}
	
	@Override
	public int updateVerifyStatus(List<Long> ids, Integer verifyStatus, String detail) {
		
		PmsProduct product = new PmsProduct();
		product.setVerifyStatus(verifyStatus);
		PmsProductExample example = new PmsProductExample();
		example.createCriteria().andIdIn(ids);
		int count = productMapper.updateByExampleSelective(product, example);
		
		List<PmsProductVertifyRecord> vertifyRecordList = new ArrayList<>();
		for (Long id : ids) {
			PmsProductVertifyRecord vertifyRecord = new PmsProductVertifyRecord();
			vertifyRecord.setProductId(id);
			vertifyRecord.setStatus(verifyStatus);
			vertifyRecord.setDetail(detail);
			vertifyRecord.setCreateTime(new Date());
			vertifyRecord.setVertifyMan("???");
			vertifyRecordList.add(vertifyRecord);
		}
		productVertifyRecordDao.insertList(vertifyRecordList);
		
		return count;
	}
	
	@Override
	public int updatePublishStatus(List<Long> ids, Integer publishStatus) {
		PmsProduct product = new PmsProduct();
		product.setPublishStatus(publishStatus);
		PmsProductExample example = new PmsProductExample();
		example.createCriteria().andIdIn(ids);
		return productMapper.updateByExampleSelective(product, example);
	}
	
	@Override
	public int updateRecommendStatus(List<Long> ids, Integer recommendStatus) {
		PmsProduct product = new PmsProduct();
		product.setRecommendStatus(recommendStatus);
		PmsProductExample example = new PmsProductExample();
		example.createCriteria().andIdIn(ids);
		return productMapper.updateByExampleSelective(product, example);
	}
	
	@Override
	public int updateNewStatus(List<Long> ids, Integer newStatus) {
		PmsProduct product = new PmsProduct();
		product.setNewStatus(newStatus);
		PmsProductExample example = new PmsProductExample();
		example.createCriteria().andIdIn(ids);
		return productMapper.updateByExampleSelective(product, example);
	}
	
	@Override
	public int updateDeleteStatus(List<Long> ids, Integer deleteStatus) {
		PmsProduct product = new PmsProduct();
		product.setDeleteStatus(deleteStatus);
		PmsProductExample example = new PmsProductExample();
		example.createCriteria().andIdIn(ids);
		return productMapper.updateByExampleSelective(product, example);
	}
	
	@Override
	public List<PmsProduct> list(String keyword) {
		PmsProductExample example = new PmsProductExample();
		PmsProductExample.Criteria criteria = example.createCriteria();
		criteria.andDeleteStatusEqualTo(0);
		if (StrUtil.isNotEmpty(keyword)) {
			criteria.andNameLike("%" + keyword + "%");
			example.or().andDeleteStatusEqualTo(0).andProductSnLike("%" + keyword + "%");
		}
		return productMapper.selectByExample(example);
	}
	
	private void handleUpdateSkuStockList(Long id, PmsProductParam productParam) {
		//当前sku信息
		List<PmsSkuStock> currStockList = productParam.getSkuStockList();
		//没有sku信息将原sku记录清空
		if (CollectionUtils.isEmpty(currStockList)) {
			PmsSkuStockExample stockExample = new PmsSkuStockExample();
			stockExample.createCriteria().andProductIdEqualTo(id);
			skuStockMapper.deleteByExample(stockExample);
			return;
		}
		//获取原sku记录
		PmsSkuStockExample example = new PmsSkuStockExample();
		example.createCriteria().andProductIdEqualTo(id);
		List<PmsSkuStock> oriSkuStockList = skuStockMapper.selectByExample(example);
		//对比当前记录 提取需要插入的新记录
		List<PmsSkuStock> insertStockList = currStockList.stream().filter(i -> i.getId() == null).collect(Collectors.toList());
		//对比当前记录 提取需要更新的记录
		List<PmsSkuStock> updateStockList = currStockList.stream().filter(i -> i.getId() != null).collect(Collectors.toList());
		//对比原始记录 原始记录如果在当前记录里找不到需要删除
		List<Long> updateIds = updateStockList.stream().map(i -> i.getId()).collect(Collectors.toList());
		List<PmsSkuStock> deleteStockList = oriSkuStockList.stream().filter(i -> !updateIds.contains(i.getId())).collect(Collectors.toList());
		handleSkuStockCode(insertStockList, id);
		handleSkuStockCode(updateStockList, id);
		
		//新增
		if (CollUtil.isNotEmpty(insertStockList)) {
			insertRelationList(skuStockDao, insertStockList, id);
		}
		//删除
		if (CollUtil.isNotEmpty(deleteStockList)) {
			List<Long> skuIds = deleteStockList.stream().map(i -> i.getId()).collect(Collectors.toList());
			PmsSkuStockExample stockExample = new PmsSkuStockExample();
			stockExample.createCriteria().andIdIn(skuIds);
			skuStockMapper.deleteByExample(stockExample);
		}
		//修改
		if (CollUtil.isNotEmpty(updateStockList)) {
			for (PmsSkuStock skuStock : updateStockList) {
				skuStockMapper.updateByPrimaryKeySelective(skuStock);
			}
		}
		
	}
	
	private void handleSkuStockCode(List<PmsSkuStock> skuStockList, Long productId) {
		if (CollectionUtils.isEmpty(skuStockList)) {
			return;
		}
		for (int i = 0; i < skuStockList.size(); i++) {
			PmsSkuStock skuStock = skuStockList.get(i);
			if (StrUtil.isEmpty(skuStock.getSkuCode())) {
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				StringBuilder sb = new StringBuilder();
				//日期
				sb.append(sdf.format(new Date()));
				//四位商品ID
				sb.append(String.format("%04d", productId));
				//三位索引ID
				sb.append(String.format("%03d", i + 1));
				skuStock.setSkuCode(sb.toString());
			}
			
		}
	}
	
	private void insertRelationList(Object dao, List dataList, Long productId) {
		try {
			if (CollectionUtils.isEmpty(dataList)) {
				return;
			}
			for (Object item : dataList) {
				Method setId = item.getClass().getMethod("setId", Long.class);
				setId.invoke(item, (Long) null);
				
				Method setProductId = item.getClass().getMethod("setProductId", Long.class);
				setProductId.invoke(item, productId);
			}
			Method insertList = dao.getClass().getMethod("insertList", List.class);
			insertList.invoke(dao, dataList);
		} catch (Exception e) {
			Logger.warn("创建产品出错：{}", e);
			throw new RuntimeException(e.getMessage());
		}
	}
}
