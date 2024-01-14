package com.utime.household.environment.service.impl;

import java.util.List;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.utime.household.common.util.HouseholdUtils;
import com.utime.household.common.vo.ReturnBasic;
import com.utime.household.environment.dao.CategoryDao;
import com.utime.household.environment.dao.StoreDao;
import com.utime.household.environment.service.CategoryService;
import com.utime.household.environment.vo.CategoryOwnerVO;
import com.utime.household.environment.vo.CategoryVO;
import com.utime.household.environment.vo.ECategoryType;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
class CategoryServiceImpl implements CategoryService {

	private final CategoryDao dao;
	
	private final StoreDao sd;
	
	/**
	 * 어플리케이션이 모두 동작 하고 난 후 이벤트
	 */
	@EventListener(ApplicationReadyEvent.class)
	private void handleApplicationReadyEvent() {
		
		final int cnt = dao.getCategoryCount();
		if( cnt > 0 )
			return;

		try {
			{
				final CategoryVO ownerCategory = new CategoryVO("입금", ECategoryType.Income );
				dao.saveCategory(ownerCategory);
				final long cateNo = ownerCategory.getNo();
				insertSubAndStore( cateNo, "급여", "급여" );
				insertSubAndStore( cateNo, "이체" );
				insertSubAndStore( cateNo, "현금" );
				insertSubAndStore( cateNo, "월세" );
				insertSubAndStore( cateNo, "적금" );
				insertSubAndStore( cateNo, "보험" );
				insertSubAndStore( cateNo, "기타" );
			}

			{
				final CategoryVO ownerCategory = new CategoryVO("지출", ECategoryType.Expense );
				dao.saveCategory(ownerCategory);
				final long cateNo = ownerCategory.getNo();
				insertSubAndStore( cateNo, "이체" );
				insertSubAndStore( cateNo, "현금" );
				insertSubAndStore( cateNo, "기타" );
			}

			{
				final CategoryVO ownerCategory = new CategoryVO("저축", ECategoryType.Saving );
				dao.saveCategory(ownerCategory);
				final long cateNo = ownerCategory.getNo();
				insertSubAndStore( cateNo, "적금" );
				insertSubAndStore( cateNo, "예금" );
				insertSubAndStore( cateNo, "보험" );
			}

			{
				final CategoryVO ownerCategory = new CategoryVO("식비", ECategoryType.Expense );
				dao.saveCategory(ownerCategory);
				final long cateNo = ownerCategory.getNo();
				insertSubAndStore( cateNo, "마트", "마트", "홈플러스" );
				insertSubAndStore( cateNo, "기타" );
			}
			
			{
				final CategoryVO ownerCategory = new CategoryVO("세금", ECategoryType.Expense );
				dao.saveCategory(ownerCategory);
				final long cateNo = ownerCategory.getNo();
				insertSubAndStore( cateNo, "월세", "주택" );
				insertSubAndStore( cateNo, "지방세", "지방세" );
				insertSubAndStore( cateNo, "자동차세", "자동차세" );
				insertSubAndStore( cateNo, "기타" );
			}
			
			{
				final CategoryVO ownerCategory = new CategoryVO("생활", ECategoryType.Expense );
				dao.saveCategory(ownerCategory);
				final long cateNo = ownerCategory.getNo();
				insertSubAndStore( cateNo, "가구", "가구", "삼성전자" );
				insertSubAndStore( cateNo, "가전", "삼성전자", "하이마트" );
				insertSubAndStore( cateNo, "생활", "쿠팡", "네이버" );
				insertSubAndStore( cateNo, "장난감", "쿠팡", "네이버" );
				insertSubAndStore( cateNo, "기타" );
			}
			
			{
				final CategoryVO ownerCategory = new CategoryVO("문화", ECategoryType.Expense );
				dao.saveCategory(ownerCategory);
				final long cateNo = ownerCategory.getNo();
				insertSubAndStore( cateNo, "여행" );
				insertSubAndStore( cateNo, "영화", "CGV", "롯데시네마", "메가박스" );
				insertSubAndStore( cateNo, "도서", "문고", "서점" );
				insertSubAndStore( cateNo, "음악", "멜론", "지니" );
				insertSubAndStore( cateNo, "기타" );
			}

			{
				final CategoryVO ownerCategory = new CategoryVO("의류", ECategoryType.Expense );
				dao.saveCategory(ownerCategory);
				final long cateNo = ownerCategory.getNo();
				insertSubAndStore( cateNo, "의복", "ABC", "나이키", "아울렛" );
				insertSubAndStore( cateNo, "세탁", "세탁" );
				insertSubAndStore( cateNo, "기타" );
			}

			{
				final CategoryVO ownerCategory = new CategoryVO("교육", ECategoryType.Expense );
				dao.saveCategory(ownerCategory);
				final long cateNo = ownerCategory.getNo();
				insertSubAndStore( cateNo, "학교", "학교" );
				insertSubAndStore( cateNo, "도서", "문고", "서점" );
				insertSubAndStore( cateNo, "학원", "태권도", "학원" );
				insertSubAndStore( cateNo, "강의", "도서관" );
				insertSubAndStore( cateNo, "기타" );
			}
			
			{
				final CategoryVO ownerCategory = new CategoryVO("의료", ECategoryType.Expense );
				dao.saveCategory(ownerCategory);
				final long cateNo = ownerCategory.getNo();
				insertSubAndStore( cateNo, "병원", "병원", "의원", "의료원");
				insertSubAndStore( cateNo, "약국", "약국", "메디팜");
				insertSubAndStore( cateNo, "검진");
				insertSubAndStore( cateNo, "기타");
			}
			
			{
				final CategoryVO ownerCategory = new CategoryVO("교통", ECategoryType.Expense );
				dao.saveCategory(ownerCategory);
				final long cateNo = ownerCategory.getNo();
				insertSubAndStore( cateNo, "대중교통", "버스", "지하철");
				insertSubAndStore( cateNo, "차량", "정유", "주유소",  "하이패스");
				insertSubAndStore( cateNo, "정비", "자동차", "모비스");
				insertSubAndStore( cateNo, "기타");
			}

			{
				final CategoryVO ownerCategory = new CategoryVO("통신", ECategoryType.Expense );
				dao.saveCategory(ownerCategory);
				final long cateNo = ownerCategory.getNo();
				insertSubAndStore( cateNo, "휴대폰", "SK", "KT", "LGU+");
				insertSubAndStore( cateNo, "인터넷", "브로드밴드");
				insertSubAndStore( cateNo, "기타");
			}

			{
				final CategoryVO ownerCategory = new CategoryVO("관리", ECategoryType.Income );
				dao.saveCategory(ownerCategory);
				final long cateNo = ownerCategory.getNo();
				insertSubAndStore( cateNo, "관리비", "관리", "아파트");
				insertSubAndStore( cateNo, "난방", "도시가스");
				insertSubAndStore( cateNo, "기타");
			}
			
			{
				final CategoryVO ownerCategory = new CategoryVO("경조사", ECategoryType.Income );
				dao.saveCategory(ownerCategory);
				final long cateNo = ownerCategory.getNo();
				insertSubAndStore( cateNo, "부모님");
				insertSubAndStore( cateNo, "축의금");
				insertSubAndStore( cateNo, "조의금");
				insertSubAndStore( cateNo, "기부");
				insertSubAndStore( cateNo, "선물");
				insertSubAndStore( cateNo, "기타");
			}

			{
				final CategoryVO ownerCategory = new CategoryVO("용돈", ECategoryType.Income );
				dao.saveCategory(ownerCategory);
				final long cateNo = ownerCategory.getNo();
				insertSubAndStore( cateNo, "도토리1");
				insertSubAndStore( cateNo, "도토리2");
				insertSubAndStore( cateNo, "도토리3");
			}
			
			{
				final CategoryVO ownerCategory = new CategoryVO("보험", ECategoryType.Income );
				dao.saveCategory(ownerCategory);
				final long cateNo = ownerCategory.getNo();
				insertSubAndStore( cateNo, "실비", "화재");
				insertSubAndStore( cateNo, "자동차", "Carrot");
			}
			
		} catch (Exception e) {
			log.error("", e);
		}

	}
	
