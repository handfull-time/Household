package com.utime.household.dataIO.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

public class IncludedTypeHandler implements TypeHandler<Boolean>{
	
	private static final String KeyIncluded = "T";
	private static final String KeyNotIncluded = "F";
	
	private static Boolean getBoolean( String s ) {
		return KeyIncluded.equals(s);
	}
	
	@Override
	public Boolean getResult(ResultSet arg0, String arg1) throws SQLException {
		return IncludedTypeHandler.getBoolean(arg0.getString(arg1));
	}

	@Override
	public Boolean getResult(ResultSet arg0, int arg1) throws SQLException {
		return IncludedTypeHandler.getBoolean(arg0.getString(arg1));
	}

	@Override
	public Boolean getResult(CallableStatement arg0, int arg1) throws SQLException {
		return IncludedTypeHandler.getBoolean(arg0.getString(arg1));
	}

	@Override
	public void setParameter(PreparedStatement arg0, int arg1, Boolean arg2, JdbcType arg3)throws SQLException {
		arg0.setString(arg1, arg2? KeyIncluded:KeyNotIncluded);
	}
}