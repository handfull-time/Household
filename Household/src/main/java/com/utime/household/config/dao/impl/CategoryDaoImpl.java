package com.utime.household.config.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.utime.household.common.mapper.CommonMapper;
import com.utime.household.config.dao.CategoryDao;
import com.utime.household.config.mapper.CategoryMapper;
import com.utime.household.config.vo.CategoryVO;
import com.utime.household.config.vo.ECategoryType;

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
				mapper.createCategory();
			}

		}catch (Exception e) {
			log.error("", e);
		}
	}

	@Override
	public List<CategoryVO> getCategoryList(ECategoryType cType) {
		return mapper.getCategoryList(cType);
	}

	@Override
	@Transactional( rollbackFor = Exception.class )
	public int saveCategory(CategoryVO vo) throws Exception {
		
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
	public CategoryVO getCategory(String name) {
		
		return mapper.getCategoryFromName( name );
	}
	
	@Override
	public CategoryVO getCategory(long no) {
		
		return mapper.getCategoryFromNo( no );
	}
	
	@Override
	@Transactional( rollbackFor = Exception.class )
	public int deleteCategory(long no) throws Exception {
		return mapper.deleteCategory(no);
	}
}
