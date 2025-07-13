package com.bhagyasree.EmployeeManagement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {

    private Long id;
    private String name;
    private LocalDate dob;
    private BigDecimal salary;
    private String departmentName;
    private String address;
    private String role;
    private LocalDate joinDate;
    private BigDecimal yearlyBonusPercentage;
    private String reportingManagerName;

}
