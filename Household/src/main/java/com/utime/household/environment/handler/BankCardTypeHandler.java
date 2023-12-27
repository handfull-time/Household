package com.utime.household.environment.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import com.utime.household.environment.vo.EBankCard;

public class BankCardTypeHandler implements TypeHandler<EBankCard>{
	
	private static EBankCard getBankCard( String s ) {
		EBankCard result;
		switch (s) {
		case "B": result = EBankCard.Bank; break;
		case "C": result = EBankCard.Card; break;
		case "O": result = EBankCard.Other; break;
		default:
			throw new IllegalArgumentException("Unexpected value: " + s);
		}
		
		return result;
	}
	
	@Override
	public EBankCard getResult(ResultSet arg0, String arg1) throws SQLException {
		return BankCardTypeHandler.getBankCard(arg0.getString(arg1));
	}

	@Override
	public EBankCard getResult(ResultSet arg0, int arg1) throws SQLException {
		return BankCardTypeHandler.getBankCard(arg0.getString(arg1));
	}

	@Override
	public EBankCard getResult(CallableStatement arg0, int arg1) throws SQLException {
		return BankCardTypeHandler.getBankCard(arg0.getString(arg1));
	}

	@Override
	public void setParameter(PreparedStatement arg0, int arg1, EBankCard arg2, JdbcType arg3)throws SQLException {
		arg0.setString(arg1, arg2.getCode());
	}
}