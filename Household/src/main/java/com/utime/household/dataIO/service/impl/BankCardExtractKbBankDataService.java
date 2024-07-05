package com.utime.household.dataIO.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.utime.household.dataIO.vo.HouseholdDataListResVO;
import com.utime.household.dataIO.vo.HouseholdDataVO;
import com.utime.household.dataIO.vo.InputBankCardDefine;
import com.utime.household.environment.vo.BankCardVO;
import com.utime.household.environment.vo.StoreVO;

import lombok.extern.slf4j.Slf4j;

/**
 * 국민은행 계좌 분석
 */
@Slf4j
@Service(InputBankCardDefine.NameKbBank)
class BankCardExtractKbBankDataService implements BankCardExtractDataService{

	@Override
	public HouseholdDataListResVO extractData(BankCardVO bcVo, MultipartFile file) throws Exception {
		final HouseholdDataListResVO result = new HouseholdDataListResVO();
		
	    final String extension = FilenameUtils.getExtension( file.getOriginalFilename() );

	    if (!extension.equals("xlsx") && !extension.equals("xls")) {
	    	result.setCodeMessage("EFXB001","엑셀파일만 업로드 해주세요");
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
	    	result.setCodeMessage("EFXB002","엑셀파일만 업로드 해주세요");
	    	return result;
		}
	    
//	    final String sheetName = workbook.getSheetName(0);
	    final Sheet sheet = workbook.getSheetAt(0);
	    final int lastRowNum = sheet.getPhysicalNumberOfRows();

	    
	    final List<HouseholdDataVO> list = new ArrayList<>();
	    result.setList(list);
	    
	    final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.KOREAN);
	    
		for( int rowNum = 14 ; rowNum < lastRowNum ; rowNum ++ ) {
			final Row row = sheet.getRow(rowNum);
			log.info(PoiUtil.toString(row));
			
			// 거래 일자
			final Date dealDate = PoiUtil.getDateCellValue( row.getCell(0) );
			if( HouseholdUtils.isEmpty(dealDate) )
				continue;
			
			// 거래 구분 = {체크카드, 이자원가, 스마트폰뱅킹 }
			final String tradeType = PoiUtil.getStringCellValue( row.getCell(1) );
			// 적요 = 체크카드:사용처, 이자원가:이자 내용, 스마트폰뱅킹: 출금메모
			final String comments = PoiUtil.getStringCellValue( row.getCell(2) );
			// 출금
			final int outAmount = PoiUtil.getIntegerCellValue(row.getCell(4));
			// 입금
			final int inAmount = PoiUtil.getIntegerCellValue(row.getCell(5));
			
			final HouseholdDataVO addItem = new HouseholdDataVO();
			addItem.setBcVo(bcVo);
			addItem.setNo( row.getRowNum() );
			
			
			try {
				addItem.setDealDate( dealDate );
				final String storeName = PoiUtil.getStringCellValue( row.getCell(2) );
				addItem.setStore( new StoreVO(storeName) );
				final int amount = PoiUtil.getIntegerCellValue(row.getCell(3)) - PoiUtil.getIntegerCellValue(row.getCell(6));
				addItem.setOriginAmount(amount);
				addItem.setUseAmount(amount);
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
				
//				addItem.setIo( addItem.getAmount() > 0? EInOut.Out:EInOut.In );
//				
//				if( addItem.getAmount() != 0 ) {
//					list.add(addItem);
//				}
				
			} catch (Exception e) {
				log.error("분석 중 오류", e);
				log.error("분석 데이터", addItem.toString());
			}
		}
	    
		return result;
	}

}
