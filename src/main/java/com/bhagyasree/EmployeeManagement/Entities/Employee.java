package com.bhagyasree.EmployeeManagement.Entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "employees")
@Entity
@Builder
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dob;

    @Column(nullable = false)
    private BigDecimal salary;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id",nullable = false)
    private Department department;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String role;

    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate joinDate;

    private BigDecimal yearlyBonusPercentage;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reportingManager_id")
    private Employee reportingManager;

    @JsonIgnore
    @OneToMany(mappedBy = "reportingManager")
    private List<Employee> directReports = new ArrayList<>();

    @JsonIgnore
    @OneToOne(mappedBy = "deptHead", fetch = FetchType.LAZY)
    private Department leadingDepartment;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id) && Objects.equals(name, employee.name) && Objects.equals(dob, employee.dob) && Objects.equals(salary, employee.salary)  && Objects.equals(address, employee.address) && Objects.equals(role, employee.role) && Objects.equals(joinDate, employee.joinDate) && Objects.equals(yearlyBonusPercentage, employee.yearlyBonusPercentage) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, dob, salary, address, role, joinDate, yearlyBonusPercentage);
    }
}
