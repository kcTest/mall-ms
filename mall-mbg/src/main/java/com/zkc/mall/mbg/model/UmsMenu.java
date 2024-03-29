package com.zkc.mall.mbg.model;


import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Date;

/**
 * ums_menu
 * @author 
 */
@Schema(description="com.zkc.mall.mbg.model.UmsMenu后台菜单表")
public class UmsMenu implements Serializable {
    private Long id;

    /**
     * 父级ID
     */
   @Schema(description="父级ID")
    private Long parentId;

    /**
     * 创建时间
     */
   @Schema(description="创建时间")
    private Date createTime;

    /**
     * 菜单名称
     */
   @Schema(description="菜单名称")
    private String title;

    /**
     * 菜单级数
     */
   @Schema(description="菜单级数")
    private Integer level;

    /**
     * 菜单排序
     */
   @Schema(description="菜单排序")
    private Integer sort;

    /**
     * 前端名称
     */
   @Schema(description="前端名称")
    private String name;

    /**
     * 前端图标
     */
   @Schema(description="前端图标")
    private String icon;

    /**
     * 前端隐藏
     */
   @Schema(description="前端隐藏")
    private Integer hidden;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getHidden() {
        return hidden;
    }

    public void setHidden(Integer hidden) {
        this.hidden = hidden;
    }
}