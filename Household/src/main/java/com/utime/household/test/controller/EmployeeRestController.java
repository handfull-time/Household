package com.utime.household.test.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.utime.household.test.model.paging.Employee;
import com.utime.household.test.model.paging.Page;
import com.utime.household.test.model.paging.PagingRequest;
import com.utime.household.test.service.EmployeeService;

import lombok.Getter;
import lombok.Setter;


@Controller
@RequestMapping("DataTable")
public class EmployeeRestController {

	@Setter
	@Getter
	public static class SubItem{
		int no;
		String name;
		
		public SubItem(int n, String s) {
			this.no = n;
			this.name = s;
		}
	}
	
	@Setter
	@Getter
	public static class MainItem{
		int mainNo;
		String mainName;
		List<SubItem> subItems;
		
		public MainItem(int n, String s) {
			this.mainNo = n;
			this.mainName = s;
			
			subItems = new ArrayList<>();
		}
	}

	private final EmployeeService employeeService;
    
    @Autowired
    public EmployeeRestController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    
    private List<MainItem> getMainItems(){
    	List<MainItem> result = new ArrayList<>();
    	
    	{
    		MainItem item = new MainItem(1, "1번");
    		item.subItems.add(new SubItem(1, "Sub 1-1"));
    		item.subItems.add(new SubItem(2, "Sub 1-2"));
    		item.subItems.add(new SubItem(3, "Sub 1-3"));
    		result.add(item);
    	}
    	
    	{
    		MainItem item = new MainItem(2, "2번");
    		item.subItems.add(new SubItem(1, "Sub 2-1"));
    		item.subItems.add(new SubItem(2, "Sub 2-2"));
    		item.subItems.add(new SubItem(3, "Sub 2-3"));
    		item.subItems.add(new SubItem(4, "Sub 2-4"));
    		item.subItems.add(new SubItem(5, "Sub 2-5"));
    		result.add(item);
    	}
    	{
    		MainItem item = new MainItem(3, "3번");
    		item.subItems.add(new SubItem(1, "Sub 3-1"));
    		item.subItems.add(new SubItem(2, "Sub 3-2"));
    		item.subItems.add(new SubItem(3, "Sub 3-3"));
    		result.add(item);
    	}
    	{
    		MainItem item = new MainItem(4, "4번");
    		item.subItems.add(new SubItem(1, "Sub 4-1"));
    		item.subItems.add(new SubItem(2, "Sub 4-2"));
    		item.subItems.add(new SubItem(3, "Sub 4-3"));
    		item.subItems.add(new SubItem(4, "Sub 4-4"));
    		result.add(item);
    	}
    	
    	return result;
    }
    
    @GetMapping("/")
    public String main() {
        return "test/DataTable";
    }

    @GetMapping("CardLayout.html")
    public String cardLayout(ModelMap model) {
    	model.addAttribute("mainItems", this.getMainItems() );
        return "gptSample/CardLayout";
    }

    @GetMapping("GridLayout.html")
    public String GridLayout(ModelMap model) {
    	model.addAttribute("mainItems", this.getMainItems() );
        return "gptSample/GridLayout";
    }

    @GetMapping("TableLayout.html")
    public String TableLayout(ModelMap model) {
    	model.addAttribute("mainItems", this.getMainItems() );
        return "gptSample/TableLayout";
    }

    @GetMapping("TableLayout2.html")
    public String TableLayout2(ModelMap model) {
    	model.addAttribute("mainItems", this.getMainItems() );
        return "gptSample/TableLayout2";
    }

    @ResponseBody
    @PostMapping("Employees")
    public Page<Employee> list(@RequestBody PagingRequest pagingRequest) {
        return employeeService.getEmployees(pagingRequest);
    }

//    @PostMapping("array")
//    public PageArray array(@RequestBody PagingRequest pagingRequest) {
//        return employeeService.getEmployeesArray(pagingRequest);
//    }
    
    
}