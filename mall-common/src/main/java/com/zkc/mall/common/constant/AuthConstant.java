package com.zkc.mall.common.constant;

public final class AuthConstant {
	
	public AuthConstant() {
	}
	
	public static final String AUTHORITY_PREFIX = "ROLE_";
	public static final String AUTHORITY_CLAIM_NAME = "authorities";
	public static final String ADMIN_CLIENT_ID = "admin-app";
	public static final String PORTAL_CLIENT_ID = "portal-app";
	public static final String ADMIN_URL_PATTERN = "/mall-admin/**";
	public static final String RESOURCE_ROLES_MAP_KEY = "auth:resourceRoleMap";
	public static final String JWT_TOKEN_HEADER = "Authorization";
	public static final String JWT_TOKEN_PREFIX = "Bearer";
	public static final String USER_TOKEN_HEADER = "user";
}