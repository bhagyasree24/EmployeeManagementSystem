package com.bhagyasree.EmployeeManagement.mappers;

import com.bhagyasree.EmployeeManagement.Entities.Department;
import com.bhagyasree.EmployeeManagement.Entities.Employee;
import com.bhagyasree.EmployeeManagement.dto.CreateDeptReq;
import com.bhagyasree.EmployeeManagement.dto.DepartmentDto;
import com.bhagyasree.EmployeeManagement.dto.DepartmentWithEmployeesDto;
import com.bhagyasree.EmployeeManagement.dto.EmployeeDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DeptMapper {
    @Mapping(target = "departmentHeadName",source = "deptHead.name")
    DepartmentDto toDto(Department department);

    @Mapping(target = "employeeDtos", source = "employeesDto")
    DepartmentWithEmployeesDto toDto(DepartmentDto departmentDto, List<EmployeeDto> employeesDto);


}
