package com.bhagyasree.EmployeeManagement.services.impl;

import com.bhagyasree.EmployeeManagement.Entities.Department;
import com.bhagyasree.EmployeeManagement.Entities.Employee;
import com.bhagyasree.EmployeeManagement.dto.CreateDeptReq;
import com.bhagyasree.EmployeeManagement.dto.UpdateDeptDto;
import com.bhagyasree.EmployeeManagement.repositories.DepartmentRepository;
import com.bhagyasree.EmployeeManagement.repositories.EmployeeRepository;
import com.bhagyasree.EmployeeManagement.services.DepartmentService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public Page<Department> getAllDepartments(Pageable pageable) {

        return departmentRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public Department createDepartment(CreateDeptReq req){
        Employee departmentHead = null;
        if (req.getDeptHeadId() != null) {
            departmentHead = employeeRepository.findById(req.getDeptHeadId())
                    .orElseThrow(() -> new RuntimeException("Department Head not found"));
        }
        if(departmentRepository.existsByNameIgnoreCase(req.getName())){
            throw new IllegalArgumentException("Department with name "+req.getName()+" already exists");
        }

        Department department = Department.builder()
                .name(req.getName())
                .creationDate(req.getCreationDate())
                .deptHead(departmentHead)
                .build();

        Department savedDept = departmentRepository.save(department);
        if (departmentHead != null) {
            departmentHead.setLeadingDepartment(savedDept);
            employeeRepository.save(departmentHead);
        }

        return savedDept;
    }

    @Override
    @Transactional
    public Department updateDepartment(long id, UpdateDeptDto updateDeptDto) {
        Department existingDepartment = getDeptById(id)
                .orElseThrow(()-> new EntityNotFoundException("Department with id "+id+" not found"));

        if(updateDeptDto.getName()!=null){
            if(updateDeptDto.getName().isBlank()) throw new IllegalArgumentException("Department name cannot be blank");
            if(departmentRepository.existsByNameIgnoreCase(updateDeptDto.getName())){
                throw new IllegalArgumentException("Department with name "+updateDeptDto.getName()+" already exists");
            }
            existingDepartment.setName(updateDeptDto.getName());
        }

        if(updateDeptDto.getCreationDate()!=null){
            existingDepartment.setCreationDate(updateDeptDto.getCreationDate());
        }

        if(updateDeptDto.getDeptHeadId()!=null){
            Employee deptHead = employeeRepository.findById(updateDeptDto.getDeptHeadId())
                    .orElseThrow(() -> new RuntimeException("Department Head not found"));
            existingDepartment.setDeptHead(deptHead);
            deptHead.setLeadingDepartment(existingDepartment);
            employeeRepository.save(deptHead);

        }else {
            existingDepartment.setDeptHead(null);
        }
        return departmentRepository.save(existingDepartment);
    }

    @Override
    public void deleteDept(long id) {
        Optional<Department> department = getDeptById(id);
        if(department.isPresent()){
            if(!department.get().getEmployees().isEmpty()){
                throw new IllegalStateException("Department has employees associated with it");
            }
            departmentRepository.deleteById(id);
        }
    }

    @Override
    public Optional<Department> getDeptAndEmpl(long id, boolean expandEmployees) {

        if (expandEmployees) {
            return departmentRepository.findByIdWithEmployees(id);
        }
        return getDeptById(id);
    }

    @Override
    public Optional<Department> getDeptById(long id){
        return departmentRepository.findById(id);
    }

}
