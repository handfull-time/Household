package com.utime.household.dataIO.vo;

import com.utime.household.environment.vo.BankCardVO;

public final class InputBankCardList {
	
//	final static Map<EBankKind, String> mapBank = new HashMap<>();
//	final static Map<ECardCompany, String> mapCard = new HashMap<>();
//	final static String ibUnknown;
	
	static {
//		mapBank.put(EBankKind.KB, InputBankCardDefine.NameKbBank);
//		mapBank.put(EBankKind.Kdb, InputBankCardDefine.NameKdb);
//		mapCard.put(ECardCompany.Samsung, InputBankCardDefine.NameSamsung);
//		mapCard.put(ECardCompany.Shinhan, InputBankCardDefine.NameShinhan);
//		mapCard.put(ECardCompany.EumIncheon, InputBankCardDefine.NameIncheonEum);
//		
//		ibUnknown = InputBankCardDefine.NameUnknown;
	}
	
	
	public static String getBeanName( BankCardVO bcVo ) {
//		String result;
//		if( bcVo.getBc() == EBankCard.Bank ) {
//			result = InputBankCardList.getBeanName(bcVo.getBank().getBankKind());
//		}else {
//			result = InputBankCardList.getBeanName(bcVo.getCard().getCardCompany());
//		}
//		
//		return result;
		return "";
	}
//	
//	private static String getBeanName( EBankKind name ) {
//		
//		String result = InputBankCardList.ibUnknown;
//
//		if( HouseholdUtils.isEmpty(name) )
//			return result; 
//		
//		if( InputBankCardList.mapBank.containsKey(name) ) {
//			result = InputBankCardList.mapBank.get(name);
//		}
//		
//		
//		return result;
//	}
//
//	private static String getBeanName( ECardCompany name ) {
//		
//		String result = InputBankCardList.ibUnknown;
//
//		if( HouseholdUtils.isEmpty(name) )
//			return result; 
//
//		if( InputBankCardList.mapCard.containsKey(name) ) {
//			result = InputBankCardList.mapCard.get(name);
//		}
//		
//		return result;
//	}
}
