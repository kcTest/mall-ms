package com.zkc.mall.admin.dao;

import com.zkc.mall.admin.dto.SmsCouponParam;
import org.apache.ibatis.annotations.Param;

public interface SmsCouponDao {
	
	SmsCouponParam getItem(@Param("id") Long id);
}
