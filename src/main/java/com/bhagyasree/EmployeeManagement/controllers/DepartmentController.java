package com.bhagyasree.EmployeeManagement.controllers;

import com.bhagyasree.EmployeeManagement.Entities.Department;
import com.bhagyasree.EmployeeManagement.Entities.Employee;
import com.bhagyasree.EmployeeManagement.dto.*;
import com.bhagyasree.EmployeeManagement.mappers.DeptMapper;
import com.bhagyasree.EmployeeManagement.mappers.EmployeeMapper;
import com.bhagyasree.EmployeeManagement.services.DepartmentService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;
    private final DeptMapper deptMapper;
    private final EmployeeMapper employeeMapper;

    @GetMapping
    public ResponseEntity<?> getAllDepartments(
            @RequestParam(defaultValue = "1") int page
    ) {
        int adjustedPage = Math.max(page - 1, 0);
        Pageable pageable = PageRequest.of(adjustedPage, 20);
        Page<Department> departmentPage = departmentService.getAllDepartments(pageable);

        List<DepartmentDto> departmentDtos = departmentPage.getContent()
                .stream()
                .map(deptMapper::toDto)
                .toList();

        PaginatedResponse<DepartmentDto> response = PaginatedResponse.<DepartmentDto>builder()
                .content(departmentDtos)
                .currentPage(page)
                .totalPages(departmentPage.getTotalPages())
                .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<DepartmentDto> createDept(@Valid @RequestBody CreateDeptReq createDeptReq){
        Department createdDepartment = departmentService.createDepartment(createDeptReq);
        DepartmentDto departmentDto = deptMapper.toDto(createdDepartment);
        return new ResponseEntity<>(departmentDto, HttpStatus.CREATED);

    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<DepartmentDto> updateDepartment(
            @PathVariable long id,
            @Valid @RequestBody UpdateDeptDto updateDeptDto){
        Department updatedDept = departmentService.updateDepartment(id,updateDeptDto);
        DepartmentDto departmentDto = deptMapper.toDto(updatedDept);
        return ResponseEntity.ok(departmentDto);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteDept(@PathVariable long id){
        departmentService.deleteDept(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<DepartmentWithEmployeesDto> getDeptAndEmployees(
            @PathVariable long id,
            @RequestParam(required = false) String expand){
        boolean expandEmployees = "employee".equalsIgnoreCase(expand);
        Department department = departmentService.getDeptAndEmpl(id, expandEmployees)
                .orElseThrow(() -> new EntityNotFoundException("Department not found"));
        DepartmentDto departmentDto = deptMapper.toDto(department);
        List<EmployeeDto> employeeDtos = expandEmployees ?
                department.getEmployees().stream()
                        .map(employeeMapper::toDto)
                        .toList() :
                Collections.emptyList();

        DepartmentWithEmployeesDto response = deptMapper.toDto(departmentDto, employeeDtos);
        return ResponseEntity.ok(response);
    }
}
