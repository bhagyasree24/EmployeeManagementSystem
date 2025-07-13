package com.bhagyasree.EmployeeManagement.repositories;

import com.bhagyasree.EmployeeManagement.Entities.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {


//    @Query("SELECT e FROM Employee e LEFT JOIN FETCH e.department LEFT JOIN FETCH e.reportingManager")
//    Page<Employee> findAllWithDeptAndManager(Pageable pageable);
@EntityGraph(attributePaths = {"department", "reportingManager"})
Page<Employee> findAll(Pageable pageable);
    boolean existsByReportingManagerIsNull();

}
