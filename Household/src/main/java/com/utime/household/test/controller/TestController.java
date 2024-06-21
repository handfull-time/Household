package com.utime.household.test.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.utime.household.environment.mapper.SequenceMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("Test")
public class TestController {
	
	private final SequenceMapper sequenceMapper;
	
	@GetMapping("CreateSequence")
	public int createSequence(){
		return sequenceMapper.createSequence();
	}
	
	final String key = "CardBank";
	@GetMapping("RegSequence")
	public int regSequence(){
		
		return sequenceMapper.registSequence(key, 0L);
	}
	@GetMapping("GetSequence")
	public long getSequence(){
		
		return sequenceMapper.selectCurrentValue(key);
	}
	
	@GetMapping("IncrementSequence")
	public int incrementSequence(){
		
		return sequenceMapper.updateIncrementValue(key);
	}
	
	@GetMapping("NextSequence")
	public long nextSequence(){
		
		return sequenceMapper.nextValue(key);
	}

}
