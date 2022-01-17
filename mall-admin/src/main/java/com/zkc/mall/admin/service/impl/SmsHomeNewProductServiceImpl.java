package com.zkc.mall.admin.service.impl;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.zkc.mall.admin.service.SmsHomeNewProductService;
import com.zkc.mall.mbg.mapper.SmsHomeNewProductMapper;
import com.zkc.mall.mbg.model.SmsHomeNewProduct;
import com.zkc.mall.mbg.model.SmsHomeNewProductExample;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@Service
public class SmsHomeNewProductServiceImpl implements SmsHomeNewProductService {
	
	@Autowired
	private SmsHomeNewProductMapper homeNewProductMapper;
	
	@Override
	public int create(List<SmsHomeNewProduct> homeNewProductList) {
		for (SmsHomeNewProduct smsHomeNewProduct : homeNewProductList) {
			smsHomeNewProduct.setRecommendStatus(1);
			smsHomeNewProduct.setSort(0);
			homeNewProductMapper.insert(smsHomeNewProduct);
		}
		return homeNewProductList.size();
	}
	
	@Override
	public int updateSort(Long id, Integer sort) {
		SmsHomeNewProduct homeNewProduct = new SmsHomeNewProduct();
		homeNewProduct.setId(id);
		homeNewProduct.setSort(sort);
		return homeNewProductMapper.updateByPrimaryKeySelective(homeNewProduct);
	}
	
	@Override
	public int delete(List<Long> ids) {
		SmsHomeNewProductExample smsHomeNewProductExample = new SmsHomeNewProductExample();
		smsHomeNewProductExample.createCriteria().andIdIn(ids);
		return homeNewProductMapper.deleteByExample(smsHomeNewProductExample);
	}
	
	@Override
	public int updateRecommendStatus(List<Long> ids, Integer recommendStatus) {
		
		SmsHomeNewProduct homeNewProduct = new SmsHomeNewProduct();
		homeNewProduct.setRecommendStatus(recommendStatus);
		
		SmsHomeNewProductExample smsHomeNewProductExample = new SmsHomeNewProductExample();
		smsHomeNewProductExample.createCriteria().andIdIn(ids);
		return homeNewProductMapper.updateByExample(homeNewProduct, smsHomeNewProductExample);
	}
	
	@Override
	public List<SmsHomeNewProduct> list(String ProductName, Integer recommendStatus, Integer pageSize, Integer pageNum) {
		PageHelper.startPage(pageNum, pageSize);
		SmsHomeNewProductExample example = new SmsHomeNewProductExample();
		SmsHomeNewProductExample.Criteria criteria = example.createCriteria();
		if (!StrUtil.isEmpty(ProductName)) {
			criteria.andProductNameLike("%" + ProductName + "%");
		}
		if (recommendStatus != null) {
			criteria.andRecommendStatusEqualTo(recommendStatus);
		}
		example.setOrderByClause("sort desc");
		return homeNewProductMapper.selectByExample(example);
	}
	
}
