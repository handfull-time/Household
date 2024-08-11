package com.utime.household.environment.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.utime.household.common.util.HouseholdUtils;
import com.utime.household.common.vo.ReturnBasic;
import com.utime.household.environment.dao.CategoryDao;
import com.utime.household.environment.dao.StoreDao;
import com.utime.household.environment.service.CategoryService;
import com.utime.household.environment.vo.CategoryOwnerList;
import com.utime.household.environment.vo.CategoryOwnerVO;
import com.utime.household.environment.vo.CategorySubStoreVO;
import com.utime.household.environment.vo.CategorySubVO;
import com.utime.household.environment.vo.CategoryVO;
import com.utime.household.environment.vo.ECategoryType;
import com.utime.household.environment.vo.StoreVO;

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
				dao.saveOwnerCategory(ownerCategory);
				insertSubAndStore( ownerCategory, "급여", "급여" );
				insertSubAndStore( ownerCategory, "이체" );
				insertSubAndStore( ownerCategory, "현금" );
				insertSubAndStore( ownerCategory, "월세" );
				insertSubAndStore( ownerCategory, "적금" );
				insertSubAndStore( ownerCategory, "보험" );
				insertSubAndStore( ownerCategory, "기타" );
			}

			{
				final CategoryVO ownerCategory = new CategoryVO("지출", ECategoryType.Expense );
				dao.saveOwnerCategory(ownerCategory);
				insertSubAndStore( ownerCategory, "이체" );
				insertSubAndStore( ownerCategory, "현금" );
				insertSubAndStore( ownerCategory, "기타" );
			}

			{
				final CategoryVO ownerCategory = new CategoryVO("저축", ECategoryType.Saving );
				dao.saveOwnerCategory(ownerCategory);
				insertSubAndStore( ownerCategory, "적금" );
				insertSubAndStore( ownerCategory, "예금" );
				insertSubAndStore( ownerCategory, "보험" );
			}

			{
				final CategoryVO ownerCategory = new CategoryVO("식비", ECategoryType.Expense );
				dao.saveOwnerCategory(ownerCategory);
				insertSubAndStore( ownerCategory, "마트", "마트", "홈플러스" );
				insertSubAndStore( ownerCategory, "기타" );
			}
			
			{
				final CategoryVO ownerCategory = new CategoryVO("세금", ECategoryType.Expense );
				dao.saveOwnerCategory(ownerCategory);
				insertSubAndStore( ownerCategory, "월세", "주택" );
				insertSubAndStore( ownerCategory, "자동차세", "자동차세" );
				insertSubAndStore( ownerCategory, "기타", "지방세", "주민세" );
			}
			
			{
				final CategoryVO ownerCategory = new CategoryVO("생활", ECategoryType.Expense );
				dao.saveOwnerCategory(ownerCategory);
				insertSubAndStore( ownerCategory, "가구", "가구" );
				insertSubAndStore( ownerCategory, "가전", "하이마트", "베스트샵" );
				insertSubAndStore( ownerCategory, "생활", "쿠팡", "네이버", "11번가", "인터파크" );
				insertSubAndStore( ownerCategory, "장난감", "장난감" );
				insertSubAndStore( ownerCategory, "기타" );
			}
			
			{
				final CategoryVO ownerCategory = new CategoryVO("문화", ECategoryType.Expense );
				dao.saveOwnerCategory(ownerCategory);
				insertSubAndStore( ownerCategory, "여행" );
				insertSubAndStore( ownerCategory, "영화", "CGV", "롯데시네마", "메가박스" );
				insertSubAndStore( ownerCategory, "도서", "문고", "서점" );
				insertSubAndStore( ownerCategory, "음악", "멜론", "지니" );
				insertSubAndStore( ownerCategory, "기타" );
			}

			{
				final CategoryVO ownerCategory = new CategoryVO("의류", ECategoryType.Expense );
				dao.saveOwnerCategory(ownerCategory);
				insertSubAndStore( ownerCategory, "의복", "나이키", "아울렛" );
				insertSubAndStore( ownerCategory, "세탁", "세탁" );
				insertSubAndStore( ownerCategory, "신발", "ABC" );
				insertSubAndStore( ownerCategory, "기타" );
			}

			{
				final CategoryVO ownerCategory = new CategoryVO("교육", ECategoryType.Expense );
				dao.saveOwnerCategory(ownerCategory);
				insertSubAndStore( ownerCategory, "학교", "학교" );
				insertSubAndStore( ownerCategory, "학원", "태권도", "학원" );
				insertSubAndStore( ownerCategory, "강의", "도서관" );
				insertSubAndStore( ownerCategory, "교제" );
				insertSubAndStore( ownerCategory, "기타" );
			}
			
			{
				final CategoryVO ownerCategory = new CategoryVO("의료", ECategoryType.Expense );
				dao.saveOwnerCategory(ownerCategory);
				insertSubAndStore( ownerCategory, "병원", "병원", "의원", "의료원");
				insertSubAndStore( ownerCategory, "약국", "약국", "메디팜");
				insertSubAndStore( ownerCategory, "검진");
				insertSubAndStore( ownerCategory, "기타");
			}
			
			{
				final CategoryVO ownerCategory = new CategoryVO("교통", ECategoryType.Expense );
				dao.saveOwnerCategory(ownerCategory);
				insertSubAndStore( ownerCategory, "대중교통", "버스", "지하철");
				insertSubAndStore( ownerCategory, "차량", "정유", "주유소",  "하이패스");
				insertSubAndStore( ownerCategory, "정비", "자동차", "모비스");
				insertSubAndStore( ownerCategory, "기타");
			}

			{
				final CategoryVO ownerCategory = new CategoryVO("통신", ECategoryType.Expense );
				dao.saveOwnerCategory(ownerCategory);
				insertSubAndStore( ownerCategory, "휴대폰", "SK", "KT", "LGU+");
				insertSubAndStore( ownerCategory, "인터넷", "브로드밴드");
				insertSubAndStore( ownerCategory, "기타");
			}

			{
				final CategoryVO ownerCategory = new CategoryVO("관리", ECategoryType.Income );
				dao.saveOwnerCategory(ownerCategory);
				insertSubAndStore( ownerCategory, "관리비", "관리", "아파트");
				insertSubAndStore( ownerCategory, "난방", "도시가스");
				insertSubAndStore( ownerCategory, "기타");
			}
			
			{
				final CategoryVO ownerCategory = new CategoryVO("경조사", ECategoryType.Income );
				dao.saveOwnerCategory(ownerCategory);
				insertSubAndStore( ownerCategory, "부모님");
				insertSubAndStore( ownerCategory, "축의금");
				insertSubAndStore( ownerCategory, "조의금");
				insertSubAndStore( ownerCategory, "기부");
				insertSubAndStore( ownerCategory, "선물");
				insertSubAndStore( ownerCategory, "기타");
			}

			{
				final CategoryVO ownerCategory = new CategoryVO("용돈", ECategoryType.Income );
				dao.saveOwnerCategory(ownerCategory);
				insertSubAndStore( ownerCategory, "도토리1");
				insertSubAndStore( ownerCategory, "도토리2");
				insertSubAndStore( ownerCategory, "도토리3");
			}
			
			{
				final CategoryVO ownerCategory = new CategoryVO("보험", ECategoryType.Income );
				dao.saveOwnerCategory(ownerCategory);
				insertSubAndStore( ownerCategory, "실비", "화재");
				insertSubAndStore( ownerCategory, "자동차", "Carrot");
			}
			
		} catch (Exception e) {
			log.error("", e);
		}

	}
	
	/**
	 * 서브 카테고리와 키워드 추가.
	 * @param cateNo
	 * @param string
	 * @param stores
	 * @throws Exception 
	 */
	private void insertSubAndStore(CategoryVO ownerVo, String subName,  String ... stores) throws Exception {
		final CategorySubVO sub = new CategorySubVO();
		sub.setOwner( ownerVo );
		sub.setNo(-1L);
		sub.setName(subName);
		
		dao.saveSubCategory(sub);
		
		final long ownerNo = ownerVo.getNo();
		final long subNo = sub.getNo();
		
		for( String store : stores ) {
			
			final StoreVO vo = new StoreVO();
			vo.setCategoryNo( ownerNo );
			vo.setCategorySubNo( subNo );
			vo.setName( store );
			
			sd.saveStore( vo );
		}
		
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
		
		return this.getCategoryOwnerList(null);
	}
	
	@Override
	public List<CategoryOwnerVO> getCategoryOwnerList(ECategoryType cType) {

		final List<CategoryOwnerVO> result = dao.getCategoryOwnerList(cType);
		
		final Map<Long, List<StoreVO>> storeMap = new HashMap<>();
		{
			final List<StoreVO> storeList = sd.getStoreList();
			storeList.forEach( item -> {
				final long subNo = item.getCategorySubNo();
				
				final List<StoreVO> lst;
				if( storeMap.containsKey(subNo) ) {
					lst = storeMap.get(subNo);
				}else {
					lst = new ArrayList<>();
					storeMap.put(subNo, lst);
				}
				
				lst.add(item);
			}); 
		}
		
		result.forEach( ownerItem -> {
			final List<CategorySubStoreVO> subCategories = ownerItem.getSubCategories();
			if( subCategories != null && subCategories.size() > 0 ) {
				
				subCategories.forEach( subItem -> {
					subItem.setList( storeMap.get(subItem.getNo()) );
				});
				
			}
		});
		
		return result; 
	}
	
	@Override
	public CategoryOwnerList getCategoryOwnerListOfList(ECategoryType cType) {

		final List<CategoryOwnerVO> list = dao.getCategoryOwnerList(cType);
		final CategoryOwnerList result = new CategoryOwnerList();
		final List<CategoryVO> ownerList = new ArrayList<>();
		final List<List<CategorySubVO>> subList = new ArrayList<>();
		
		result.setOwnerList(ownerList);
		result.setSubList(subList);
		
		int max = -1;
		for( CategoryOwnerVO owner : list ) {
			ownerList.add(owner);
			if( max < owner.getSubCategories().size() ) {
				max = owner.getSubCategories().size();
			}
		}
		max++;

		for( int i=0 ; i<=max ; i++ ) {
			subList.add(new ArrayList<>());
		}

		
		final CategorySubVO empty = new CategorySubVO();
		empty.setNo(-1L);
		empty.setName("");
		empty.setOwner(null);
		

		for( int i=0 ; i<max ; i++ ) {
			final List<CategorySubVO> addList = subList.get(i);
			
			for( CategoryOwnerVO owner : list ) {
				
				if( i < owner.getSubCategories().size() ) {
					final CategorySubStoreVO sub = owner.getSubCategories().get(i);
					addList.add(sub);
				}else if( i == owner.getSubCategories().size() ){
					final CategorySubVO add = new CategorySubVO();
					add.setNo(-2L);
					add.setName(owner.getName());
					add.setOwner(owner);
					addList.add(add);
				}else {
					addList.add(empty);
				}
			}
		}
		
		return result;
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
			this.dao.saveOwnerCategory(vo);
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
			this.dao.deleteOwnerCategory(no);
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
			result = this.dao.getOwnerCategory(no);
		}
		
		return result;
	}

	@Override
	public List<CategorySubVO> getSubCategoryList(long ownerNo) {
		return dao.getSubCategoryList(ownerNo);
	}

	@Override
	public CategorySubVO getSubCategory(long subNo) {
		final CategorySubVO result;
		
		if( subNo < 0L ) {
			result = new CategorySubVO();
			result.setNo( subNo );
		}else {
			result = this.dao.getSubCategory(subNo);
		}
		
		return result;
	}

	@Override
	public ReturnBasic saveSubCategory(CategorySubVO sub) {
		final ReturnBasic result = new ReturnBasic();
		
		if( HouseholdUtils.isEmpty( sub.getName() )) {
			result.setCodeMessage("ECS0D1", "이름은 필수");
			return result;
		}
		
		if( sub.getOwner() == null || sub.getOwner().getNo() < -1L ) {
			result.setCodeMessage("ECS0D2", "대분류 선택은 필수");
			return result;
		}
		
		sub.setName( sub.getName().trim() );
		
		try {
			this.dao.saveSubCategory(sub);
		} catch (Exception e) {
			log.error("", e);
			result.setCodeMessage("ECS0D3", e.getMessage());
		}
		
		return result;
	}

	@Override
	public ReturnBasic deleteSubCategory(long subNo) {
		final ReturnBasic result = new ReturnBasic();
		try {
			this.dao.deleteSubCategory(subNo);
		} catch (Exception e) {
			result.setCodeMessage("ECS0W1", e.getMessage());
		}
		return result;
	}
	
	
}
