package com.utime.household.dataIO.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.utime.household.environment.vo.ECardType;
import com.utime.household.environment.vo.StoreVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 삼성카드 거래 내역 분석
 */
@Slf4j
@RequiredArgsConstructor
@Service(InputBankCardDefine.NameSamsung)
class BankCardExtractSamsungDataService implements BankCardExtractDataService{
	
	private final BankCardDao bcDao;
	
	@SuppressWarnings("unused")
	private static class TSamsungCardVO{
		/** 이용일 */
		Date useDate;
		/** 이용구분 */
		String useType;
		/** 가맹점 */
		String merchant;
		/** 이용금액 */
		int useAmount;
		/** 총할부금액 */
		int totalInstallmentAmount;
		/** 이용혜택 */
		String useBenefit;
		/** 혜택금액 */
		int benefitAmount;
		/** 개월 */
		int months;
		/** 회차 */
		int installmentNumber;
		/** 원금 */
		int principal;
		/** 이자/수수료 */
		int interestFee;
		/** 포인트명 */
		String pointName;
		/** 적립금액 */
		int accumulatedAmount;
		/** 입금후잔액 */
		int balanceAfterDeposit;
		
		@Override
		public String toString() {
			return HouseholdUtils.toString(this);
		}
	}
	
    /**
     * 삼성카드 파일 데이터 Object로 변환
     * 이용일	이용구분	가맹점	이용금액	총할부금액	이용혜택	혜택금액	개월	회차	원금	이자/수수료	포인트명	적립금액	입금후잔액
     * @param row
     * @return
     * @throws Exception
     */
	private TSamsungCardVO convertSamsungCardVO(final Row row ) throws Exception{
		TSamsungCardVO result = null;
		
		// 이용일
		final Date useDate = PoiUtil.getDateCellValue( row.getCell(0) );
		if( HouseholdUtils.isEmpty(useDate) )
			return result;

		result = new TSamsungCardVO();
		result.useDate = useDate;
		// 이용구분
		result.useType = PoiUtil.getStringCellValue( row.getCell(1) );
		// 가맹점 
		result.merchant = PoiUtil.getStringCellValue( row.getCell(2) );
		// 이용금액
		result.useAmount = PoiUtil.getIntegerCellValue( row.getCell(3) );
		// 총할부금
		result.totalInstallmentAmount = PoiUtil.getIntegerCellValue( row.getCell(4) );
		// 이용혜택 
		result.useBenefit = PoiUtil.getStringCellValue( row.getCell(5) );
		// 혜택금액 
		result.benefitAmount = PoiUtil.getIntegerCellValue( row.getCell(6) );
		// 개월 
		result.months = PoiUtil.getIntegerCellValue( row.getCell(7) );
		// 회차
		result.installmentNumber = PoiUtil.getIntegerCellValue( row.getCell(8) );
		// 원금
		result.principal = PoiUtil.getIntegerCellValue( row.getCell(9) );
		// 이자/수수료 
		result.interestFee = PoiUtil.getIntegerCellValue( row.getCell(10) );
		// 포인트명
		result.pointName = PoiUtil.getStringCellValue( row.getCell(11) );
		// 적립금액
		result.accumulatedAmount = PoiUtil.getIntegerCellValue( row.getCell(12) );
		// 입금후잔액 
		result.balanceAfterDeposit = PoiUtil.getIntegerCellValue( row.getCell(13) );
		
		return result;

	}
	
	
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
	    
    	final List<CardItemVO> cards = bcVo.getCard().getCards();
    	final Map<String, CardItemVO> cardItemMap = new HashMap<>();
    	
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

    			CardItemVO item = null;
    			for( CardItemVO im : cards ) {
    				if( im.getNo() >= 0L && userDif.equals( im.getInnerFileCardName() ) ) {
    					item = im;
    					break;
    				}
    			}
    			
    			if( item == null ) {
    				item = new CardItemVO();
    				item.setNo(-1L);
    				item.setCardType(ECardType.Credit);
    				item.setCardNo( bcVo.getNo() );
    				item.setName(name);
    				item.setInnerFileCardName(userDif);
    				item.setInnerFileCardNumber(number);
    				
    				bcDao.saveCardItem(item);
    				cards.add(item);
    			}
    			
    			cardItemMap.put(userDif, item);
    		}
	    }

	    
	    final List<HouseholdDataVO> list = new ArrayList<>();
	    result.setList(list);
	    
	    final int sheetCount = workbook.getNumberOfSheets();
	    for( int i=1 ; i<sheetCount ; i++ ) {
	    	final String sheetName = workbook.getSheetName(i);
	    	
	    	// sheet 이름
	    	if( ! "일시불".equals(sheetName) && ! "연회비-기타수수료".equals(sheetName) ) 
	    		continue;
	    	
    		final Sheet sheet = workbook.getSheet(sheetName);
    		
    		final int lastRowNum = sheet.getLastRowNum();
    		for( int rowNum = 3 ; rowNum < lastRowNum ; rowNum ++ ) {
    			final Row row = sheet.getRow(rowNum);
    			log.info(PoiUtil.toString(row));

    			final TSamsungCardVO cardVo;
    			
    			try {
    				cardVo = this.convertSamsungCardVO(row);
    			}catch( Exception e) {
    				log.error("", e);
    				throw e;
    			}
    			
    			if( cardVo == null ) {
    				log.warn("데이터 없음");
    				continue;
    			}
    			
    			log.info(cardVo.toString());
    			
				final HouseholdDataVO addItem = new HouseholdDataVO();
				addItem.setBcVo( bcVo );
				addItem.setNo( row.getRowNum() );
				addItem.setCarditem( cardItemMap.get(cardVo.useType) );
				
				addItem.setDealDate( cardVo.useDate );
				addItem.setStore( new StoreVO( cardVo.merchant ) );
				addItem.setOriginAmount( cardVo.useAmount );
				addItem.setUseAmount( cardVo.principal + cardVo.interestFee );
				final String dscr = cardVo.useBenefit;
				
				final boolean isDscr = HouseholdUtils.isNotEmpty(dscr);
				if( isDscr ) {
					addItem.setDscr( dscr );
				}

				if( cardVo.months > 0 && cardVo.installmentNumber > 0 ) {
					addItem.setDscr( (isDscr? (dscr + ", "):"") + cardVo.months +"개월 (" + cardVo.installmentNumber + "회차)");
				}
				
				addItem.setIo( (addItem.getUseAmount() > 0)? EInOut.Out:EInOut.In );
				addItem.setIncluded(addItem.getUseAmount() != 0);
				
				list.add(addItem);
    		}
    	}
	    
		return result;
	}

}
