package com.zkc.mall.mbg.model;

import io.swagger.annotations.ApiModel;

import java.io.Serializable;

/**
 * ums_admin_role_relation
 * @author 
 */
@ApiModel(value="com.zkc.mall.mbg.model.UmsAdminRoleRelation后台用户和角色关系表")
public class UmsAdminRoleRelation implements Serializable {
    private Long id;

    private Long adminId;

    private Long roleId;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}