package com.utime.household.common.util;

import java.io.PrintWriter;
import java.io.StringWriter;

public class HouseholdUtils {
	/**
	 * obj 값이 비었는가? 
	 * @param obj
	 * @return true : null 또는 암것도 없다.
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isEmpty( Object obj ) {
		
		if( obj == null )
			return true;
		
		if( obj instanceof String )
			return ((String)obj).trim().length() == 0;
		
		if( obj instanceof java.lang.Iterable ) 
			return !((java.lang.Iterable)obj).iterator().hasNext();
		
		if( obj instanceof java.util.Map ) 
			return ((java.util.Map)obj).isEmpty();
		
		if( obj.getClass().isArray() ) 
			return ((Object[])obj).length == 0;
		
		if( obj instanceof Number )
			return ((Number)obj).longValue() != 0L;
		
		return false;
	}
	
	public static boolean isNotEmpty( Object obj ) {
		return ! HouseholdUtils.isEmpty(obj);
	}
	
	/**
     * Exception 내용을 String 형태로 반환 한다.
     * @param e Exception
     * @return
     */
    public static String exceptionToStr( final Exception e ){
    	final StringWriter sw = new StringWriter();
		final PrintWriter out = new PrintWriter(sw);
		e.printStackTrace( out );
		return new String( sw.getBuffer() );
    }
}
