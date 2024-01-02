package com.utime.household.dataIO.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.utime.household.dataIO.dao.DataIODao;
import com.utime.household.dataIO.service.DataIOService;
import com.utime.household.dataIO.vo.HouseholdDataListResVO;
import com.utime.household.dataIO.vo.HouseholdDataVO;
import com.utime.household.dataIO.vo.HouseholdReqDataVO;
import com.utime.household.dataIO.vo.HouseholdResDataVO;
import com.utime.household.dataIO.vo.InputBankCardList;
import com.utime.household.dataIO.vo.OuputReqVO;
import com.utime.household.environment.dao.BankCardDao;
import com.utime.household.environment.vo.BankCardVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
class DataIOServiceImpl implements DataIOService{
	
	private final ApplicationContext ctx;
	
	private final BankCardDao bankCardDao;
	
	private final DataIODao ioDao;
	
	@Override
	public HouseholdDataListResVO analyzeData(long bankCardNo, MultipartFile file) {
		
		log.info(file.getOriginalFilename());
		
		HouseholdDataListResVO result;

		final BankCardVO bcVo = bankCardDao.getBankCard(bankCardNo);
		if( bcVo == null ) {
			result = new HouseholdDataListResVO();
			
			result.setCodeMessage("EHS0A1", "은행/카드 정보 찾을 수 없습니다.");
			
			return result;
		}
		
		String beanName = InputBankCardList.getBeanName(bcVo);
		
		final BankCardExtractDataService service = ctx.getBean(beanName, BankCardExtractDataService.class);

		bcVo.setBank(null);
		bcVo.setCard(null);
		bcVo.setDscr(null);
		
		try {
			result = service.extractData( bcVo, file );
			result.setBcVo( bcVo );
		} catch (Exception e) {
			log.error("", e);
			result = new HouseholdDataListResVO();
			result.setCodeMessage("EHS0A2", e.getMessage());
		}
		
		return result;
	}
	

	@Override
	public HouseholdResDataVO saveCompareData(HouseholdReqDataVO vo) {
		final HouseholdResDataVO result = new HouseholdResDataVO();
		
		final BankCardVO bcVo = bankCardDao.getSimpleBankCard(vo.getBcNo());
		result.setBcVo( bcVo );
		
		List<HouseholdDataVO> list = vo.getList();
		HouseholdDataVO first = list.get(0);
		Date minDate = first.getDealDate();
        Date maxDate = first.getDealDate();
		
		for( HouseholdDataVO item : list) {
			item.setBcVo(bcVo);
			final Date date  = item.getDealDate();
			
			if (date.before(minDate)) {
                minDate = date;
            }
			
			if (date.after(maxDate)) {
                maxDate = date;
            }
		}
		
		try {
			ioDao.addHouseholdData( list, minDate, maxDate );
		} catch (Exception e) {
			log.error("", e);
			result.setCodeMessage("EHS0A2", e.getMessage());
		}
		
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
		
		result.setBegin( sdf.format(minDate) );
		result.setEnd( sdf.format(maxDate) );
		
		return result;
	}
	
	@Override
	public HouseholdResDataVO saveDirectData(HouseholdReqDataVO vo) {
		final HouseholdResDataVO result = new HouseholdResDataVO();
		
		final BankCardVO bcVo = bankCardDao.getSimpleBankCard(vo.getBcNo());
		result.setBcVo( bcVo );
		
		List<HouseholdDataVO> list = vo.getList();
		
		for( HouseholdDataVO item : list) {
			item.setBcVo(bcVo);
			try {
				ioDao.addHouseholdData(item);
			} catch (Exception e) {
				log.error("", e);
				result.setCodeMessage("EHS0A2", e.getMessage());
			}
		}
		
		return result;
	}
	
	@Override
	public HouseholdDataListResVO getHouseholdDataList(OuputReqVO reqVo) {
		
		final HouseholdDataListResVO result = new HouseholdDataListResVO();
		
		result.setBcVo( bankCardDao.getBankCard(reqVo.getBcNo()) );
		result.setList( ioDao.getHouseholdDataList(reqVo) );
		
		return result;
	}
}
