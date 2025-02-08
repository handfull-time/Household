package com.utime.household.test.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Controller
public class TestViewController {
	
    @GetMapping("V/signin.html")
    public String staticView(Model model) {
        return "TestView/signin";
    }

    @GetMapping("View/{path}.html")
    public String dynamicView(HttpServletRequest request, Model model, @PathVariable("path") String path) {
    	
    	final String uri = request.getRequestURI().substring(1);
        model.addAttribute("currentURI", uri.substring(uri.indexOf("/")) );
        
        return "TestView/" + path;  // templates/View/{path}.html 를 렌더링
    }

    @Setter
    @Getter
    public static class User{
    	long userNo;
    	String name;
    	String email;
    	int age;
    	String gender;
    	String note;
    }
    
    @GetMapping("View/{path}.layer")
    public String layerView(Model model, @PathVariable("path") String path) {
    	
    	User user = new User();
    	user.setUserNo(2541L);
    	user.setName("홍길동");
    	user.setEmail("asdf@pp.com");
    	user.setAge(24);
    	user.setGender("Man");
    	user.setNote("모범학생");
    	
        model.addAttribute("user", user );
        
        return "TestView/" + path;
    }
    
    @Setter
    @Getter
    public static class ResBasic{
    	String code;
    	String message;
    }
    
    
    @PostMapping("Api/SaveUser.json")
    public ResponseEntity<ResBasic> saveUser(Model model, @RequestBody User user) {
    	
    	ResBasic result = new ResBasic();
    	result.setCode("success");
        
        return ResponseEntity.ok(result);
    }

}
