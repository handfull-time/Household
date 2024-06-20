package com.utime.household.dataIO.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.utime.household.common.util.HouseholdUtils;
import com.utime.household.common.util.PoiUtil;
import com.utime.household.dataIO.vo.EInOut;
import com.utime.household.dataIO.vo.HouseholdDataListResVO;
import com.utime.household.dataIO.vo.HouseholdDataVO;
import com.utime.household.dataIO.vo.InputBankCardDefine;
import com.utime.household.environment.vo.BankCardVO;
import com.utime.household.environment.vo.CardItemVO;
import com.utime.household.environment.vo.StoreVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service(InputBankCardDefine.NameSamsung)
class BankCardExtractSamsungDataService implements BankCardExtractDataService{
	
	@Override
	public HouseholdDataListResVO extractData(BankCardVO bcVo, MultipartFile file) throws Exception {
		
		final HouseholdDataListResVO result = new HouseholdDataListResVO();
		
	    final String extension = FilenameUtils.getExtension( file.getOriginalFilename() );

	    if (!extension.equals("xlsx") && !extension.equals("xls")) {
	    	result.setCodeMessage("EFX001","엑셀파일만 업로드 해주세요");
	    	return result;
	    }
	    
    	Workbook workbook = null;
	    try {
		    if (extension.equals("xlsx")) {
		      workbook = new XSSFWorkbook(file.getInputStream());
		    } else if (extension.equals("xls")) {
		      workbook = new HSSFWorkbook(file.getInputStream());
		    }
	    } catch (Exception e) {
			log.error("", e);
	    	result.setCodeMessage("EFX001","엑셀파일만 업로드 해주세요");
	    	return result;
		}
	    
	    {
	    	final String sheetName = workbook.getSheetName(0);
	    	if( ! "청구요약".equals(sheetName) ) {
	    		log.error("sheetName 다름 - " + sheetName);
		    	result.setCodeMessage("EFX002", "청구요약 Sheet가 없습니다.");
		    	return result;
	    	}
	    	
	    	final Sheet sheet = workbook.getSheetAt(0);
	    	final int lastRowNum = sheet.getLastRowNum();
    		for( int rowNum = 3 ; rowNum < lastRowNum ; rowNum ++ ) {
    			final Row row = sheet.getRow(rowNum);
    			log.info(PoiUtil.toString(row));
    			
    			// 이용자 ex) 본 인
    			final String user = PoiUtil.getStringCellValue( row.getCell(0) );
    			// 카드번호/대출번호 ex) ****-****-****-*118
    			final String number = PoiUtil.getStringCellValue( row.getCell(1) );
    			// 상품명 ex) 엠베스트 엘리하이 삼성카드
    			final String name = PoiUtil.getStringCellValue( row.getCell(2) );
    			// 이용구분 ex) 본 인 445
    			final String userDif = user + " " + number.substring(number.lastIndexOf('*')+1);
    			
    			final CardItemVO cardItem = new CardItemVO();
    			cardItem.setName(name);
    			cardItem.setInnerFileCardName(name);
    			
    			
    			bcVo.getCard().getCards().add( cardItem );
    		}
	    }

	    
	    final List<HouseholdDataVO> list = new ArrayList<>();
	    result.setList(list);
	    
	    final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.KOREAN);
	    
	    final int sheetCount = workbook.getNumberOfSheets();
	    for( int i=1 ; i<sheetCount ; i++ ) {
	    	final String sheetName = workbook.getSheetName(i);
	    	
	    	
	    	
	    	
	    	// sheet 이름
	    	if( "일시불".equals(sheetName) || "연회비-기타수수료".equals(sheetName) ) {
	    		
	    		// sheet 구성 내용
//	    		이용일	이용구분	가맹점	이용금액	총할부금액	이용혜택	혜택금액	개월	회차	원금	이자/수수료	포인트명	적립금액	입금후잔액
	    		final Sheet sheet = workbook.getSheet(sheetName);
	    		
	    		final int lastRowNum = sheet.getLastRowNum();
	    		for( int rowNum = 3 ; rowNum < lastRowNum ; rowNum ++ ) {
	    			final Row row = sheet.getRow(rowNum);
	    			log.info(PoiUtil.toString(row));
	    			
	    			final String dealDate = PoiUtil.getStringCellValue( row.getCell(0) );
	    			if( HouseholdUtils.isEmpty(dealDate) )
	    				continue;
	    			
    				final HouseholdDataVO addItem = new HouseholdDataVO();
    				addItem.setBcVo(bcVo);
    				addItem.setNo( row.getRowNum() );
    				
	    			try {
	    				addItem.setDealDate( sdf.parse(dealDate) );
	    				final String storeName = PoiUtil.getStringCellValue( row.getCell(2) );
	    				addItem.setStore( new StoreVO(storeName) );
	    				final int amount = PoiUtil.getIntegerCellValue(row.getCell(3)) - PoiUtil.getIntegerCellValue(row.getCell(6));
	    				addItem.setAmount( amount );
	    				final String dscr = PoiUtil.getStringCellValue( row.getCell(5) );
	    				final String installmentMonth  = PoiUtil.getStringCellValue( row.getCell(7) );
	    				final String installmentCount  = PoiUtil.getStringCellValue( row.getCell(8) );
	    				
	    				final boolean isDscr = HouseholdUtils.isNotEmpty(dscr);
	    				if( isDscr ) {
	    					addItem.setDscr( dscr );
	    				}
	    				
	    				if( HouseholdUtils.isNotEmpty(installmentMonth) ) {
	    					addItem.setDscr( (isDscr? (dscr + ", "):"") + installmentMonth +"개월 (" + installmentCount + "회차)");
	    				}
	    				
	    				addItem.setIo( addItem.getAmount() > 0? EInOut.Out:EInOut.In );
	    				
	    				if( addItem.getAmount() != 0 ) {
	    					list.add(addItem);
	    				}
	    				
					} catch (Exception e) {
						log.error("분석 중 오류", e);
						log.error("분석 데이터", addItem.toString());
					}
	    		}
	    	}
	    }
	    
		return result;
	}

}
