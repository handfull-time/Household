package com.utime.household.environment.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import com.utime.household.dataIO.vo.InputBankCardItem;
import com.utime.household.dataIO.vo.InputBankCardList;

@Deprecated
public class InputBcTypeHandler implements TypeHandler<InputBankCardItem>{
	
	private static InputBankCardItem getBankCard( String s ) {
//		final InputBankCardItem result = InputBankCardList.getItem(s);
//		
//		if( result == null )
//			throw new IllegalArgumentException("Unexpected value: " + s);
//		
//		return result;
		return null;
	}
	
	@Override
	public InputBankCardItem getResult(ResultSet arg0, String arg1) throws SQLException {
		return InputBcTypeHandler.getBankCard(arg0.getString(arg1));
	}

	@Override
	public InputBankCardItem getResult(ResultSet arg0, int arg1) throws SQLException {
		return InputBcTypeHandler.getBankCard(arg0.getString(arg1));
	}

	@Override
	public InputBankCardItem getResult(CallableStatement arg0, int arg1) throws SQLException {
		return InputBcTypeHandler.getBankCard(arg0.getString(arg1));
	}

	@Override
	public void setParameter(PreparedStatement arg0, int arg1, InputBankCardItem arg2, JdbcType arg3)throws SQLException {
//		arg0.setString(arg1, arg2.getName());
	}
}