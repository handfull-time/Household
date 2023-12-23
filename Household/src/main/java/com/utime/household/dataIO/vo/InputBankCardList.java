package com.utime.household.dataIO.vo;

import java.util.ArrayList;
import java.util.List;

import com.utime.household.config.vo.EBankCard;

public final class InputBankCardList {
	
	final static List<InputBankCardItem> list = new ArrayList<>();
	
	static {
		list.add( new InputBankCardItem("KbBank", EBankCard.Bank, InputBankCardDefine.NameKbBank,"국민은행") );
		list.add( new InputBankCardItem("Kdb", EBankCard.Bank, InputBankCardDefine.NameKdb, "산업은행") );
		list.add( new InputBankCardItem("Samsung", EBankCard.Card, InputBankCardDefine.NameSamsung, "삼성카드") );
		list.add( new InputBankCardItem("Shinhan", EBankCard.Card, InputBankCardDefine.NameShinhan, "신한카드") );
		list.add( new InputBankCardItem("IncheonEum", EBankCard.Card, InputBankCardDefine.NameIncheonEum, "인천-이음카드") );
	}
	
	public static List<InputBankCardItem> valuse() {
		return InputBankCardList.list;
	}
	
	public static InputBankCardItem getItem( String name ) {
		
		InputBankCardItem result = null;

		if( name == null )
			return result; 
		
		
		for( InputBankCardItem item : list) {
			if( name.equals(item.getName()) ) {
				result = item;
				break;
			}
		}
		
		return result;
	}
}
