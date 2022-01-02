package com.zkc.mall.common.constant;

public final class AuthConstant {
	
	public AuthConstant() {
	}
	
	public static final String JWT_ALIAS = "jwt";
	public static final String JWT_KEY_PASS = "123456";
	public static final String JWT_STORE_PASS = JWT_KEY_PASS;
	
	
	public static final String AUTH_CLIENT_ID = "client_id";
	public static final String AUTH_CLIENT_ID_DESC = "Oauth2客户端ID";
	public static final String AUTH_CLIENT_SECRETE = "client_secrete";
	public static final String AUTH_CLIENT_SECRETE_DESC = "Oauth2客户端秘钥";
	public static final String AUTH_CLIENT_SECRETE_DEFAULT = "123456";
	public static final String AUTH_GRANT_TYPE = "grant_type";
	public static final String AUTH_GRANT_TYPE_DESC = "授权模式";
	public static final String AUTH_GRANT_TYPE_DEFAULT = "password";
	public static final String AUTH_REFRESH_TOKEN = "refresh_token";
	public static final String AUTH_REFRESH_TOKEN_DESC = "用于刷新TOKEN的令牌";
	public static final String AUTH_USERNAME = "username";
	public static final String AUTH_USERNAME_DESC = "登录用户名";
	public static final String AUTH_PASSWORD = "password";
	public static final String AUTH_PASSWORD_DESC = "登录密码";
	
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