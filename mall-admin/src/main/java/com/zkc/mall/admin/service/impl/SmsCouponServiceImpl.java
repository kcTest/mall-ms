package com.zkc.mall.admin.service.impl;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.zkc.mall.admin.dao.SmsCouponDao;
import com.zkc.mall.admin.dao.SmsCouponProductCategoryRelationDao;
import com.zkc.mall.admin.dao.SmsCouponProductRelationDao;
import com.zkc.mall.admin.dto.SmsCouponParam;
import com.zkc.mall.admin.service.SmsCouponService;
import com.zkc.mall.mbg.mapper.SmsCouponMapper;
import com.zkc.mall.mbg.mapper.SmsCouponProductCategoryRelationMapper;
import com.zkc.mall.mbg.mapper.SmsCouponProductRelationMapper;
import com.zkc.mall.mbg.model.*;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@Service
public class SmsCouponServiceImpl implements SmsCouponService {
	
	@Autowired
	private SmsCouponMapper couponMapper;
	@Autowired
	private SmsCouponDao couponDao;
	@Autowired
	private SmsCouponProductRelationMapper couponProductRelationMapper;
	@Autowired
	private SmsCouponProductRelationDao couponProductRelationDao;
	@Autowired
	private SmsCouponProductCategoryRelationMapper couponProductCategoryRelationMapper;
	@Autowired
	private SmsCouponProductCategoryRelationDao couponProductCategoryRelationDao;
	
	@Override
	public int create(SmsCouponParam couponParam) {
		couponParam.setCount(couponParam.getPublishCount());
		couponParam.setUseCount(0);
		couponParam.setReceiveCount(0);
		//插入优惠券
		int count = couponMapper.insert(couponParam);
		//插入优惠券与商品的关系
		if (couponParam.getUseType().equals(2)) {
			List<SmsCouponProductRelation> productRelationList = couponParam.getProductRelationList();
			for (SmsCouponProductRelation couponProductRelation : productRelationList) {
				couponProductRelation.setCouponId(couponParam.getId());
			}
			couponProductRelationDao.insertList(productRelationList);
		}
		//插入优惠券与商品分类的关系
		if (couponParam.getUseType().equals(1)) {
			List<SmsCouponProductCategoryRelation> couponProductCategoryRelationList = couponParam.getProductCategoryRelationList();
			for (SmsCouponProductCategoryRelation couponProductCategoryRelation : couponProductCategoryRelationList) {
				couponProductCategoryRelation.setCouponId(couponParam.getId());
			}
			couponProductCategoryRelationDao.insertList(couponProductCategoryRelationList);
		}
		
		return count;
	}
	
	@Override
	public int delete(Long id) {
		int count = couponMapper.deleteByPrimaryKey(id);
		deleteProductRelation(id);
		deleteProductCategoryRelation(id);
		return count;
	}
	
	@Override
	public int update(Long id, SmsCouponParam couponParam) {
		couponParam.setId(id);
		int count = couponMapper.updateByPrimaryKeySelective(couponParam);
		//更新关系
		if (couponParam.getType().equals(2)) {
			List<SmsCouponProductRelation> productRelationList = couponParam.getProductRelationList();
			for (SmsCouponProductRelation relation : productRelationList) {
				relation.setCouponId(id);
			}
			deleteProductRelation(id);
			couponProductRelationDao.insertList(productRelationList);
		}
		if (couponParam.getType().equals(0)) {
			List<SmsCouponProductCategoryRelation> categoryRelationList = couponParam.getProductCategoryRelationList();
			for (SmsCouponProductCategoryRelation relation : categoryRelationList) {
				relation.setId(id);
			}
			deleteProductCategoryRelation(id);
			couponProductCategoryRelationDao.insertList(categoryRelationList);
		}
		
		return count;
	}
	
	@Override
	public List<SmsCoupon> list(String name, Integer type, Integer pageSize, Integer pageNum) {
		PageHelper.startPage(pageNum, pageSize);
		SmsCouponExample example = new SmsCouponExample();
		if (StrUtil.isNotEmpty(name)) {
			example.createCriteria().andNameLike("%" + name + "%");
		}
		if (type != null) {
			example.createCriteria().andTypeEqualTo(type);
		}
		return couponMapper.selectByExample(example);
	}
	
	@Override
	public SmsCouponParam getItem(Long id) {
		return couponDao.getItem(id);
	}
	
	private void deleteProductRelation(Long couponId) {
		SmsCouponProductRelationExample example = new SmsCouponProductRelationExample();
		example.createCriteria().andCouponIdEqualTo(couponId);
		couponProductRelationMapper.deleteByExample(example);
	}
	
	private void deleteProductCategoryRelation(Long couponId) {
		SmsCouponProductCategoryRelationExample example = new SmsCouponProductCategoryRelationExample();
		example.createCriteria().andCouponIdEqualTo(couponId);
		couponProductCategoryRelationMapper.deleteByExample(example);
	}
	
}
