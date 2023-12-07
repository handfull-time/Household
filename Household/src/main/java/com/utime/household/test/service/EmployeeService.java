package com.utime.household.test.service;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.utime.household.test.model.paging.Column;
import com.utime.household.test.model.paging.Employee;
import com.utime.household.test.model.paging.Order;
import com.utime.household.test.model.paging.Page;
import com.utime.household.test.model.paging.PagingRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EmployeeService {

    private static final Comparator<Employee> EMPTY_COMPARATOR = (e1, e2) -> 0;
    
    private List<Employee> employees;
    
    public EmployeeService() throws StreamReadException, DatabindException, IOException {
    	final ObjectMapper objectMapper = new ObjectMapper();
    	this.employees = objectMapper.readValue(getClass().getClassLoader()
                .getResourceAsStream("employees.json"),
                new TypeReference<List<Employee>>() {});
	}

    public Page<Employee> getEmployees(PagingRequest pagingRequest) {
    	
    	return getPage(employees, pagingRequest);
    }

    private Page<Employee> getPage(List<Employee> employees, PagingRequest pagingRequest) {
        List<Employee> filtered = this.employees.stream()
                                       .sorted(sortEmployees(pagingRequest))
                                       .filter(filterEmployees(pagingRequest))
                                       .skip(pagingRequest.getStart())
                                       .limit(pagingRequest.getLength())
                                       .collect(Collectors.toList());

        long count = this.employees.stream()
                              .filter(filterEmployees(pagingRequest))
                              .count();

        Page<Employee> page = new Page<>(filtered);
        page.setRecordsFiltered((int) count);
        page.setRecordsTotal((int) count);
        page.setDraw(pagingRequest.getDraw());

        return page;
    }

    private Predicate<Employee> filterEmployees(PagingRequest pagingRequest) {
        if (pagingRequest.getSearch() == null || ! StringUtils.hasText(pagingRequest.getSearch()
                                                                                  .getValue())) {
            return employee -> true;
        }

        String value = pagingRequest.getSearch()
                                    .getValue();

        return employee -> employee.getName()
                                   .toLowerCase()
                                   .contains(value)
                || employee.getPosition()
                           .toLowerCase()
                           .contains(value)
                || employee.getOffice()
                           .toLowerCase()
                           .contains(value);
    }

    private Comparator<Employee> sortEmployees(PagingRequest pagingRequest) {
        if (pagingRequest.getOrder() == null) {
            return EMPTY_COMPARATOR;
        }

        try {
            Order order = pagingRequest.getOrder()
                                       .get(0);

            int columnIndex = order.getColumn();
            Column column = pagingRequest.getColumns()
                                         .get(columnIndex);

            Comparator<Employee> comparator = EmployeeComparators.getComparator(column.getData(), order.getDir());
            if (comparator == null) {
                return EMPTY_COMPARATOR;
            }

            return comparator;

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return EMPTY_COMPARATOR;
    }
}