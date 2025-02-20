package com.utime.household.user.vo;

public enum EJwtRule {
    JWT_ISSUE_HEADER("Set-Cookie"),
    JWT_RESOLVE_HEADER("Cookie"),
    ACCESS_PREFIX("access"),
    REFRESH_PREFIX("refresh");

    private final String value;
    
    private EJwtRule(String s) {
		this.value = s;
	}
    
    public String getValue() {
		return value;
	}
}