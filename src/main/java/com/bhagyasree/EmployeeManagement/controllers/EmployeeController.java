package com.bhagyasree.EmployeeManagement.controllers;

import com.bhagyasree.EmployeeManagement.Entities.Employee;
import com.bhagyasree.EmployeeManagement.dto.*;
import com.bhagyasree.EmployeeManagement.mappers.EmployeeMapper;
import com.bhagyasree.EmployeeManagement.services.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;
    private final EmployeeMapper employeeMapper;

    @PostMapping
    public ResponseEntity<EmployeeDto> createEmployee(@Valid @RequestBody CreateEmployeeReq req){
        Employee createdEmployee = employeeService.createEmployee(req);
        EmployeeDto employeeDto = employeeMapper.toDto(createdEmployee);
        return new ResponseEntity<>(employeeDto, HttpStatus.CREATED);
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<EmployeeDto> updateEmployee(
            @PathVariable long id,
            @Valid @RequestBody UpdateEmployeeDto updateEmployeeDto){
         Employee updatedEmployee = employeeService.updateEmployee(id,updateEmployeeDto);
         EmployeeDto employeeDto = employeeMapper.toDto(updatedEmployee);
         return ResponseEntity.ok(employeeDto);
    }

@GetMapping()
public ResponseEntity<?> getEmployees(
        @RequestParam(required = false) Boolean lookup,
        @RequestParam(defaultValue = "1") int page) {

    int adjustedPage = Math.max(page - 1, 0);
    if (Boolean.TRUE.equals(lookup)) {
        return ResponseEntity.ok(employeeService.employeeLookup(adjustedPage));
    }

    PaginatedResponse<Employee> paginatedResponse = employeeService.getAllEmployees(adjustedPage);

    List<EmployeeDto> employeeDtos = paginatedResponse.getContent()
            .stream()
            .map(employeeMapper::toDto)
            .toList();

    PaginatedResponse<EmployeeDto> response = PaginatedResponse.<EmployeeDto>builder()
            .content(employeeDtos)
            .currentPage(paginatedResponse.getCurrentPage())
            .totalPages(paginatedResponse.getTotalPages())
            .build();

    return ResponseEntity.ok(response);
}
}