	private void insertSubAndStore(long cateNo, String string,  String ... stores) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<CategoryVO> getCategoryList() {
		
		return dao.getCategoryList(null);
	}
	
	@Override
	public List<CategoryVO> getCategoryList(ECategoryType cType) {
		
		return dao.getCategoryList(cType);
	}
	
	@Override
	public List<CategoryOwnerVO> getCategoryOwnerList() {
		return dao.getCategoryOwnerList(null);
	}

	@Override
	public ReturnBasic saveCategory(CategoryVO vo) {
		final ReturnBasic result = new ReturnBasic();
		
		if( HouseholdUtils.isEmpty( vo.getName() )) {
			result.setCodeMessage("ECS0U1", "이름은 필수");
			return result;
		}
		
		vo.setName( vo.getName().trim() );
		
		try {
			this.dao.saveCategory(vo);
		} catch (Exception e) {
			log.error("", e);
			result.setCodeMessage("ECS0U2", e.getMessage());
		}
		
		return result;
	}
	
	@Override
	public ReturnBasic deleteCategory(long no) {
		final ReturnBasic result = new ReturnBasic();
		try {
			this.dao.deleteCategory(no);
		} catch (Exception e) {
			result.setCodeMessage("ECS0P1", e.getMessage());
		}
		return result;
	}
	
	@Override
	public CategoryVO getCategory(long no) {
		final CategoryVO result;
		
		if( no < 0L ) {
			result = new CategoryVO();
			result.setNo( no );
			result.setCType(ECategoryType.Expense);
		}else {
			result = this.dao.getCategory(no);
		}
		
		return result;
	}
}
