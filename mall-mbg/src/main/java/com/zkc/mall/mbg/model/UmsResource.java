package com.zkc.mall.mbg.model;


import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Date;

/**
 * ums_resource
 * @author 
 */
@Schema(description="com.zkc.mall.mbg.model.UmsResource后台资源表")
public class UmsResource implements Serializable {
    private Long id;

    /**
     * 创建时间
     */
   @Schema(description="创建时间")
    private Date createTime;

    /**
     * 资源名称
     */
   @Schema(description="资源名称")
    private String name;

    /**
     * 资源URL
     */
   @Schema(description="资源URL")
    private String url;

    /**
     * 描述
     */
   @Schema(description="描述")
    private String description;

    /**
     * 资源分类ID
     */
   @Schema(description="资源分类ID")
    private Long categoryId;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}