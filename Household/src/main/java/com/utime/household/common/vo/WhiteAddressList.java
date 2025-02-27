package com.utime.household.common.vo;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * white list (Spring Security 체크 제외 목록)
 * @author utime
 *
 */
public class WhiteAddressList {

	/**
	 * white list
	 */
	public static String [] AddressList = new String[] {
			"/js/"
			, "/images/"
			, "/css/"
			, "/html/"
			, "/favicon.ico"
			, "/User/"
			, "/Auth/"
			, "/View/"
		};
	
	public static final Set<String> whiteListPaths = Arrays.stream(AddressList)
//            .map(path -> path.endsWith("/") ? path : path + "/") 
            .collect(Collectors.toSet());
}
