package com.es.employee_management.repository;

import com.es.employee_management.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentRepo extends JpaRepository<Department,Integer> {
    Optional<Department> findByDeptName(String deptName);
}
