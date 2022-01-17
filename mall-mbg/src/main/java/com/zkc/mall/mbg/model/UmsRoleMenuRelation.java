package com.zkc.mall.mbg.model;


import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;

/**
 * ums_role_menu_relation
 * @author 
 */
@Schema(description="com.zkc.mall.mbg.model.UmsRoleMenuRelation后台角色菜单关系表")
public class UmsRoleMenuRelation implements Serializable {
    private Long id;

    /**
     * 角色ID
     */
   @Schema(description="角色ID")
    private Long roleId;

    /**
     * 菜单ID
     */
   @Schema(description="菜单ID")
    private Long menuId;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }
}