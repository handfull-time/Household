package com.utime.household.dataIO.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

/**
 * 거래 일자 변환 핸들러
 */
public class DealDateTypeHandler implements TypeHandler<Date>{
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.KOREA);
	
	private static Date getInOut( String s ) throws SQLException{
		Date result;
		try {
			result = sdf.parse(s);
		} catch (Exception e) {
			throw new SQLException(e);
		}
		return result;
	}
	
	@Override
	public Date getResult(ResultSet arg0, String arg1) throws SQLException {
		return DealDateTypeHandler.getInOut(arg0.getString(arg1));
	}

	@Override
	public Date getResult(ResultSet arg0, int arg1) throws SQLException {
		return DealDateTypeHandler.getInOut(arg0.getString(arg1));
	}

	@Override
	public Date getResult(CallableStatement arg0, int arg1) throws SQLException {
		return DealDateTypeHandler.getInOut(arg0.getString(arg1));
	}

	@Override
	public void setParameter(PreparedStatement arg0, int arg1, Date arg2, JdbcType arg3)throws SQLException {
		arg0.setString(arg1, sdf.format(arg2));
	}
}