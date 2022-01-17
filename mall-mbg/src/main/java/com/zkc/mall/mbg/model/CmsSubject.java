package com.zkc.mall.mbg.model;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.util.Date;

/**
 * cms_subject
 *
 * @author
 */
@Schema(description = "com.zkc.mall.mbg.model.CmsSubject专题表")
public class CmsSubject implements Serializable {
	private Long id;
	
	private Long categoryId;
	
	private String title;
	
	/**
	 * 专题主图
	 */
	@Schema(description = "专题主图")
	private String pic;
	
	/**
	 * 关联产品数量
	 */
	@Schema(description = "关联产品数量")
	private Integer productCount;
	
	private Integer recommendStatus;
	
	private Date createTime;
	
	private Integer collectCount;
	
	private Integer readCount;
	
	private Integer commentCount;
	
	/**
	 * 画册图片用逗号分割
	 */
	@Schema(description = "画册图片用逗号分割")
	private String albumPics;
	
	private String description;
	
	/**
	 * 显示状态：0->不显示；1->显示
	 */
	@Schema(description = "显示状态：0->不显示；1->显示")
	private Integer showStatus;
	
	private String content;
	
	/**
	 * 转发数
	 */
	@Schema(description = "转发数")
	private Integer forwardCount;
	
	/**
	 * 专题分类名称
	 */
	@Schema(description= "专题分类名称")
	private String categoryName;
	
	private static final long serialVersionUID = 1L;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getCategoryId() {
		return categoryId;
	}
	
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getPic() {
		return pic;
	}
	
	public void setPic(String pic) {
		this.pic = pic;
	}
	
	public Integer getProductCount() {
		return productCount;
	}
	
	public void setProductCount(Integer productCount) {
		this.productCount = productCount;
	}
	
	public Integer getRecommendStatus() {
		return recommendStatus;
	}
	
	public void setRecommendStatus(Integer recommendStatus) {
		this.recommendStatus = recommendStatus;
	}
	
	public Date getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public Integer getCollectCount() {
		return collectCount;
	}
	
	public void setCollectCount(Integer collectCount) {
		this.collectCount = collectCount;
	}
	
	public Integer getReadCount() {
		return readCount;
	}
	
	public void setReadCount(Integer readCount) {
		this.readCount = readCount;
	}
	
	public Integer getCommentCount() {
		return commentCount;
	}
	
	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}
	
	public String getAlbumPics() {
		return albumPics;
	}
	
	public void setAlbumPics(String albumPics) {
		this.albumPics = albumPics;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Integer getShowStatus() {
		return showStatus;
	}
	
	public void setShowStatus(Integer showStatus) {
		this.showStatus = showStatus;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public Integer getForwardCount() {
		return forwardCount;
	}
	
	public void setForwardCount(Integer forwardCount) {
		this.forwardCount = forwardCount;
	}
	
	public String getCategoryName() {
		return categoryName;
	}
	
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
}