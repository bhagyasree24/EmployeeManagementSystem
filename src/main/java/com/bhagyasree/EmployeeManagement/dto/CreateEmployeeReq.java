package com.bhagyasree.EmployeeManagement.dto;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateEmployeeReq {

    @NotBlank(message = "Name cannot be empty")
    @Size(min = 2,max = 50,message = "Employee name must be between 2 and 50 characters")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Name must contain only letters and spaces")
    private String name;

    @NotNull(message = "Date of Birth cannot be empty")
    @Past(message = "Date of Birth must be a past date")
    private LocalDate dob;

    @NotNull(message = "Salary is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Salary must be positive")
    private BigDecimal salary;

    @NotBlank(message = "Department name cannot be empty")
    @Size(min = 2,max = 50,message = "Department name must be between 2 and 50 characters")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Department name must contain only letters and spaces")
    private String departmentName;

    @NotBlank(message = "Address cannot be empty")
    @Size(min = 5, max = 100, message = "Address must be between 5 and 100 characters")
    private String address;

    @NotBlank(message = "Role cannot be empty")
    @Size(min = 2,max = 50,message = "Role name must be between 2 and 50 characters")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Role name must contain only letters and spaces")
    private String role;

    @NotNull(message = "Date of Joining cannot be empty")
    private LocalDate joinDate;

    @DecimalMin(value = "0.0", message = "Bonus must be non-negative")
    @DecimalMax(value = "100.0", message = "Bonus cannot exceed 100%")
    private BigDecimal yearlyBonusPercentage;

    @Positive(message = "Reporting Manager ID must be a positive number")
    private Long reportingManagerId;
}
