package com.es.employee_management.controller;


import com.es.employee_management.model.Department;
import com.es.employee_management.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;
//post
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/department")
    public  ResponseEntity<String>  addDepartment(@RequestBody Department dept){
        String response = departmentService.addDepartment(dept);
        return ResponseEntity.ok(response);
    }

//getall
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/department")
    public  ResponseEntity<List<Department>> getAllDepartment(){
        List<Department> response = departmentService.getAllDepartment();
        return ResponseEntity.ok(response);
    }

//getby -id for employee
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/department/{id}")
    public  ResponseEntity<Department> getDepartmentById(@PathVariable int id){
        Department response = departmentService.getDepartmentById(id);
        return ResponseEntity.ok(response);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/department/{id}")
    public  ResponseEntity<String> updateDepartment(@PathVariable int id ,@RequestBody Department dept){
        String response = departmentService.updateDepartment(id,dept);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/department/{id}")
    public  ResponseEntity<String> deleteDepartment(@PathVariable int id ){
        String response = departmentService.deleteDepartment(id);
        return ResponseEntity.ok(response);

    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @GetMapping("/department/mydept")
    public  ResponseEntity<Department> getDepartment(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String emailName = authentication.getName();


        Department response = departmentService.getDepartment(emailName);
        return ResponseEntity.ok(response);
    }



}
