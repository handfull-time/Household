package com.utime.household.common.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

public class BooleanTypeHandler implements TypeHandler<Boolean>{
	
	private static Boolean getBoolean( String s ) {
		return "Y".equals(s);
	}
	
	@Override
	public Boolean getResult(ResultSet arg0, String arg1) throws SQLException {
		return BooleanTypeHandler.getBoolean(arg0.getString(arg1));
	}

	@Override
	public Boolean getResult(ResultSet arg0, int arg1) throws SQLException {
		return BooleanTypeHandler.getBoolean(arg0.getString(arg1));
	}

	@Override
	public Boolean getResult(CallableStatement arg0, int arg1) throws SQLException {
		return BooleanTypeHandler.getBoolean(arg0.getString(arg1));
	}

	@Override
	public void setParameter(PreparedStatement arg0, int arg1, Boolean arg2, JdbcType arg3)throws SQLException {
		arg0.setString(arg1, arg2? "Y":"N");
	}
}