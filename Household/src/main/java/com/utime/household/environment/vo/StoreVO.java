package com.utime.household.environment.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 사용처 정보
 */
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
	
	/**
	 * 이것은 새로 추가할 데이터 인가?
	 * @return true:그렇다 새로 추가다.
	 */	
	public boolean isNew() {
		return this.categoryNo > -1 && this.no < 0 && this.name != null && ! "".equals(this.name ); 
	}
}
