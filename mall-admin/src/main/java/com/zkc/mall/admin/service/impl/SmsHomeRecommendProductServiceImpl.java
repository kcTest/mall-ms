package com.zkc.mall.admin.service.impl;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.zkc.mall.admin.service.SmsHomeRecommendProductService;
import com.zkc.mall.mbg.mapper.SmsHomeRecommendProductMapper;
import com.zkc.mall.mbg.model.SmsHomeRecommendProduct;
import com.zkc.mall.mbg.model.SmsHomeRecommendProductExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SmsHomeRecommendProductServiceImpl implements SmsHomeRecommendProductService {
	
	@Resource
	private SmsHomeRecommendProductMapper homeRecommendProductMapper;
	
	@Override
	public int create(List<SmsHomeRecommendProduct> homeRecommendProductList) {
		for (SmsHomeRecommendProduct smsHomeRecommendProduct : homeRecommendProductList) {
			smsHomeRecommendProduct.setRecommendStatus(1);
			smsHomeRecommendProduct.setSort(0);
			homeRecommendProductMapper.insert(smsHomeRecommendProduct);
		}
		return homeRecommendProductList.size();
	}
	
	@Override
	public int updateSort(Long id, Integer sort) {
		SmsHomeRecommendProduct homeRecommendProduct = new SmsHomeRecommendProduct();
		homeRecommendProduct.setId(id);
		homeRecommendProduct.setSort(sort);
		return homeRecommendProductMapper.updateByPrimaryKeySelective(homeRecommendProduct);
	}
	
	@Override
	public int delete(List<Long> ids) {
		SmsHomeRecommendProductExample smsHomeRecommendProductExample = new SmsHomeRecommendProductExample();
		smsHomeRecommendProductExample.createCriteria().andIdIn(ids);
		return homeRecommendProductMapper.deleteByExample(smsHomeRecommendProductExample);
	}
	
	@Override
	public int updateRecommendStatus(List<Long> ids, Integer recommendStatus) {
		
		SmsHomeRecommendProduct homeRecommendProduct = new SmsHomeRecommendProduct();
		homeRecommendProduct.setRecommendStatus(recommendStatus);
		
		SmsHomeRecommendProductExample smsHomeRecommendProductExample = new SmsHomeRecommendProductExample();
		smsHomeRecommendProductExample.createCriteria().andIdIn(ids);
		return homeRecommendProductMapper.updateByExample(homeRecommendProduct, smsHomeRecommendProductExample);
	}
	
	@Override
	public List<SmsHomeRecommendProduct> list(String ProductName, Integer recommendStatus, Integer pageSize, Integer pageNum) {
		PageHelper.startPage(pageNum, pageSize);
		SmsHomeRecommendProductExample example = new SmsHomeRecommendProductExample();
		SmsHomeRecommendProductExample.Criteria criteria = example.createCriteria();
		if (!StrUtil.isEmpty(ProductName)) {
			criteria.andProductNameLike("%" + ProductName + "%");
		}
		if (recommendStatus != null) {
			criteria.andRecommendStatusEqualTo(recommendStatus);
		}
		example.setOrderByClause("sort desc");
		return homeRecommendProductMapper.selectByExample(example);
	}
	
}
