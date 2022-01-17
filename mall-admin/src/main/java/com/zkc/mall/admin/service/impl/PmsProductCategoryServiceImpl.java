package com.zkc.mall.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.github.pagehelper.PageHelper;
import com.zkc.mall.admin.dao.PmsProductCategoryAttributeRelationDao;
import com.zkc.mall.admin.dao.PmsProductCategoryDao;
import com.zkc.mall.admin.dto.PmsProductCategoryParam;
import com.zkc.mall.admin.dto.PmsProductCategoryWithChildrenItem;
import com.zkc.mall.admin.service.PmsProductCategoryService;
import com.zkc.mall.mbg.mapper.PmsProductCategoryAttributeRelationMapper;
import com.zkc.mall.mbg.mapper.PmsProductCategoryMapper;
import com.zkc.mall.mbg.mapper.PmsProductMapper;
import com.zkc.mall.mbg.model.*;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;
import java.util.List;

@Service
public class PmsProductCategoryServiceImpl implements PmsProductCategoryService {
	
	@Autowired
	private PmsProductCategoryMapper productCategoryMapper;
	@Autowired
	private PmsProductCategoryDao productCategoryDao;
	@Autowired
	private PmsProductMapper productMapper;
	@Autowired
	private PmsProductCategoryAttributeRelationDao productCategoryAttributeRelationDao;
	@Autowired
	private PmsProductCategoryAttributeRelationMapper productCategoryAttributeRelationMapper;
	
	@Override
	public int create(PmsProductCategoryParam productCategoryParam) {
		
		PmsProductCategory productCategory = new PmsProductCategory();
		productCategory.setProductCount(0);
		BeanUtil.copyProperties(productCategoryParam, productCategory);
		setCategory(productCategory);
		int count = productCategoryMapper.insertSelective(productCategory);
		
		//分类与属性关系
		List<Long> attributeIdList = productCategoryParam.getProductAttributeIdList();
		if (!CollectionUtil.isEmpty(attributeIdList)) {
			insertRelationList(productCategory.getId(), attributeIdList);
		}
		
		return count;
	}
	
	@Override
	public int update(Long id, PmsProductCategoryParam productCategoryParam) {
		PmsProductCategory productCategory = new PmsProductCategory();
		productCategory.setId(id);
		BeanUtil.copyProperties(productCategoryParam, productCategory);
		setCategory(productCategory);
		
		//更新商品的分类名称
		PmsProduct product = new PmsProduct();
		product.setProductCategoryName(productCategory.getName());
		PmsProductExample productExample = new PmsProductExample();
		productExample.createCriteria().andProductCategoryIdEqualTo(id);
		productMapper.updateByExampleSelective(product, productExample);
		
		//更新属性关系
		PmsProductCategoryAttributeRelationExample attributeRelationExample = new PmsProductCategoryAttributeRelationExample();
		attributeRelationExample.createCriteria().andProductCategoryIdEqualTo(id);
		productCategoryAttributeRelationMapper.deleteByExample(attributeRelationExample);
		if (!CollectionUtil.isEmpty(productCategoryParam.getProductAttributeIdList())) {
			insertRelationList(id, productCategoryParam.getProductAttributeIdList());
		}
		
		return productCategoryMapper.updateByPrimaryKeySelective(productCategory);
	}
	
	@Override
	public List<PmsProductCategory> getList(Long parentId, Integer pageSize, Integer pageNum) {
		PageHelper.startPage(pageNum, pageSize);
		PmsProductCategoryExample example = new PmsProductCategoryExample();
		example.setOrderByClause("sort desc");
		example.createCriteria().andParentIdEqualTo(parentId);
		return productCategoryMapper.selectByExample(example);
	}
	
	@Override
	public PmsProductCategory getItem(Long id) {
		return productCategoryMapper.selectByPrimaryKey(id);
	}
	
	@Override
	public int delete(Long id) {
		return productCategoryMapper.deleteByPrimaryKey(id);
	}
	
	@Override
	public int updateNavStatus(List<Long> ids, Integer navStatus) {
		PmsProductCategoryExample example = new PmsProductCategoryExample();
		example.createCriteria().andIdIn(ids);
		PmsProductCategory productCategory = new PmsProductCategory();
		productCategory.setNavStatus(navStatus);
		return productCategoryMapper.updateByExampleSelective(productCategory, example);
	}
	
	@Override
	public int updateShowStatus(List<Long> ids, Integer showStatus) {
		PmsProductCategoryExample example = new PmsProductCategoryExample();
		example.createCriteria().andIdIn(ids);
		PmsProductCategory productCategory = new PmsProductCategory();
		productCategory.setShowStatus(showStatus);
		return productCategoryMapper.updateByExampleSelective(productCategory, example);
	}
	
	@Override
	public List<PmsProductCategoryWithChildrenItem> listWithChildren() {
		return productCategoryDao.listWithChildren();
	}
	
	private void setCategory(PmsProductCategory productCategory) {
		productCategory.setLevel(0);
		if (productCategory.getParentId() == 0) {
			return;
		}
		PmsProductCategory parentCategory = productCategoryMapper.selectByPrimaryKey(productCategory.getParentId());
		if (parentCategory != null) {
			productCategory.setLevel(productCategory.getLevel() + 1);
		}
	}
	
	private void insertRelationList(Long id, List<Long> attributeIdList) {
		
		List<PmsProductCategoryAttributeRelation> list = new ArrayList<>();
		for (Long attrId : attributeIdList) {
			PmsProductCategoryAttributeRelation relation = new PmsProductCategoryAttributeRelation();
			relation.setProductCategoryId(id);
			relation.setProductAttributeId(attrId);
			list.add(relation);
		}
		productCategoryAttributeRelationDao.insertList(list);
	}
}
