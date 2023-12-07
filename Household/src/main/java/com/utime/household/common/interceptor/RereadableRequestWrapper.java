package com.utime.household.common.interceptor;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import jakarta.servlet.DispatcherType;
import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

/**
 * HttpReq의 InputStream 데이터를 보관 하기 위한 Wrapper class
 * 
 * @see <a href='http://meetup.toast.com/posts/44'>meetup.toast.com/posts/44</a>
 */
public class RereadableRequestWrapper extends HttpServletRequestWrapper {

	private final Charset encoding;
	private byte[] rawData;
	private Map<String, String[]> params;

	public RereadableRequestWrapper(HttpServletRequest request) throws IOException {
		super(request);

		String characterEncoding = request.getCharacterEncoding();
		if (characterEncoding == null || characterEncoding.length() < 1) {
			characterEncoding = StandardCharsets.UTF_8.name();
		}
		this.encoding = Charset.forName(characterEncoding);
	}
	
	/**
	 * 데이터를 파라미터 형태로 변환
	 * @param params
	 * @param encode
	 * @return
	 */
	private String [] getParamsValue( String params, String encode ){
		if( encode == null || encode.length() < 1 ){
			encode = "utf-8";
		}
		
		final String [] result = params.toString().split(",");
    	final int size = result.length;
    	for( int j=0 ; j<size ; j++ ){
    		try {
				result[j] = URLDecoder.decode(result[j], encode);
			} catch (Exception e) {
				e.printStackTrace();
				result[j] = "Error:" + e.getLocalizedMessage();
			}
    	}
    	
    	return result;
	}

	private void parserRawData() {
		
		this.params = new HashMap<>();
		
		final Enumeration<String> names = super.getParameterNames();
		if( names.hasMoreElements() ){
			while ( names.hasMoreElements() ) {
				final String name = names.nextElement();
				this.params.put( name, this.getParamsValue(super.getParameter(name).toString(), this.encoding.name()) );
			}
		}
		
		
		final String strValue;
		{
			byte[] rData;
			try {
				rData = this.getRawData();
			} catch (IOException e) {
				e.printStackTrace();
				rData = new byte[0];
			}
			
			final int iMax = rData.length - 1;
	        if (iMax == -1){
	            return ;
	        }

	        final StringBuilder b = new StringBuilder();
	        for (int i = 0; i <= iMax; i++) {
	            b.append((char)rData[i]);
	        }
			
			strValue = b.toString();
			b.setLength(0);
			
			if( strValue.indexOf("Content-Disposition: form-data; ") > -1 ){
				return;
			}
		}
		
		final StringBuilder name = new StringBuilder();
		final StringBuilder value = new StringBuilder();
        
		boolean isName = true, isValue = false, isSkip = true;
		final int iLen = strValue.length();
		char oneChar;
        for( int i=0 ; i<iLen ; i++ ){
        	oneChar = strValue.charAt(i);
        	switch( oneChar ){
        	case '=':
        		if( isName ){
        			isSkip = false;
        			isValue = true;
        			isName = false;
        		}
        		break;
        	case '?':
        		if( ! isValue ){
        			i++;
        			isValue = false;
        			isName = true;
        		}
        		break;
        	case '&':
                final String strName = name.toString().trim();
                if( strName.length() > 0 ){
                	params.put(strName, this.getParamsValue(value.toString(), this.encoding.name()) );
                }
                isSkip = false;
                name.setLength(0);
        		value.setLength(0);
        		isValue = false;
    			isName = true;
        		break;
        	}

        	if( isSkip ){
            	if( i<iLen ){
                	if( isName ){
                		name.append( oneChar );
                	} else if( isValue ){
                		value.append( oneChar );
                	}
            	}
        	} else {
        		isSkip = true;
        	}
        }
        
        final String strName = name.toString().trim();
        if( strName.length() > 0 ){
        	params.put(strName, this.getParamsValue(value.toString(), this.encoding.name()) );
        }
	}
	
	private byte [] getRawData() throws IOException{
		if( this.rawData == null ){
			try {
				InputStream inputStream = super.getInputStream();
				this.rawData = IOUtils.toByteArray(inputStream);
			} catch (IOException e) {
				throw e;
			}
		}
		
		return this.rawData;
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		return new ServletInputStreamImpl( new ByteArrayInputStream(this.getRawData()) );
	}
	
	@Override
	public String getParameter(String name) {
		final String [] values = this.getParameterMap().get(name);
		if( values == null || values.length < 1 )
			return null;
		
		String r = "";
		for( String s : values ){
			r += s + ",";
		}
		return (r.length()>0)?  r.substring(0, r.length()-1):"";
	}
	
	@Override
	public Map<String, String[]> getParameterMap() {
		
		if( this.getDispatcherType() == DispatcherType.INCLUDE ){
			return super.getParameterMap();
		}else{
			if( this.params == null ){
				this.parserRawData();
			} else {
				final Map<String, String[]> paramsMap = super.getParameterMap();
				if( paramsMap.size() > 0 ){
					this.params.putAll( paramsMap );
				}
			}
			return this.params;
		}
	}
	
	@Override
	public Enumeration<String> getParameterNames() {
		return Collections.enumeration( this.getParameterMap().keySet() );
	}
	
	@Override
	public String[] getParameterValues(String name) {
		return this.getParameterMap().get(name);
	}
	
	private class ServletInputStreamImpl extends ServletInputStream {
		private InputStream is;
		public ServletInputStreamImpl(InputStream bis) {
			this.is = bis;
		}

		@Override
		public int read() throws IOException {
			return this.is.read();
		}

		@Override
		public int read(byte[] b) throws IOException {
			return this.is.read(b);
		}

		@Override
		public boolean isFinished() {
			return false;
		}

		@Override
		public boolean isReady() {
			return false;
		}

		@Override
		public void setReadListener(ReadListener arg0) {
		}
	}

	@Override
	public BufferedReader getReader() throws IOException {
		
		return new BufferedReader(new InputStreamReader(this.getInputStream(), this.encoding));
	}

	@Override
	public ServletRequest getRequest() {
		return super.getRequest();
	}
}
