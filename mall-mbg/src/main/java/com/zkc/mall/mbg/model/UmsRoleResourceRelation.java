package com.zkc.mall.mbg.model;


import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;

/**
 * ums_role_resource_relation
 * @author 
 */
@Schema(description="com.zkc.mall.mbg.model.UmsRoleResourceRelation后台角色资源关系表")
public class UmsRoleResourceRelation implements Serializable {
    private Long id;

    /**
     * 角色ID
     */
   @Schema(description="角色ID")
    private Long roleId;

    /**
     * 资源ID
     */
   @Schema(description="资源ID")
    private Long resourceId;

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

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }
}