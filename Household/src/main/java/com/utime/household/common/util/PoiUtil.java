package com.utime.household.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;

public class PoiUtil {
	
	private static final DataFormatter fmt = new DataFormatter();
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd hh:mm:ss", Locale.KOREAN);
	
	private static final String Sepataotor = "(\r\n|\r|\n|\n\r)";
	
	public static String getStringCellValue(Cell cell){
		
		if(cell==null){
            return "";
        }

		final String result;
        //타입별로 내용 읽기
        switch (cell.getCellType()){
        case NUMERIC: {
        	if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
                result = sdf.format(cell.getDateCellValue());
            } else {
            	String tmp = String.valueOf( cell.getNumericCellValue() );
            	if( tmp.lastIndexOf(".0") > 0 ){
            		tmp = String.valueOf( Long.parseLong(tmp.substring(0, tmp.indexOf(".0"))) );
            	}

            	// 값이 지수가 있을 경우 숫자 형태로 
            	result = ( tmp.indexOf("E") > 0 )? fmt.formatCellValue( cell ):tmp;
            }
        	break;
        }
        default: {
        	final String tmp = fmt.formatCellValue( cell );
        	if( tmp == null || tmp.length() < 1 ){
        		result = cell.getStringCellValue();
        	} else {
        		result = tmp;
        	}
        }
        }
        
		return result.replaceAll(Sepataotor, " ");
	}
	
	public static int getIntegerCellValue(Cell cell){
		if(cell==null){
            return 0;
        }

		final int result;
        //타입별로 내용 읽기
        switch (cell.getCellType()){
        case NUMERIC: {
        	
        	result = Double.valueOf( cell.getNumericCellValue() ).intValue();
        	break;
        }
        default: {
        	final String cellValue = fmt.formatCellValue( cell );
        	final String numData = cellValue.replaceAll("[^0-9]", "");
        	if( HouseholdUtils.isEmpty(numData) ) {
        		result = 0;
        	}else {
        		result = Integer.parseInt( numData );
        	}
        }
        }
        
		return result;
	}
	
	public static long getLongCellValue(Cell cell){
		if(cell==null){
            return 0L;
        }

		final long result;
        //타입별로 내용 읽기
        switch (cell.getCellType()){
        case NUMERIC: {
        	
        	result = Double.valueOf( cell.getNumericCellValue() ).longValue();
        	break;
        }
        default: 
        	final String cellValue = fmt.formatCellValue( cell );
        	final String numData = cellValue.replaceAll("[^0-9]", "");
        	if( HouseholdUtils.isEmpty(numData) ) {
        		result = 0L;
        	}else {
        		result = Long.parseLong( numData );
        	}

        }
        
		return result;
	}
	
	private static final java.text.SimpleDateFormat formatterYear = new java.text.SimpleDateFormat( "yyyy", java.util.Locale.KOREA);
	private static final java.text.SimpleDateFormat formatterYearMonth = new java.text.SimpleDateFormat( "yyyyMM", java.util.Locale.KOREA);
	private static final java.text.SimpleDateFormat formatterYearMonthDay = new java.text.SimpleDateFormat( "yyyyMMdd", java.util.Locale.KOREA);
	private static final java.text.SimpleDateFormat formatterHrMntScnd = new java.text.SimpleDateFormat( "HHmmss", java.util.Locale.KOREA);
	private static final java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat( "yyyyMMddHHmmss", java.util.Locale.KOREA);
	
	/**
	 * 문자열 값을 날짜로 변환한다.
	 * @param value
	 * @return
	 */
	private static Date convertStrToDate(String value ) {
		if( value == null || value.length() < 1 )
    		return null;
    	
		final String numData = value.replaceAll("[^0-9]", "");
		
		final int numLen = numData.length(); 
		if( numLen < 1 ) {
			return null;
		}

		Date result;
		try {
    		switch( numLen ) {
    		case 4 : {
    			// 년도
				result = formatterYear.parse(numData);
    			break;
    		}
    		case 6 : {
    			// 년 월 or 시분초
    			if( value.indexOf(":") > 0 ) {
    				result = formatterHrMntScnd.parse(numData);
    			}else {
    				result = formatterYearMonth.parse(numData);
    			}
    			
    			break;
    		}
    		case 8 : {
    			// 년월일
    			result = formatterYearMonthDay.parse(numData);
    			break;
    		}
    		case 14 :{
    			//년월일시분초
    			result = formatter.parse(numData);
    			break;
    		}
    		default : return null;
    		}
		} catch (ParseException e) {
			e.printStackTrace();
			result = null;
		}
		
		return result;
	}
	
	public static Date getDateCellValue(Cell cell){
		if(cell==null){
            return null;
        }

		Date result;
        //타입별로 내용 읽기
        switch (cell.getCellType()){
        case NUMERIC: {
        	if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
                result = cell.getDateCellValue();
            } else {
            	final String tmp = String.valueOf( (long)cell.getNumericCellValue() );
            	
            	result = PoiUtil.convertStrToDate( tmp );
            }
        	break;
        }
        default: {
        	result = PoiUtil.convertStrToDate( fmt.formatCellValue( cell ) );
			break;
        }
        }
        
		return result;
	}
	
	public static String toString( Row row ) {
		
		final short lastCellNum = row.getLastCellNum();
		
		Cell cell;
		final StringBuilder sb = new StringBuilder();
		sb.append("RowNum : " + row.getRowNum() + "\n");
		for( short cellNum = row.getFirstCellNum() ; cellNum<lastCellNum ; cellNum++  ) {
			cell = row.getCell(cellNum);
			sb.append( "\t" + String.format("%-4d", cellNum) + " : [" + (cell == null? "-null":cell.getStringCellValue()) + "]\n" );
		}
		
		return sb.toString();
	}
}
