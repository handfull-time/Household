package com.utime.household.environment.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.utime.household.common.mapper.CommonMapper;
import com.utime.household.environment.dao.CategoryDao;
import com.utime.household.environment.mapper.CategoryMapper;
import com.utime.household.environment.vo.CategoryOwnerVO;
import com.utime.household.environment.vo.CategorySubVO;
import com.utime.household.environment.vo.CategoryVO;
import com.utime.household.environment.vo.ECategoryType;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
class CategoryDaoImpl implements CategoryDao{

	private final CommonMapper common;
	
	private final CategoryMapper mapper;
	
	@PostConstruct
	private void construct() {
		try {
			
			if( ! common.existTable("HH_CATEGORY") ) {
				log.info("CATEGORY 생성");
				mapper.createCategories();
			}

		}catch (Exception e) {
			log.error("", e);
		}

		try {
			
			if( ! common.existTable("HH_SUB_CATEGORY") ) {
				log.info("HH_SUB_CATEGORY 생성");
				mapper.createSubcategories();
				
				common.createIndex("HH_SUB_CATEGORY_OWNER_NO_INDX", "HH_SUB_CATEGORY", "OWNER_NO");
			}

		}catch (Exception e) {
			log.error("", e);
		}
	}
	
	@Override
	public int getCategoryCount() {
		return mapper.getCategoryCount();
	}

	@Override
	public List<CategoryVO> getCategoryList(ECategoryType cType) {
		return mapper.getCategoryList(cType);
	}
	
	@Override
	public List<CategoryOwnerVO> getCategoryOwnerList(ECategoryType cType) {
		return mapper.getCategoryOwnerList(cType);
	}

	@Override
	@Transactional( rollbackFor = Exception.class )
	public int saveOwnerCategory(CategoryVO vo) throws Exception {
		
		int result;
		if( vo.getNo() < 0L ) {
			
			if( mapper.sameCheckCategory(vo) ) {
				throw new Exception("동일 정보 있다.");
			}
			
			result = mapper.insertCategory(vo);
		}else {
			result = mapper.updateCategory(vo);
		}
		
		return result;
	}
	
	@Override
	public CategoryVO getOwnerCategory(String name) {
		
		return mapper.getCategoryFromName( name );
	}
	
	@Override
	public CategoryVO getOwnerCategory(long no) {
		
		return mapper.getCategoryFromNo( no );
	}
	
	@Override
	@Transactional( rollbackFor = Exception.class )
	public int deleteOwnerCategory(long no) throws Exception {
		return mapper.deleteCategory(no);
	}

	@Override
	public List<CategorySubVO> getSubCategoryList(long ownerNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CategorySubVO getSubCategory(long subNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int saveSubCategory(CategorySubVO sub) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteSubCategory(long subNo) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}
}
