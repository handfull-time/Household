package com.utime.household.test.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.utime.household.user.vo.UserVo;

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
    	
//    	final String uri = request.getRequestURI().substring(1);
//        model.addAttribute("currentURI", uri.substring(uri.indexOf("/")) );
        
    	if( "menuA_10".equals( path ) ){
        	UserVo user = new UserVo();
        	user.setUserNo(2541L);
        	user.setId("홍길동");
        	user.setImage("data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0idXRmLTgiPz4NCjwhLS0gR2VuZXJhdG9yOiBBZG9iZSBJbGx1c3RyYXRvciAyNS4wLjAsIFNWRyBFeHBvcnQgUGx1Zy1JbiAuIFNWRyBWZXJzaW9uOiA2LjAwIEJ1aWxkIDApICAtLT4NCjxzdmcgdmVyc2lvbj0iMS4xIiBpZD0iTGF5ZXJfMSIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIiB4bWxuczp4bGluaz0iaHR0cDovL3d3dy53My5vcmcvMTk5OS94bGluayIgeD0iMHB4IiB5PSIwcHgiDQoJIHZpZXdCb3g9IjAgMCAxMDguMyAxMDguMyIgc3R5bGU9ImVuYWJsZS1iYWNrZ3JvdW5kOm5ldyAwIDAgMTA4LjMgMTA4LjM7IiB4bWw6c3BhY2U9InByZXNlcnZlIj4NCjxzdHlsZSB0eXBlPSJ0ZXh0L2NzcyI+DQoJLnN0MHtmaWxsOiNFNkU2RTY7fQ0KCS5zdDF7ZmlsbDojRkZCOEI4O30NCgkuc3Qye2ZpbGw6IzU3NUE4OTt9DQoJLnN0M3tmaWxsOiMyRjJFNDE7fQ0KPC9zdHlsZT4NCjxnIGlkPSJHcm91cF80NSIgdHJhbnNmb3JtPSJ0cmFuc2xhdGUoLTE5MSAtMTUyLjA3OSkiPg0KCTxnIGlkPSJHcm91cF8zMCIgdHJhbnNmb3JtPSJ0cmFuc2xhdGUoMjgyLjI0NiAyMjQuMzUzKSI+DQoJCTxwYXRoIGlkPSJQYXRoXzk0NCIgY2xhc3M9InN0MCIgZD0iTTE3LjEtMTguMWMwLDEwLjUtMywyMC44LTguOCwyOS42Yy0xLjIsMS45LTIuNSwzLjYtNCw1LjNjLTMuNCw0LTcuMyw3LjQtMTEuNiwxMC4zDQoJCQljLTEuMiwwLjgtMi40LDEuNS0zLjYsMi4yYy02LjUsMy42LTEzLjcsNS44LTIxLDYuNWMtMS43LDAuMi0zLjQsMC4yLTUuMSwwLjJjLTQuNywwLTkuNC0wLjYtMTQtMS44Yy0yLjYtMC43LTUuMS0xLjYtNy42LTIuNg0KCQkJYy0xLjMtMC41LTIuNS0xLjEtMy43LTEuOGMtMi45LTEuNS01LjYtMy4zLTguMi01LjNjLTEuMi0wLjktMi4zLTEuOS0zLjQtMi45Qy05NS44LDEuMy05Ny4xLTMzLTc2LjgtNTQuOXM1NC42LTIzLjMsNzYuNS0yLjkNCgkJCUMxMC44LTQ3LjYsMTcuMS0zMy4yLDE3LjEtMTguMUwxNy4xLTE4LjF6Ii8+DQoJCTxwYXRoIGlkPSJQYXRoXzk0NSIgY2xhc3M9InN0MSIgZD0iTS01MC4yLTEzLjJjMCwwLDQuOSwxMy43LDEuMSwyMS40czYsMTYuNCw2LDE2LjRzMjUuOC0xMy4xLDIyLjUtMTkuN3MtOC44LTE1LjMtNy43LTIwLjgNCgkJCUwtNTAuMi0xMy4yeiIvPg0KCQk8ZWxsaXBzZSBpZD0iRWxsaXBzZV8xODUiIGNsYXNzPSJzdDEiIGN4PSItNDAuNiIgY3k9Ii0yNS41IiByeD0iMTcuNSIgcnk9IjE3LjUiLz4NCgkJPHBhdGggaWQ9IlBhdGhfOTQ2IiBjbGFzcz0ic3QyIiBkPSJNLTUxLjEsMzQuMmMtMi42LTAuNy01LjEtMS42LTcuNi0yLjZsMC41LTEzLjNsNC45LTExYzEuMSwwLjksMi4zLDEuNiwzLjUsMi4zDQoJCQljMC4zLDAuMiwwLjYsMC4zLDAuOSwwLjVjNC42LDIuMiwxMi4yLDQuMiwxOS41LTEuM2MyLjctMi4xLDUtNC43LDYuNy03LjZMLTguOCw5bDAuNyw4LjRsMC44LDkuOGMtMS4yLDAuOC0yLjQsMS41LTMuNiwyLjINCgkJCWMtNi41LDMuNi0xMy43LDUuOC0yMSw2LjVjLTEuNywwLjItMy40LDAuMi01LjEsMC4yQy00MS44LDM2LjEtNDYuNSwzNS40LTUxLjEsMzQuMnoiLz4NCgkJPHBhdGggaWQ9IlBhdGhfOTQ3IiBjbGFzcz0ic3QyIiBkPSJNLTQ3LjctMC45TC00Ny43LTAuOWwtMC43LDcuMmwtMC40LDMuOGwtMC41LDUuNmwtMS44LDE4LjVjLTIuNi0wLjctNS4xLTEuNi03LjYtMi42DQoJCQljLTEuMy0wLjUtMi41LTEuMS0zLjctMS44Yy0yLjktMS41LTUuNi0zLjMtOC4yLTUuM2wtMS45LTlsMC4xLTAuMUwtNDcuNy0wLjl6Ii8+DQoJCTxwYXRoIGlkPSJQYXRoXzk0OCIgY2xhc3M9InN0MiIgZD0iTS0xMC45LDI5LjNjLTYuNSwzLjYtMTMuNyw1LjgtMjEsNi41YzAuNC02LjcsMS0xMy4xLDEuNi0xOC44YzAuMy0yLjksMC43LTUuNywxLjEtOC4yDQoJCQljMS4yLTgsMi41LTEzLjUsMy40LTE0LjJsNi4xLDRMNC45LDcuM2wtMC41LDkuNWMtMy40LDQtNy4zLDcuNC0xMS42LDEwLjNDLTguNSwyNy45LTkuNywyOC43LTEwLjksMjkuM3oiLz4NCgkJPHBhdGggaWQ9IlBhdGhfOTQ5IiBjbGFzcz0ic3QyIiBkPSJNLTcwLjUsMjQuNmMtMS4yLTAuOS0yLjMtMS45LTMuNC0yLjlsMC45LTYuMWwwLjctMC4xbDMuMS0wLjRsNi44LDE0LjgNCgkJCUMtNjUuMiwyOC4zLTY3LjksMjYuNi03MC41LDI0LjZMLTcwLjUsMjQuNnoiLz4NCgkJPHBhdGggaWQ9IlBhdGhfOTUwIiBjbGFzcz0ic3QyIiBkPSJNOC4zLDExLjVjLTEuMiwxLjktMi41LDMuNi00LDUuM2MtMy40LDQtNy4zLDcuNC0xMS42LDEwLjNjLTEuMiwwLjgtMi40LDEuNS0zLjYsMi4ybC0wLjYtMi44DQoJCQlsMy41LTkuMWw0LjItMTEuMWw4LjgsMS4xQzYuMSw4LjcsNy4yLDEwLjEsOC4zLDExLjV6Ii8+DQoJCTxwYXRoIGlkPSJQYXRoXzk1MSIgY2xhc3M9InN0MyIgZD0iTS0yMy45LTQxLjRjLTIuNy00LjMtNi44LTcuNS0xMS42LTguOWwtMy42LDIuOWwxLjQtMy4zYy0xLjItMC4yLTIuMy0wLjItMy41LTAuMmwtMy4yLDQuMQ0KCQkJbDEuMy00Yy01LjYsMC43LTEwLjcsMy43LTE0LDguM2MtNC4xLDUuOS00LjgsMTQuMS0wLjgsMjBjMS4xLTMuNCwyLjQtNi42LDMuNS05LjljMC45LDAuMSwxLjcsMC4xLDIuNiwwbDEuMy0zLjFsMC40LDMNCgkJCWM0LjItMC40LDEwLjMtMS4yLDE0LjMtMS45bC0wLjQtMi4zbDIuMywxLjljMS4yLTAuMywxLjktMC41LDEuOS0wLjdjMi45LDQuNyw1LjgsNy43LDguOCwxMi41Qy0yMi4xLTI5LjgtMjAuMi0zNS4zLTIzLjktNDEuNHoiDQoJCQkvPg0KCQk8ZWxsaXBzZSBpZD0iRWxsaXBzZV8xODYiIGNsYXNzPSJzdDEiIGN4PSItMjQuOSIgY3k9Ii0yNi4xIiByeD0iMS4yIiByeT0iMi40Ii8+DQoJPC9nPg0KPC9nPg0KPC9zdmc+DQo=");
            model.addAttribute("user", user );
    	}
    	
    	
    	
    	
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
    	String image;
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
    	user.setImage("data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0idXRmLTgiPz4NCjwhLS0gR2VuZXJhdG9yOiBBZG9iZSBJbGx1c3RyYXRvciAyNS4wLjAsIFNWRyBFeHBvcnQgUGx1Zy1JbiAuIFNWRyBWZXJzaW9uOiA2LjAwIEJ1aWxkIDApICAtLT4NCjxzdmcgdmVyc2lvbj0iMS4xIiBpZD0iTGF5ZXJfMSIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIiB4bWxuczp4bGluaz0iaHR0cDovL3d3dy53My5vcmcvMTk5OS94bGluayIgeD0iMHB4IiB5PSIwcHgiDQoJIHZpZXdCb3g9IjAgMCAxMDguMyAxMDguMyIgc3R5bGU9ImVuYWJsZS1iYWNrZ3JvdW5kOm5ldyAwIDAgMTA4LjMgMTA4LjM7IiB4bWw6c3BhY2U9InByZXNlcnZlIj4NCjxzdHlsZSB0eXBlPSJ0ZXh0L2NzcyI+DQoJLnN0MHtmaWxsOiNFNkU2RTY7fQ0KCS5zdDF7ZmlsbDojRkZCOEI4O30NCgkuc3Qye2ZpbGw6IzU3NUE4OTt9DQoJLnN0M3tmaWxsOiMyRjJFNDE7fQ0KPC9zdHlsZT4NCjxnIGlkPSJHcm91cF80NSIgdHJhbnNmb3JtPSJ0cmFuc2xhdGUoLTE5MSAtMTUyLjA3OSkiPg0KCTxnIGlkPSJHcm91cF8zMCIgdHJhbnNmb3JtPSJ0cmFuc2xhdGUoMjgyLjI0NiAyMjQuMzUzKSI+DQoJCTxwYXRoIGlkPSJQYXRoXzk0NCIgY2xhc3M9InN0MCIgZD0iTTE3LjEtMTguMWMwLDEwLjUtMywyMC44LTguOCwyOS42Yy0xLjIsMS45LTIuNSwzLjYtNCw1LjNjLTMuNCw0LTcuMyw3LjQtMTEuNiwxMC4zDQoJCQljLTEuMiwwLjgtMi40LDEuNS0zLjYsMi4yYy02LjUsMy42LTEzLjcsNS44LTIxLDYuNWMtMS43LDAuMi0zLjQsMC4yLTUuMSwwLjJjLTQuNywwLTkuNC0wLjYtMTQtMS44Yy0yLjYtMC43LTUuMS0xLjYtNy42LTIuNg0KCQkJYy0xLjMtMC41LTIuNS0xLjEtMy43LTEuOGMtMi45LTEuNS01LjYtMy4zLTguMi01LjNjLTEuMi0wLjktMi4zLTEuOS0zLjQtMi45Qy05NS44LDEuMy05Ny4xLTMzLTc2LjgtNTQuOXM1NC42LTIzLjMsNzYuNS0yLjkNCgkJCUMxMC44LTQ3LjYsMTcuMS0zMy4yLDE3LjEtMTguMUwxNy4xLTE4LjF6Ii8+DQoJCTxwYXRoIGlkPSJQYXRoXzk0NSIgY2xhc3M9InN0MSIgZD0iTS01MC4yLTEzLjJjMCwwLDQuOSwxMy43LDEuMSwyMS40czYsMTYuNCw2LDE2LjRzMjUuOC0xMy4xLDIyLjUtMTkuN3MtOC44LTE1LjMtNy43LTIwLjgNCgkJCUwtNTAuMi0xMy4yeiIvPg0KCQk8ZWxsaXBzZSBpZD0iRWxsaXBzZV8xODUiIGNsYXNzPSJzdDEiIGN4PSItNDAuNiIgY3k9Ii0yNS41IiByeD0iMTcuNSIgcnk9IjE3LjUiLz4NCgkJPHBhdGggaWQ9IlBhdGhfOTQ2IiBjbGFzcz0ic3QyIiBkPSJNLTUxLjEsMzQuMmMtMi42LTAuNy01LjEtMS42LTcuNi0yLjZsMC41LTEzLjNsNC45LTExYzEuMSwwLjksMi4zLDEuNiwzLjUsMi4zDQoJCQljMC4zLDAuMiwwLjYsMC4zLDAuOSwwLjVjNC42LDIuMiwxMi4yLDQuMiwxOS41LTEuM2MyLjctMi4xLDUtNC43LDYuNy03LjZMLTguOCw5bDAuNyw4LjRsMC44LDkuOGMtMS4yLDAuOC0yLjQsMS41LTMuNiwyLjINCgkJCWMtNi41LDMuNi0xMy43LDUuOC0yMSw2LjVjLTEuNywwLjItMy40LDAuMi01LjEsMC4yQy00MS44LDM2LjEtNDYuNSwzNS40LTUxLjEsMzQuMnoiLz4NCgkJPHBhdGggaWQ9IlBhdGhfOTQ3IiBjbGFzcz0ic3QyIiBkPSJNLTQ3LjctMC45TC00Ny43LTAuOWwtMC43LDcuMmwtMC40LDMuOGwtMC41LDUuNmwtMS44LDE4LjVjLTIuNi0wLjctNS4xLTEuNi03LjYtMi42DQoJCQljLTEuMy0wLjUtMi41LTEuMS0zLjctMS44Yy0yLjktMS41LTUuNi0zLjMtOC4yLTUuM2wtMS45LTlsMC4xLTAuMUwtNDcuNy0wLjl6Ii8+DQoJCTxwYXRoIGlkPSJQYXRoXzk0OCIgY2xhc3M9InN0MiIgZD0iTS0xMC45LDI5LjNjLTYuNSwzLjYtMTMuNyw1LjgtMjEsNi41YzAuNC02LjcsMS0xMy4xLDEuNi0xOC44YzAuMy0yLjksMC43LTUuNywxLjEtOC4yDQoJCQljMS4yLTgsMi41LTEzLjUsMy40LTE0LjJsNi4xLDRMNC45LDcuM2wtMC41LDkuNWMtMy40LDQtNy4zLDcuNC0xMS42LDEwLjNDLTguNSwyNy45LTkuNywyOC43LTEwLjksMjkuM3oiLz4NCgkJPHBhdGggaWQ9IlBhdGhfOTQ5IiBjbGFzcz0ic3QyIiBkPSJNLTcwLjUsMjQuNmMtMS4yLTAuOS0yLjMtMS45LTMuNC0yLjlsMC45LTYuMWwwLjctMC4xbDMuMS0wLjRsNi44LDE0LjgNCgkJCUMtNjUuMiwyOC4zLTY3LjksMjYuNi03MC41LDI0LjZMLTcwLjUsMjQuNnoiLz4NCgkJPHBhdGggaWQ9IlBhdGhfOTUwIiBjbGFzcz0ic3QyIiBkPSJNOC4zLDExLjVjLTEuMiwxLjktMi41LDMuNi00LDUuM2MtMy40LDQtNy4zLDcuNC0xMS42LDEwLjNjLTEuMiwwLjgtMi40LDEuNS0zLjYsMi4ybC0wLjYtMi44DQoJCQlsMy41LTkuMWw0LjItMTEuMWw4LjgsMS4xQzYuMSw4LjcsNy4yLDEwLjEsOC4zLDExLjV6Ii8+DQoJCTxwYXRoIGlkPSJQYXRoXzk1MSIgY2xhc3M9InN0MyIgZD0iTS0yMy45LTQxLjRjLTIuNy00LjMtNi44LTcuNS0xMS42LTguOWwtMy42LDIuOWwxLjQtMy4zYy0xLjItMC4yLTIuMy0wLjItMy41LTAuMmwtMy4yLDQuMQ0KCQkJbDEuMy00Yy01LjYsMC43LTEwLjcsMy43LTE0LDguM2MtNC4xLDUuOS00LjgsMTQuMS0wLjgsMjBjMS4xLTMuNCwyLjQtNi42LDMuNS05LjljMC45LDAuMSwxLjcsMC4xLDIuNiwwbDEuMy0zLjFsMC40LDMNCgkJCWM0LjItMC40LDEwLjMtMS4yLDE0LjMtMS45bC0wLjQtMi4zbDIuMywxLjljMS4yLTAuMywxLjktMC41LDEuOS0wLjdjMi45LDQuNyw1LjgsNy43LDguOCwxMi41Qy0yMi4xLTI5LjgtMjAuMi0zNS4zLTIzLjktNDEuNHoiDQoJCQkvPg0KCQk8ZWxsaXBzZSBpZD0iRWxsaXBzZV8xODYiIGNsYXNzPSJzdDEiIGN4PSItMjQuOSIgY3k9Ii0yNi4xIiByeD0iMS4yIiByeT0iMi40Ii8+DQoJPC9nPg0KPC9nPg0KPC9zdmc+DQo=");
    	
        model.addAttribute("user", user );
        
        return "TestView/" + path;
    }
    
    @PostMapping("View/L/{path}.layer")
    public String layerViewParam(Model model, @PathVariable("path") String path, @RequestBody String jsonData) {
    	
    	try {
            // ObjectMapper를 사용하여 JSON 파싱
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonData);

            // JSON 루트에서 각 키를 모델에 동적으로 추가
            jsonNode.fieldNames().forEachRemaining(fieldName -> {
                JsonNode fieldValue = jsonNode.get(fieldName);
                model.addAttribute(fieldName, fieldValue); // JSON 키를 모델에 추가
            });

        } catch (Exception e) {
            model.addAttribute("error", "Invalid JSON Data");
        }
        
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
