package com.es.employee_management.service;

import com.es.employee_management.model.Employee;
import com.es.employee_management.model.Salary;
import com.es.employee_management.repository.EmployeeRepo;
import com.es.employee_management.repository.SalaryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SalaryService {

    @Autowired
    private SalaryRepo salaryRepo;

    @Autowired
    private EmployeeRepo employeeRepo;

    public Salary addSalary(Salary sal) {
        sal.setPayDate(LocalDateTime.now());
        return salaryRepo.save(sal);
    }

    public List<Salary> getSalary(String email) {
        // use Optional to avoid null pointer issues
        Employee emp = employeeRepo.findByEmailId(email)
                .orElseThrow(() -> new RuntimeException("Employee not found for email: " + email));

        return salaryRepo.findByEmpId(emp.getEmpId());
    }
}
