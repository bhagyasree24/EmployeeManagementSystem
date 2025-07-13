package com.bhagyasree.EmployeeManagement.dto;

import com.bhagyasree.EmployeeManagement.Entities.Employee;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateDeptReq {

    @NotBlank(message = "Department name cannot be empty")
    @Size(min = 2,max = 50,message = "Department name must be between 2 and 50 characters")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Department name must contain only letters and spaces")
    private String name;

    @NotNull(message = "Creation date cannot be empty")
    @PastOrPresent(message = "Creation date must be in the past or present")
    private LocalDate creationDate;

    @Positive(message = "Department head ID must be a positive number")
    private Long deptHeadId;
}
