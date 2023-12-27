package com.utime.household.dataIO.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.utime.household.environment.vo.EBankCard;

public final class InputBankCardList {
	
	final static List<InputBankCardItem> list = new ArrayList<>();
	final static Map<String, InputBankCardItem> map = new HashMap<>();
	
	static {
		list.add( new InputBankCardItem("KbBank", EBankCard.Bank, InputBankCardDefine.NameKbBank,"국민은행") );
		list.add( new InputBankCardItem("Kdb", EBankCard.Bank, InputBankCardDefine.NameKdb, "산업은행") );
		list.add( new InputBankCardItem("Samsung", EBankCard.Card, InputBankCardDefine.NameSamsung, "삼성카드") );
		list.add( new InputBankCardItem("Shinhan", EBankCard.Card, InputBankCardDefine.NameShinhan, "신한카드") );
		list.add( new InputBankCardItem("IncheonEum", EBankCard.Card, InputBankCardDefine.NameIncheonEum, "인천-이음카드") );
		
		for( InputBankCardItem item : list ) {
			map.put(item.getName(), item);
		}
	}
	
	public static List<InputBankCardItem> valuse() {
		return InputBankCardList.list;
	}
	
	public static InputBankCardItem getItem( String name ) {
		
		InputBankCardItem result = null;

		if( name == null )
			return result; 
		
		result = map.get(name);
		
		return result;
	}
}
