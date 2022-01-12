package com.zkc.mall.portal.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * 用户收藏的商品
 */
@Getter
@Setter
@Document
public class MemberProductCollection {
	
	@Id
	private String id;
	
	@Indexed
	private Long memberId;
	private String memberNickname;
	private String memberIcon;
	
	@Indexed
	private Long productId;
	private String productName;
	private String productPic;
	private String productSubtitle;
	private String productPrice;
	private Date createTime;
}
