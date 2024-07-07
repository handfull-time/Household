package com.utime.household.dataIO.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.utime.household.common.util.HouseholdUtils;
import com.utime.household.environment.vo.StoreVO;

class StoreManage extends ArrayList<StoreVO>{

	private static final long serialVersionUID = 5680478376117083400L;
	
	private final Map<String, StoreVO> map = new HashMap<>();
	
	public StoreManage(List<StoreVO> stList) {
		for( StoreVO vo : stList ){
			this.add(vo);
			this.map.put(vo.getName(), vo);
		}
	}
	
	private StoreVO getSplit(String name, String key) {
		StoreVO result = null;
		if( name.indexOf(key) > 0 ){
			String [] split = name.split(key);
			for( String item : split ) {
				if( HouseholdUtils.isNotEmpty(item) ) {
					
					result = this.findStoreName(item);
	
					if( result != null ) {
						return result;
					}
				}
			}
		}
		
		return result;
	}
	
	private StoreVO findStoreName(String name ) {
		StoreVO result = this.map.get(name);
		if( result != null ) {
			return result;
		}
		
		for( StoreVO vo : this ){
			if( name.indexOf( vo.getName() ) > -1 ) {
				return vo;
			}
		}
		
		return null;
	}
	
	/**
	 * find -> store
	 * @param find
	 * @param store
	 */
	private void findToStore(StoreVO find, StoreVO store) {
		store.setNo( find.getNo() );
		store.setName( find.getName() );
		store.setCategoryNo( find.getCategoryNo() );
		store.setCategorySubNo( find.getCategorySubNo() );
	}
	
	public void genericStore( StoreVO store ) {

		if( store == null ) {
			return;
		}
		
		String name = store.getStore();
		
		if( name == null ) {
			return;
		}
		
		name = name.trim();
		store.setStore(name);
		
		if( name.length() < 1 ){
			return;
		}
		
		StoreVO find;
		find = this.findStoreName(name);
		if( find != null ) {
			this.findToStore( find, store );
			return;
		}
		
		find = this.getSplit(name, " ");
		if( find != null ) {
			this.findToStore( find, store );
			return;
		}
		
		find = this.getSplit(name, ",");
		if( find != null ) {
			this.findToStore( find, store );
			return;
		}
		
		find = this.getSplit(name, "_");
		if( find != null ) {
			this.findToStore( find, store );
			return;
		}
	}

}
