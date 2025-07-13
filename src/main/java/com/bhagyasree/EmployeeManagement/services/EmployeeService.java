package com.bhagyasree.EmployeeManagement.services;

import com.bhagyasree.EmployeeManagement.Entities.Employee;
import com.bhagyasree.EmployeeManagement.dto.*;

import java.util.List;

public interface EmployeeService {

    PaginatedResponse<Employee> getAllEmployees(int page);
    Employee createEmployee(CreateEmployeeReq request);
    Employee updateEmployee(long id, UpdateEmployeeDto updateEmployeeDto);
    PaginatedResponse<EmployeeLookupDto> employeeLookup(int page);
}
