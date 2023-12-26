package com.utime.household.dataIO.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import com.utime.household.dataIO.vo.EInOut;

public class InoutTypeHandler implements TypeHandler<EInOut>{
	
	private static final String KeyInput = "I";
	private static final String KeyOutput = "O";
	
	private static EInOut getInOut( String s ) {
		return KeyInput.equals(s)? EInOut.In:EInOut.Out;
	}
	
	@Override
	public EInOut getResult(ResultSet arg0, String arg1) throws SQLException {
		return InoutTypeHandler.getInOut(arg0.getString(arg1));
	}

	@Override
	public EInOut getResult(ResultSet arg0, int arg1) throws SQLException {
		return InoutTypeHandler.getInOut(arg0.getString(arg1));
	}

	@Override
	public EInOut getResult(CallableStatement arg0, int arg1) throws SQLException {
		return InoutTypeHandler.getInOut(arg0.getString(arg1));
	}

	@Override
	public void setParameter(PreparedStatement arg0, int arg1, EInOut arg2, JdbcType arg3)throws SQLException {
		arg0.setString(arg1, arg2==EInOut.In? KeyInput:KeyOutput);
	}
}