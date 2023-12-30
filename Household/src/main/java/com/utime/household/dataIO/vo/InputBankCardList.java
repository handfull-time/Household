package com.utime.household.dataIO.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.utime.household.environment.vo.EBankCard;
import com.utime.household.environment.vo.EBankKind;
import com.utime.household.environment.vo.ECardCompany;

public final class InputBankCardList {
	
	final static List<InputBankCardItem> list = new ArrayList<>();
	final static Map<EBankKind, InputBankCardItem> mapBank = new HashMap<>();
	final static Map<ECardCompany, InputBankCardItem> mapCard = new HashMap<>();
	
	static {
		list.add( new InputBankCardItem(EBankKind.KB, InputBankCardDefine.NameKbBank) );
		list.add( new InputBankCardItem(EBankKind.Kdb, InputBankCardDefine.NameKdb) );
		list.add( new InputBankCardItem(ECardCompany.Samsung, InputBankCardDefine.NameSamsung) );
		list.add( new InputBankCardItem(ECardCompany.Shinhan, InputBankCardDefine.NameShinhan) );
		list.add( new InputBankCardItem(ECardCompany.EumIncheon, InputBankCardDefine.NameIncheonEum) );
		
		for( InputBankCardItem item : list ) {
			if( item.getBc() == EBankCard.Bank )
				mapBank.put(item.getBk(), item);
			else
				mapCard.put(item.getCk(), item);
		}
	}
	
//	public static List<InputBankCardItem> values() {
//		return InputBankCardList.list;
//	}
//	
//	public static List<InputBankCardItem> values(EBankCard bc) {
//		final List<InputBankCardItem> result = new ArrayList<>();
//
//		for( InputBankCardItem item : list ) {
//			if( item.getBc() == bc )
//			result.add(item);
//		}
//		
//		return result;
//	}
	
	public static InputBankCardItem getItem( EBankKind name ) {
		
		InputBankCardItem result = null;

		if( name == null )
			return result; 
		
		result = mapBank.get(name);
		
		return result;
	}

	public static InputBankCardItem getItem( ECardCompany name ) {
		
		InputBankCardItem result = null;

		if( name == null )
			return result; 
		
		result = mapCard.get(name);
		
		return result;
	}
}
