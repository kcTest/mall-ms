package com.zkc.mall.admin.dao;

import com.zkc.mall.admin.dto.OmsOrderReturnApplyQueryParam;
import com.zkc.mall.mbg.model.OmsOrderReturnApply;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OmsOrderReturnApplyDao {
	
	List<OmsOrderReturnApply> getList(@Param("returnApplyParam") OmsOrderReturnApplyQueryParam returnApplyParam);
	
}
