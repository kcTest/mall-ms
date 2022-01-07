package com.zkc.mall.admin.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OmsOrderReturnApplyQueryParam {
	
	@ApiModelProperty(value = "服务单号")
	private Long id;
	
	@ApiModelProperty(value = "收货人姓名/号码")
	private String receiveKeyword;
	
	@ApiModelProperty("申请状态: 0->待处理 1->退货中 2->已完成 3->已拒绝")
	private Integer status;
	
	@ApiModelProperty("申请时间")
	private String createTime;
	
	@ApiModelProperty("处理人员")
	private String handleMan;
	
	@ApiModelProperty("处理时间")
	private String handleTime;
	
}
