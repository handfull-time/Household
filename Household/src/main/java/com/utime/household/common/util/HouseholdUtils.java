package com.utime.household.common.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import jakarta.servlet.http.HttpServletRequest;

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
    
    /**
     * md5
     * @param text
     * @return
     */
    public static String md5Digest(String text) {
        final StringBuffer sb = new StringBuffer();
	    try {
	        final MessageDigest md = MessageDigest.getInstance("MD5");
	        md.update(text.getBytes("UTF-8"));
	        final byte byteData[] = md.digest();
	        for (int i = 0; i < byteData.length; i++) {
	            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
	        }
	    } catch (NoSuchAlgorithmException e) {
	        e.printStackTrace();
	    } catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	    return sb.toString().toUpperCase();
	}
    
    public static boolean StringEquals( String src1, String src2 ) {
    	if( src1 == null && src2 == null )
    		return true;
    	
    	if( src1 != null && src2 == null )
    		return false;
    	
    	if( src1 == null && src2 != null ) {
    		return false;
    	}
    	
    	return src1.equals(src2);
    }
    
    private static Gson gson = new GsonBuilder().setPrettyPrinting().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    
    public static String toString( Object obj) {
    	return obj.getClass().getName() + "\n" + HouseholdUtils.gson.toJson(obj) + "\n";
    }
    
private static final String UnknownIp = "unknown";
	
	/**
	 * 접근한 IP Address를 반환한다.
	 * @param request
	 * @return Real Remote Address
	 */
	public static String getRemoteAddress( final HttpServletRequest request ) {
		
		String result = request.getHeader("X-Forwarded-For");
        if (result == null || result.length() == 0 || UnknownIp.equalsIgnoreCase(result)) {  
            result = request.getHeader("Proxy-Client-IP");  
        }  
        if (result == null || result.length() == 0 || UnknownIp.equalsIgnoreCase(result)) {  
            result = request.getHeader("WL-Proxy-Client-IP");  
        }  
        if (result == null || result.length() == 0 || UnknownIp.equalsIgnoreCase(result)) {  
            result = request.getHeader("HTTP_CLIENT_IP");  
        }  
        if (result == null || result.length() == 0 || UnknownIp.equalsIgnoreCase(result)) {  
            result = request.getHeader("HTTP_X_FORWARDED_FOR");  
        }  
        if (result == null || result.length() == 0 || UnknownIp.equalsIgnoreCase(result)) {  
            result = request.getRemoteAddr();  
        }
        
        if( result != null && result.indexOf(",") > 0 ){
        	// ELB 접근 했을 때와 EC2 접근 IP가 [,]를 구분으로 넘어 온다.
        	result = result.split(",")[0];
        }
        
        return result;
	}
}
