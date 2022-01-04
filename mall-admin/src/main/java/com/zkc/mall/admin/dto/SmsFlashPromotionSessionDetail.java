package com.zkc.mall.admin.dto;

import com.zkc.mall.mbg.model.SmsFlashPromotionSession;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


public class SmsFlashPromotionSessionDetail extends SmsFlashPromotionSession {
	
	@Getter
	@Setter
	@ApiModelProperty
	private Long productCount;
}
