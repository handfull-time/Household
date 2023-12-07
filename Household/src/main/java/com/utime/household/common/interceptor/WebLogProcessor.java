package com.utime.household.common.interceptor;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.utime.household.common.util.RandomValue;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 로그 작성 처리
 */
final class WebLogProcessor {
	
	private static class _ObjectInfor{
		private String packageName;
		private String methodName;
	}

	/**
	 * Object handler로부터 필요 클래스 method 등을 빼온다.
	 * @param handler
	 * @return
	 */
	private _ObjectInfor getHandlerParam(HandlerMethod handler){
		final _ObjectInfor result = new _ObjectInfor();
		
		result.packageName = handler.getBeanType().getName();
		result.methodName =  handler.getMethod().getName();
		
		return result;
	}
	
	/** Create Common Logger */
//	private Logger logger = Logger.getLogger("default");
	
//	/** interceptor Common Logger */
//	private Logger interCeptorLogger = Logger.getLogger("interceptor");
	
	private final String KEY_USER_AGENT = "User-Agent";

	private final String line1 = "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━";
	private final String line2 = "┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━";
	private final String equals = "=";
	public static final String front = "┃";
	private final String STARTVALUE = "StartValue";
	
	public static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
	
	private final String REQUEST_VALUE = front +   "Request \t: ";
	private final String RESPONSE_VALUE = front +  "Response \t: ";
	private final String REMOTE_URI = front +      "Remote URI \t: ";
	private final String PACKAGE_PATH = front +    "Package Path \t: ";
	private final String METHOD_NAME = front +     "Method Name \t: ";
	private final String SERVER_PORT = front +     "Service Port \t: ";
	private final String CONTENT_TYPE = front +    "Content Type \t: ";
	private final String CONTENT_LEN = front +    "Content Legnth \t: ";
	private final String USER_AGENT = front +  "User Agent \t: ";
	private final String RES_STATUS = front +  "Status \t: ";
	private final String REQUEST_BODY = front +    "Request Body \t:\n";
	private final String PARAM_VALUES = front +    "Request Params \t:";
	private final String VALUE_FRONT = "\t";
	private final String CONTENTS_FILE = front + "Contents File \t: ";
	private final String JSP_NAME = front +    "JSP \t\t: ";
	private final String CONTENT_TYPE_JSON = "JSON";
	private final String CONTENT_TYPE_STREAM = "STREAM";
	private final String HEADER_CONTENT_DISPOSITION = "content-Disposition";
	private final String FILTER_KEY = "org.springframework.validation";
	
	public static final String lineSepretor = System.lineSeparator();

	private final RandomValue rv = new RandomValue(8, RandomValue.ERandomType.NumericUppercase);
	
	
	
	/**
	 * 컨트롤러를 타기전 파라메터값을 뿌려준다.
	 * @param handler
	 * @param req
	 * @throws UnsupportedEncodingException
	 */
	public String urlParamLogPre( HttpServletRequest req, HandlerMethod handler) throws UnsupportedEncodingException{
		final String startValue = System.currentTimeMillis() + rv.getRandomValue();
		final _ObjectInfor infor = this.getHandlerParam( handler );
		final String contentType = req.getContentType();
		final StringBuffer paramStrBuffer = new StringBuffer(lineSepretor);
		
		paramStrBuffer.append(line1).append(lineSepretor);
		paramStrBuffer.append(REQUEST_VALUE + startValue).append(lineSepretor);
		paramStrBuffer.append(REMOTE_URI + req.getRequestURI()).append(lineSepretor);
		paramStrBuffer.append(PACKAGE_PATH + infor.packageName).append(lineSepretor);
		paramStrBuffer.append(METHOD_NAME + infor.methodName).append(lineSepretor);
		paramStrBuffer.append(CONTENT_TYPE + contentType ).append(lineSepretor);
		final String userAgent = req.getHeader(KEY_USER_AGENT);
		paramStrBuffer.append(USER_AGENT + (userAgent==null? "Is Empty.":userAgent) ).append(lineSepretor);
		final int port = req.getServerPort();
		paramStrBuffer.append(SERVER_PORT + port ).append(lineSepretor);

		
		boolean isJson = false;
		if( contentType == null ){
			//
		}else{
			String [] contentTypes = contentType.toLowerCase().split(";");
			switch( contentTypes[0] ){
			case "application/json":{
				try {
					final JsonObject obj = gson.fromJson(req.getReader(), JsonObject.class);
					paramStrBuffer.append(REQUEST_BODY + gson.toJson(obj) ).append(lineSepretor);
					isJson = true;
				} catch (IOException e) {
					e.printStackTrace();
					paramStrBuffer.append(REQUEST_BODY + "Convert Error : " + e.getLocalizedMessage() ).append(lineSepretor);
				}
				break;
			}
			case "application/octet-stream":
			case "application/x-java-object":
			case "multipart/form-data":{
				paramStrBuffer.append(CONTENT_LEN + String.format("%,dByte", req.getContentLength()) ).append(lineSepretor);
				
				if( req instanceof MultipartHttpServletRequest ){
					final MultipartHttpServletRequest multipart = (MultipartHttpServletRequest)req;
					final Map<String, MultipartFile> fileMap = multipart.getFileMap();
					final Set<String> keys = fileMap.keySet();
					for( String key : keys ){
						final MultipartFile file = fileMap.get(key);
						paramStrBuffer.append( CONTENTS_FILE + file.getOriginalFilename() + String.format("\t%,dByte", file.getSize()) ).append(lineSepretor);
					}
				}
				
				break;
			}
			default:break;
			}
		}
		
		final Enumeration<String> enumValue = req.getParameterNames();
		if( enumValue.hasMoreElements() ){
			paramStrBuffer.append(PARAM_VALUES ).append(lineSepretor);
			while ( enumValue.hasMoreElements() ) {
				final String name = enumValue.nextElement();
				if( (name.indexOf("{") == 0 || name.indexOf("[") == 0) && isJson ) {
					continue;
				}
				paramStrBuffer.append(VALUE_FRONT + name + equals + req.getParameter(name).toString() ).append(lineSepretor);
			}
		}

		
		paramStrBuffer.append(line2);
		
		req.setAttribute(STARTVALUE, startValue);
		return paramStrBuffer.toString();
	};

