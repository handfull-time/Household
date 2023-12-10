package com.utime.household.config.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.utime.household.common.util.HouseholdUtils;
import com.utime.household.common.vo.ReturnBasic;
import com.utime.household.config.dao.CategoryDao;
import com.utime.household.config.dao.StoreDao;
import com.utime.household.config.service.CategoryService;
import com.utime.household.config.vo.CategoryVO;
import com.utime.household.config.vo.ECategoryType;
import com.utime.household.config.vo.StoreVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
class CategoryServiceImpl implements CategoryService {

	private final CategoryDao dao;
	
	private final StoreDao sd;
	
	private void initStore( String categoryName, String ... stores ) throws Exception{
		final CategoryVO vo = dao.getCategory(categoryName);
		if( vo != null ) {
			final long categoryNo = vo.getNo();
			
			for( String store : stores) {
				sd.saveStore( new StoreVO(store, categoryNo) );
			}
		}
	}
	
	private void initData() throws Exception {
		
		dao.saveCategory(new CategoryVO("급여", ECategoryType.Income ));
		dao.saveCategory(new CategoryVO("월세", ECategoryType.Income ));
		dao.saveCategory(new CategoryVO("저축만기", ECategoryType.Income ));
		dao.saveCategory(new CategoryVO("환불", ECategoryType.Income ));
		
		dao.saveCategory(new CategoryVO("이체", ECategoryType.Saving ));
		dao.saveCategory(new CategoryVO("적금", ECategoryType.Saving ));
		dao.saveCategory(new CategoryVO("예금", ECategoryType.Saving ));

		dao.saveCategory(new CategoryVO("식비", ECategoryType.Expense ));
		dao.saveCategory(new CategoryVO("마트", ECategoryType.Expense ));
		dao.saveCategory(new CategoryVO("교통", ECategoryType.Expense ));
		dao.saveCategory(new CategoryVO("외식", ECategoryType.Expense ));
		dao.saveCategory(new CategoryVO("관리", ECategoryType.Expense ));
		dao.saveCategory(new CategoryVO("통신", ECategoryType.Expense ));
		dao.saveCategory(new CategoryVO("효도", ECategoryType.Expense ));
		dao.saveCategory(new CategoryVO("보험", ECategoryType.Expense ));
		dao.saveCategory(new CategoryVO("세금", ECategoryType.Expense ));
		dao.saveCategory(new CategoryVO("오락", ECategoryType.Expense ));
		dao.saveCategory(new CategoryVO("생활", ECategoryType.Expense ));
		dao.saveCategory(new CategoryVO("교육", ECategoryType.Expense ));
		dao.saveCategory(new CategoryVO("교양", ECategoryType.Expense ));
		dao.saveCategory(new CategoryVO("피복", ECategoryType.Expense ));
		dao.saveCategory(new CategoryVO("회비", ECategoryType.Expense ));
		dao.saveCategory(new CategoryVO("의료", ECategoryType.Expense ));
		
		this.initStore("마트", "마트", "홈플러스");
		this.initStore("의료", "병원", "약국", "메디팜", "의원", "의료원");
		this.initStore("교통", "버스", "지하철", "정유", "주유소", "자동차");
		this.initStore("관리", "관리비", "도시가스");
		this.initStore("통신", "통신료");
		this.initStore("회비", "연회비");
		this.initStore("급여", "급여");
		this.initStore("월세", "월세");
		this.initStore("교육", "태권도", "학원");
	}

	@Override
	public List<CategoryVO> getCategoryList() {
		
		List<CategoryVO> result = dao.getCategoryList(null);
		if( result.size() < 1 ) {
			
			try {
				this.initData();
			} catch (Exception e) {
				log.error("", e);
			}
			
			result = dao.getCategoryList(null);
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
			this.dao.saveCategory(vo);
		} catch (Exception e) {
			log.error("", e);
			result.setCodeMessage("ECS0U2", e.getMessage());
		}
		
		return result;
	}
	
	@Override
	public ReturnBasic deleteCategory(CategoryVO vo) {
		final ReturnBasic result = new ReturnBasic();
		try {
			this.dao.deleteCategory(vo);
		} catch (Exception e) {
			result.setCodeMessage("ECS0P1", e.getMessage());
		}
		return result;
	}
}
