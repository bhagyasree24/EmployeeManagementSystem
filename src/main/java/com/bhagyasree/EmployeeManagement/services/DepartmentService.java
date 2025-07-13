package com.bhagyasree.EmployeeManagement.services;

import com.bhagyasree.EmployeeManagement.Entities.Department;
import com.bhagyasree.EmployeeManagement.Entities.Employee;
import com.bhagyasree.EmployeeManagement.dto.CreateDeptReq;
import com.bhagyasree.EmployeeManagement.dto.DepartmentDto;
import com.bhagyasree.EmployeeManagement.dto.UpdateDeptDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface DepartmentService {

    Page<Department> getAllDepartments(Pageable pageable);
    Department createDepartment(CreateDeptReq req);
    Department updateDepartment(long id, UpdateDeptDto updateDeptDto);
    void deleteDept(long id);
    Optional<Department> getDeptById(long id);
    Optional<Department> getDeptAndEmpl(long id, boolean expandEmployees);

}
