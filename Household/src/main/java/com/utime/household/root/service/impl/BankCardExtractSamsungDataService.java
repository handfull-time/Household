package com.utime.household.root.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
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
import com.utime.household.config.dao.BankCardDao;
import com.utime.household.config.dao.CategoryDao;
import com.utime.household.config.dao.StoreDao;
import com.utime.household.config.vo.BankCardVO;
import com.utime.household.config.vo.CategoryVO;
import com.utime.household.config.vo.StoreVO;
import com.utime.household.root.service.BankCardExtractDataService;
import com.utime.household.root.vo.EInputBankCard;
import com.utime.household.root.vo.HouseholdDataListResVO;
import com.utime.household.root.vo.HouseholdDataVO;
import com.utime.household.root.vo.InputBankCardDefine;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service(InputBankCardDefine.NameSamsung)
class BankCardExtractSamsungDataService implements BankCardExtractDataService{

	private final BankCardDao bcDao;
	private final StoreDao storeDao;
	private final CategoryDao ctDao;
	
	@Override
	public HouseholdDataListResVO extractData(MultipartFile file) throws Exception {
		
		final HouseholdDataListResVO result = new HouseholdDataListResVO();
		
		final BankCardVO bcVo = bcDao.getBankCard( EInputBankCard.Samsung );
		if( bcVo == null ) {
			result.setCodeMessage("EBCESDS0A1", "삼성카드 등록이 필요합니다.");
			return result;
		}
		
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
	    
	    final List<CategoryVO> ctList = ctDao.getCategoryList(null);
		final Map<Long, CategoryVO> ctMap = new HashMap<>();
		for( CategoryVO vo : ctList ){
			ctMap.put(vo.getNo(), vo);
		}
		
	    final StoreManage storeMgr = new StoreManage( storeDao.getStoreList() );
	    
	    final List<HouseholdDataVO> list = new ArrayList<>();
	    result.setList(list);
	    
	    final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.KOREAN);
	    
	    final int sheetCount = workbook.getNumberOfSheets();
	    for( int i=0 ; i<sheetCount ; i++ ) {
	    	final String sheetName = workbook.getSheetName(i);
	    	if( "일시불".equals(sheetName) || "연회비-기타수수료".equals(sheetName) ) {
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
    				
	    			try {
	    				addItem.setDealDate( sdf.parse(dealDate) );
	    				final String storeName = PoiUtil.getStringCellValue( row.getCell(2) );
	    				final StoreVO store = storeMgr.getStore(storeName);
	    				addItem.setStore( store );
	    				addItem.setCategory( ctMap.get( store.getCategoryNo() ) );
	    				addItem.setAmount( PoiUtil.getIntegerCellValue(row.getCell(3)) - PoiUtil.getIntegerCellValue(row.getCell(6)) );
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
