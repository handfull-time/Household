package com.utime.household.dataIO.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.utime.household.environment.dao.BankCardDao;
import com.utime.household.environment.vo.BankCardVO;
import com.utime.household.environment.vo.CardItemVO;
import com.utime.household.environment.vo.StoreVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 산업은행 계좌 분석</P>
 * 
 * 은행 계좌 내역과 체크카드 내역이 혼용돼 있기 때문에 "체크카드" 라고 구분 돼 있는 것은 체크카드 내역으로, 그렇지 않은 것은 은행 계좌 내역으로 분리해 처리한다.
 */
@Slf4j
@RequiredArgsConstructor
@Service(InputBankCardDefine.NameKdb)
class BankCardExtractKdbDataService implements BankCardExtractDataService{

	private final BankCardDao bcDao;
	
	@SuppressWarnings("unused")
	private static class TKdbDataVO{
		/** 거래일시 */
		Date transactionDate;
		/** 거래구분 {체크카드, 이자원가, 스마트폰뱅킹, 인터넷뱅킹, 오픈플랫폼출금, 타행자동이체, 요구불이체, CD출금, 만기상환, 펌뱅킹출금, 일반지로 } */
		String transactionType;
		/** 내용 */
		String description;
		/** 출금 */ 
		int withdrawal;
		/** 입금 */
		int deposit;
		
		@Override
		public String toString() {
			return HouseholdUtils.toString(this);
		}
	}
	
	private TKdbDataVO convertKdbVO(final Row row ) throws Exception{
		TKdbDataVO result = null;
		
		// 이용일
		final Date transactionDate = PoiUtil.getDateCellValue( row.getCell(0) );
		if( HouseholdUtils.isEmpty(transactionDate) )
			return result;

		final String temp = PoiUtil.getStringCellValue( row.getCell(1) );
		if( HouseholdUtils.isEmpty(temp) )
			return result;
		
		final String [] tmpSplit = temp.split("/");
		
		result = new TKdbDataVO();
		result.transactionDate = transactionDate;
		result.transactionType = tmpSplit[0].trim();
		result.description = tmpSplit[1].trim();
		result.withdrawal = PoiUtil.getIntegerCellValue( row.getCell(2) );
		result.deposit = PoiUtil.getIntegerCellValue( row.getCell(3) );
		
		return result;
	}
	
	@Override
	public HouseholdDataListResVO extractData(BankCardVO bcVo, MultipartFile file) throws Exception {
		final HouseholdDataListResVO result = new HouseholdDataListResVO();
		
	    final String extension = FilenameUtils.getExtension( file.getOriginalFilename() );

	    if (!extension.equals("xlsx") && !extension.equals("xls")) {
	    	result.setCodeMessage("EFXK001","엑셀파일만 업로드 해주세요");
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
	    	result.setCodeMessage("EFXK002","엑셀파일만 업로드 해주세요");
	    	return result;
		}
	    
	    final Sheet sheet = workbook.getSheetAt(0);
	    final int lastRowNum = sheet.getPhysicalNumberOfRows();
	    
	    BankCardVO bankVo = bcVo;
	    BankCardVO cardVo;
	    
	    {
	    	List<BankCardVO> cardList = bcDao.getCardFromBankNo( bcVo.getNo() );
	    	if( HouseholdUtils.isNotEmpty(cardList) ) {
	    		log.error("데이터가 없다.");
		    	result.setCodeMessage("EFXK002","카드 등록 필요");
		    	return result;
	    	}
	    	
	    	cardVo = cardList.get(0);
	    }
	    
	    final CardItemVO checkCard =  cardVo.getCard().getCards().get(0);
	    
	    final List<HouseholdDataVO> list = new ArrayList<>();
	    result.setList(list);
	    
		for( int rowNum = 14 ; rowNum < lastRowNum ; rowNum ++ ) {
			final Row row = sheet.getRow(rowNum);
			log.info(PoiUtil.toString(row));
			 
			final TKdbDataVO kdbVo;
			
			try {
				kdbVo = this.convertKdbVO(row);
			}catch( Exception e) {
				log.error("", e);
				throw e;
			}
			
			if( kdbVo == null ) {
				log.warn("데이터 없음");
				continue;
			}
			
			log.info(kdbVo.toString());
			
			
			final boolean isCard = "체크카드".equals(kdbVo.transactionType);
			final HouseholdDataVO addItem = new HouseholdDataVO();
			addItem.setBcVo( isCard? cardVo:bankVo );
			addItem.setNo( row.getRowNum() );
			
			addItem.setDealDate( kdbVo.transactionDate );
			addItem.setStore( new StoreVO( kdbVo.description ) );
			addItem.setOriginAmount( Math.max(kdbVo.deposit, kdbVo.withdrawal) );
			addItem.setUseAmount( addItem.getOriginAmount() );
			if( isCard ) {
				addItem.setDscr( kdbVo.description );
				addItem.setCarditem(checkCard);
			}else {
				addItem.setDscr( kdbVo.transactionType + ", "  + kdbVo.description );	
			}			
			addItem.setIo( (addItem.getUseAmount() > 0)? EInOut.Out:EInOut.In );
			addItem.setIncluded(addItem.getUseAmount() != 0);
			
			list.add(addItem);
		}
	    
		return result;
	}

}
