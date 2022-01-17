package com.zkc.mall.admin.dto;

import com.zkc.mall.mbg.model.SmsFlashPromotionSession;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;


public class SmsFlashPromotionSessionDetail extends SmsFlashPromotionSession {
	
	@Getter
	@Setter
	@Schema
	private Long productCount;
}
