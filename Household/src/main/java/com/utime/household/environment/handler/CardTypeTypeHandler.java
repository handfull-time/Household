package com.utime.household.environment.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import com.utime.household.environment.vo.ECardType;

public class CardTypeTypeHandler implements TypeHandler<ECardType>{
	
	private static ECardType getCardType( String s ) {
		ECardType result;
		switch (s) {
		case "C": result = ECardType.Credit; break;
		case "H": result = ECardType.Check; break;
		case "P": result = ECardType.Prepaid; break;
		case "G": result = ECardType.Gift; break;
		case "I": result = ECardType.Point; break;
		case "O": result = ECardType.Other; break;
		default:
			throw new IllegalArgumentException("Unexpected value: " + s);
		}
		
		return result;
	}
	
	@Override
	public ECardType getResult(ResultSet arg0, String arg1) throws SQLException {
		return CardTypeTypeHandler.getCardType(arg0.getString(arg1));
	}

	@Override
	public ECardType getResult(ResultSet arg0, int arg1) throws SQLException {
		return CardTypeTypeHandler.getCardType(arg0.getString(arg1));
	}

	@Override
	public ECardType getResult(CallableStatement arg0, int arg1) throws SQLException {
		return CardTypeTypeHandler.getCardType(arg0.getString(arg1));
	}

	@Override
	public void setParameter(PreparedStatement arg0, int arg1, ECardType arg2, JdbcType arg3)throws SQLException {
		arg0.setString(arg1, arg2.getCode());
	}
}