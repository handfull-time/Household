package com.utime.household.user.vo;

public enum EJwtRole {
    Admin("Administrator"),
    User("Normal User");

    private final String dscr;
    
    private EJwtRole(String s) {
		this.dscr = s;
	}
    
    public String getDscr() {
		return dscr;
	}
    
}