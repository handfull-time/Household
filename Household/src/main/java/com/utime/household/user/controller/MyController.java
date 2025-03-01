package com.utime.household.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("My")
public class MyController {
	
	@GetMapping("Check")
    public ResponseEntity<?> refreshAccessToken() {
    	
    	return ResponseEntity.ok().body(Boolean.TRUE);
    }
}
