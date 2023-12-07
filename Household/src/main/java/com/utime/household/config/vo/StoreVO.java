package com.utime.household.config.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString(callSuper = true)
public class StoreVO extends BasicItemVO{

	/** 카테고리 번호 */
	long categoryNo = -1L;
	
	/** 내역에서 전달받은 실제 이름 */
	String store;

	public StoreVO() {
		super();
	}
	
	public StoreVO(String name) {
		super(-1, name);
	}
	
	public StoreVO(String name, long cn) {
		super(-1, name);
		this.categoryNo = cn;
	}
}