	/**
	 * 로그 출력 최대 크기
	 */
	private final int LOG_OUT_LENGTH = 5 * 1024;
	
	/**
	 * 컨트롤러가 view를 호출하기 전 최종 파라메터값을 뿌려준다.
	 * @param req
	 * @param res
	 * @param handler
	 * @param mav
	 */
	public String urlParamLogPost(HttpServletRequest req, HttpServletResponse res, HandlerMethod handler, ModelAndView mav) {
		
		final _ObjectInfor infor = this.getHandlerParam( handler );
		final String contentType = res.getContentType();
		
		final StringBuffer paramStrBuffer = new StringBuffer(lineSepretor);
		paramStrBuffer.append(line1).append(lineSepretor);
		paramStrBuffer.append(RESPONSE_VALUE + req.getAttribute(STARTVALUE).toString() ).append(lineSepretor);
		paramStrBuffer.append(PACKAGE_PATH + infor.packageName ).append(lineSepretor);
		paramStrBuffer.append(METHOD_NAME + infor.methodName ).append(lineSepretor);
		paramStrBuffer.append(CONTENT_TYPE + contentType ).append(lineSepretor);
		paramStrBuffer.append(RES_STATUS + res.getStatus() ).append(lineSepretor);
		
		if(mav != null){
			paramStrBuffer.append(JSP_NAME + mav.getViewName() ).append(lineSepretor);
			final Map<String, Object> model = mav.getModel();
			final java.util.Set<String> keys = model.keySet();
			for( String key : keys ){
				if( key.indexOf(FILTER_KEY) > -1 )
					continue;
				
				final Object obj = model.get(key);
				paramStrBuffer.append(front + key + equals + (obj==null? "Is Null.":obj.toString()) ).append(lineSepretor);
			}

		}else if( contentType != null ){
			final String ctUpper = contentType.toUpperCase();//":"
			if( ctUpper.indexOf(CONTENT_TYPE_STREAM) > -1 ){
				// Stream Data
				paramStrBuffer.append( CONTENTS_FILE + res.getHeader(HEADER_CONTENT_DISPOSITION) ).append(lineSepretor);
				
			} else if(ctUpper.indexOf(CONTENT_TYPE_JSON) > -1 ){
				// Json
//				String s = res.toString();
//				final JsonObject obj = gson.fromJson(res.toString(), JsonObject.class);
//				paramStrBuffer.append( gson.toJson(obj) ).append(lineSepretor);
			} else if(ctUpper.indexOf("IMAGE") > -1 || ctUpper.indexOf("VIDEO") > -1 ){
				// Image or Movie
				paramStrBuffer.append( CONTENTS_FILE + res.getHeader(HEADER_CONTENT_DISPOSITION) ).append(lineSepretor);
			} else{
				paramStrBuffer.append( "[" + res.toString() + "]").append(lineSepretor);
			}
		}else{
			paramStrBuffer.append( res.toString() ).append(lineSepretor);
		}
		
		final int orgSize = paramStrBuffer.length(); 
		if( orgSize > LOG_OUT_LENGTH ){
			paramStrBuffer.setLength( LOG_OUT_LENGTH );
			paramStrBuffer.append("...\nLog Total Size : " + String.format("%,d Byte", orgSize)).append(lineSepretor);
		}
		
		paramStrBuffer.append(line2);
		return paramStrBuffer.toString();
	}
	
}
