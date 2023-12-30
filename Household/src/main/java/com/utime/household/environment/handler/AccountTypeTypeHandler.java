package com.utime.household.environment.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import com.utime.household.common.util.HouseholdUtils;
import com.utime.household.environment.vo.EAccountType;

public class AccountTypeTypeHandler implements TypeHandler<EAccountType>{
	
	private static EAccountType getAccountType( String s ) {
		
		if( HouseholdUtils.isEmpty(s) ) {
			return null;
		}
		
		EAccountType result;
		switch (s) {
		case "O": result = EAccountType.Ordinary; break;
		case "T": result = EAccountType.Term; break;
		case "R": result = EAccountType.Regular; break;
		case "F": result = EAccountType.Free; break;
		default:
			throw new IllegalArgumentException("Unexpected value: " + s);
		}
		
		return result;
	}
	
	@Override
	public EAccountType getResult(ResultSet arg0, String arg1) throws SQLException {
		return AccountTypeTypeHandler.getAccountType(arg0.getString(arg1));
	}

	@Override
	public EAccountType getResult(ResultSet arg0, int arg1) throws SQLException {
		return AccountTypeTypeHandler.getAccountType(arg0.getString(arg1));
	}

	@Override
	public EAccountType getResult(CallableStatement arg0, int arg1) throws SQLException {
		return AccountTypeTypeHandler.getAccountType(arg0.getString(arg1));
	}

	@Override
	public void setParameter(PreparedStatement arg0, int arg1, EAccountType arg2, JdbcType arg3)throws SQLException {
		if( arg2 != null )
			arg0.setString(arg1, arg2.getCode());
		else
			arg0.setString(arg1, "");
	}
}