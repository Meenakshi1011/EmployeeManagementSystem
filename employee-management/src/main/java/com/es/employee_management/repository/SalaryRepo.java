package com.es.employee_management.repository;

import com.es.employee_management.model.Salary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SalaryRepo extends JpaRepository<Salary,Integer> {
    List<Salary> findByEmpId(int id);
}
