package com.bhagyasree.EmployeeManagement.dto;

import com.bhagyasree.EmployeeManagement.Entities.Employee;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentWithEmployeesDto {

    private long id;
    private String name;
    private LocalDate creationDate;
    private String departmentHeadName;
    private List<EmployeeDto> employeeDtos;
}
