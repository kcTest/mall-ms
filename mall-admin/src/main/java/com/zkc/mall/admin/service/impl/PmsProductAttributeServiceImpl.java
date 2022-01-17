package com.zkc.mall.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.zkc.mall.admin.dao.PmsProductAttributeDao;
import com.zkc.mall.admin.dto.PmsProductAttributeParam;
import com.zkc.mall.admin.dto.ProductAttrInfo;
import com.zkc.mall.admin.service.PmsProductAttributeService;
import com.zkc.mall.mbg.mapper.PmsProductAttributeCategoryMapper;
import com.zkc.mall.mbg.mapper.PmsProductAttributeMapper;
import com.zkc.mall.mbg.model.PmsProductAttribute;
import com.zkc.mall.mbg.model.PmsProductAttributeCategory;
import com.zkc.mall.mbg.model.PmsProductAttributeExample;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@Service
public class PmsProductAttributeServiceImpl implements PmsProductAttributeService {
	
	@Autowired
	private PmsProductAttributeMapper productAttributeMapper;
	@Autowired
	private PmsProductAttributeDao productAttributeDao;
	@Autowired
	private PmsProductAttributeCategoryMapper productAttributeCategoryMapper;
	
	@Override
	public List<PmsProductAttribute> getList(Long cid, Integer type, Integer pageSize, Integer pageNum) {
		PageHelper.startPage(pageNum, pageSize);
		PmsProductAttributeExample example = new PmsProductAttributeExample();
		example.createCriteria().andTypeEqualTo(type).andProductAttributeCategoryIdEqualTo(cid);
		example.setOrderByClause("sort desc");
		return productAttributeMapper.selectByExample(example);
	}
	
	@Override
	public int create(PmsProductAttributeParam productAttributeParam) {
		PmsProductAttribute productAttribute = new PmsProductAttribute();
		BeanUtils.copyProperties(productAttributeParam, productAttribute);
		int count = productAttributeMapper.insertSelective(productAttributeParam);
		
		//更新分类下属性或参数的数量
		PmsProductAttributeCategory productAttributeCategory = productAttributeCategoryMapper.selectByPrimaryKey(productAttribute.getProductAttributeCategoryId());
		if (productAttribute.getType() == 0) {
			productAttributeCategory.setAttributeCount(productAttributeCategory.getAttributeCount() + 1);
		} else {
			productAttributeCategory.setParamCount(productAttributeCategory.getParamCount() + 1);
		}
		productAttributeCategoryMapper.updateByPrimaryKey(productAttributeCategory);
		
		return count;
	}
	
	@Override
	public int update(Long id, PmsProductAttributeParam productAttributeParam) {
		PmsProductAttribute productAttribute = new PmsProductAttribute();
		productAttribute.setId(id);
		BeanUtils.copyProperties(productAttributeParam, productAttribute);
		return productAttributeMapper.updateByPrimaryKeySelective(productAttribute);
	}
	
	@Override
	public PmsProductAttribute getItem(Long id) {
		return productAttributeMapper.selectByPrimaryKey(id);
	}
	
	@Override
	public int delete(List<Long> ids) {
		
		PmsProductAttributeExample example = new PmsProductAttributeExample();
		example.createCriteria().andIdIn(ids);
		int count = productAttributeMapper.deleteByExample(example);
		
		//更新分类的属性或参数数量
		PmsProductAttribute productAttribute = productAttributeMapper.selectByPrimaryKey(ids.get(0));
		Long categoryId = productAttribute.getProductAttributeCategoryId();
		Integer type = productAttribute.getType();
		PmsProductAttributeCategory category = productAttributeCategoryMapper.selectByPrimaryKey(categoryId);
		if (type == 0) {
			category.setAttributeCount(category.getAttributeCount() - count > 0 ? category.getAttributeCount() - count : 0);
		} else {
			category.setParamCount(category.getParamCount() - count > 0 ? category.getParamCount() - count : 0);
		}
		productAttributeCategoryMapper.updateByPrimaryKeySelective(category);
		
		return count;
	}
	
	@Override
	public List<ProductAttrInfo> getAttrInfo(Long productCategoryId) {
		return productAttributeDao.getAttrInfo(productCategoryId);
	}
}
