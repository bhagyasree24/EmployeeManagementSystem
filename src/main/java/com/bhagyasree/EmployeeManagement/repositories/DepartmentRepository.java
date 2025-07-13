package com.bhagyasree.EmployeeManagement.repositories;

import com.bhagyasree.EmployeeManagement.Entities.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

//    @Query("SELECT d FROM Department d LEFT JOIN FETCH d.deptHead")
//    List<Department> findAllWithDeptHead();
@EntityGraph(attributePaths = {"deptHead"})
Page<Department> findAll(Pageable pageable);

    Optional<Department> findByNameIgnoreCase(String name);
    boolean existsByNameIgnoreCase(String name);

    @Query("SELECT d FROM Department d LEFT JOIN FETCH d.employees WHERE d.id = :id")
    Optional<Department> findByIdWithEmployees(@Param("id") long id);
}
