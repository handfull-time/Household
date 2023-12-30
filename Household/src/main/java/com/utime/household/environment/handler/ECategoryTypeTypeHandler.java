package com.utime.household.environment.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import com.utime.household.common.util.HouseholdUtils;
import com.utime.household.environment.vo.ECategoryType;

public class ECategoryTypeTypeHandler implements TypeHandler<ECategoryType>{
	
	private static ECategoryType getCategoryType( String s ) {
		
		if( HouseholdUtils.isEmpty(s) ) {
			return null;
		}
		
		ECategoryType result;
		switch (s) {
		case "I": result = ECategoryType.Income; break;
		case "S": result = ECategoryType.Saving; break;
		case "E": result = ECategoryType.Expense; break;
		default:
			throw new IllegalArgumentException("Unexpected value: " + s);
		}
		
		return result;
	}
	
	@Override
	public ECategoryType getResult(ResultSet arg0, String arg1) throws SQLException {
		return ECategoryTypeTypeHandler.getCategoryType(arg0.getString(arg1));
	}

	@Override
	public ECategoryType getResult(ResultSet arg0, int arg1) throws SQLException {
		return ECategoryTypeTypeHandler.getCategoryType(arg0.getString(arg1));
	}

	@Override
	public ECategoryType getResult(CallableStatement arg0, int arg1) throws SQLException {
		return ECategoryTypeTypeHandler.getCategoryType(arg0.getString(arg1));
	}

	@Override
	public void setParameter(PreparedStatement arg0, int arg1, ECategoryType arg2, JdbcType arg3)throws SQLException {
		arg0.setString(arg1, arg2.getCode());
	}
}