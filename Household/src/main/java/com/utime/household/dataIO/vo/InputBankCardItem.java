package com.utime.household.dataIO.vo;

import com.utime.household.config.vo.EBankCard;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class InputBankCardItem {
	
	private final String name;
	
	private final EBankCard bc;
	
	private final String bean;
	
	private final String dscr;
	
	public InputBankCardItem(String name) {
		this(name, null, null, null);
	}
	
	public InputBankCardItem(String name, EBankCard bc, String bean, String dscr) {
		this.name = name;
		this.bc = bc;
		this.bean = bean;
		this.dscr = dscr;
	}
}
