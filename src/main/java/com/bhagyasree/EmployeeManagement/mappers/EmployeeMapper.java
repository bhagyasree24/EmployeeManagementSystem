package com.bhagyasree.EmployeeManagement.mappers;

import com.bhagyasree.EmployeeManagement.Entities.Employee;
import com.bhagyasree.EmployeeManagement.dto.CreateEmployeeReq;
import com.bhagyasree.EmployeeManagement.dto.EmployeeDto;
import com.bhagyasree.EmployeeManagement.dto.EmployeeLookupDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EmployeeMapper {

    @Mapping(target = "departmentName", source = "department.name")
    @Mapping(target = "reportingManagerName", source = "reportingManager.name")
    EmployeeDto toDto(Employee employee);
    EmployeeLookupDto toLookupDto(Employee employee);


}
