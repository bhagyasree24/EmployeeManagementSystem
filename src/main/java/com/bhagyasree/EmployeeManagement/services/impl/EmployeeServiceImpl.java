package com.bhagyasree.EmployeeManagement.services.impl;

import com.bhagyasree.EmployeeManagement.Entities.Department;
import com.bhagyasree.EmployeeManagement.Entities.Employee;
import com.bhagyasree.EmployeeManagement.dto.CreateEmployeeReq;
import com.bhagyasree.EmployeeManagement.dto.EmployeeLookupDto;
import com.bhagyasree.EmployeeManagement.dto.PaginatedResponse;
import com.bhagyasree.EmployeeManagement.dto.UpdateEmployeeDto;
import com.bhagyasree.EmployeeManagement.repositories.DepartmentRepository;
import com.bhagyasree.EmployeeManagement.repositories.EmployeeRepository;
import com.bhagyasree.EmployeeManagement.services.EmployeeService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;


@Override
public PaginatedResponse<Employee> getAllEmployees(int page) {
    Pageable pageable = PageRequest.of(page, 20);
    Page<Employee> employeePage = employeeRepository.findAll(pageable);

    return PaginatedResponse.<Employee>builder()
            .content(employeePage.getContent())
            .currentPage(employeePage.getNumber()+1)
            .totalPages(employeePage.getTotalPages())
            .build();
}

    @Override
    @Transactional
    public Employee createEmployee(CreateEmployeeReq request) {

        Employee reportingManager = null;

        if (request.getReportingManagerId() != null) {
            reportingManager = employeeRepository.findById(request.getReportingManagerId())
                    .orElseThrow(() -> new EntityNotFoundException("Reporting manager not found"));
        } else {

            if (employeeRepository.existsByReportingManagerIsNull()) {
                throw new RuntimeException("Top-level employee already exists");
            }
        }

        Department department = departmentRepository.findByNameIgnoreCase(request.getDepartmentName())
                .orElseThrow(() -> new EntityNotFoundException("Department not found"));

        Employee employee = Employee.builder()
                .name(request.getName())
                .dob(request.getDob())
                .salary(request.getSalary())
                .department(department)
                .address(request.getAddress())
                .role(request.getRole())
                .joinDate(request.getJoinDate())
                .yearlyBonusPercentage(request.getYearlyBonusPercentage())
                .reportingManager(reportingManager)
                .build();

        return employeeRepository.save(employee);

    }

    @Override
    @Transactional
    public Employee updateEmployee(long id, UpdateEmployeeDto dto) {
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Employee does not exist with id: "+id));

        if (dto.getName() != null) {
            if (dto.getName().isBlank()) throw new IllegalArgumentException("Name cannot be blank");
            existingEmployee.setName(dto.getName());
        }

        if (dto.getDob() != null) {
            existingEmployee.setDob(dto.getDob());
        }

        if (dto.getSalary() != null) {
            existingEmployee.setSalary(dto.getSalary());
        }

        if (dto.getDepartmentName() != null) {
            Department department = departmentRepository.findByNameIgnoreCase(dto.getDepartmentName())
                    .orElseThrow(() -> new EntityNotFoundException("Department not found: " + dto.getDepartmentName()));
            existingEmployee.setDepartment(department);
        }

        if (dto.getAddress() != null) {
            existingEmployee.setAddress(dto.getAddress());
        }

        if (dto.getRole() != null) {
            existingEmployee.setRole(dto.getRole());
        }

        if (dto.getJoinDate() != null) {
            existingEmployee.setJoinDate(dto.getJoinDate());
        }

        if (dto.getYearlyBonusPercentage() != null) {
            existingEmployee.setYearlyBonusPercentage(dto.getYearlyBonusPercentage());
        }

        if (dto.getReportingManagerId() != null) {
            if (dto.getReportingManagerId().equals(id)) {
                throw new IllegalArgumentException("An employee cannot report to themselves");
            }
            Employee manager = employeeRepository.findById(dto.getReportingManagerId())
                    .orElseThrow(() -> new EntityNotFoundException("Reporting manager not found"));
            existingEmployee.setReportingManager(manager);
        }else {
            existingEmployee.setReportingManager(null);
        }

        return employeeRepository.save(existingEmployee);
    }

@Override
public PaginatedResponse<EmployeeLookupDto> employeeLookup(int page) {
    Pageable pageable = PageRequest.of(page, 20);
    Page<Employee> employeePage = employeeRepository.findAll(pageable);

    List<EmployeeLookupDto> content = employeePage.getContent()
            .stream()
            .map(emp -> new EmployeeLookupDto(emp.getId(), emp.getName()))
            .toList();

    return PaginatedResponse.<EmployeeLookupDto>builder()
            .content(content)
            .currentPage(employeePage.getNumber()+1)
            .totalPages(employeePage.getTotalPages())
            .build();
}
}
