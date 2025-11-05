package com.es.employee_management.controller;

import com.es.employee_management.model.Salary;
import com.es.employee_management.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/salary")
public class SalaryController {

    @Autowired
    private SalaryService salaryService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<String> addSalary(@RequestBody Salary sal){



        salaryService.addSalary(sal);
        return ResponseEntity.ok("salary added successfully");
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @GetMapping("/paySlip")
    public ResponseEntity<List<Salary>> getSalary(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        List<Salary> salary = salaryService.getSalary(email);
        return ResponseEntity.ok(salary);
    }
}
