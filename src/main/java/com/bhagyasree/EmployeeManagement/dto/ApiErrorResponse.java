package com.bhagyasree.EmployeeManagement.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiErrorResponse {

        private int status;
        private String message;
}
