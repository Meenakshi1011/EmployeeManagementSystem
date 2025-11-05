package com.es.employee_management.service;

import com.es.employee_management.model.Department;
import com.es.employee_management.model.Employee;
import com.es.employee_management.repository.DepartmentRepo;
import com.es.employee_management.repository.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;


@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepo departmentRepo;

    @Autowired
    private  EmployeeRepo employeeRepo;


    public String addDepartment(Department dept){
         departmentRepo.save(dept);
         return "Department saved Successfully";
    }

    public List<Department> getAllDepartment(){
        return  departmentRepo.findAll();
    }


    public Department getDepartmentById(int id) {
        return departmentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found with ID: " + id));
    }

    public String updateDepartment(int id,Department dept){
        Department existing = departmentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Department id not found " + id));
        existing.setDeptName(dept.getDeptName());
        departmentRepo.save(existing);
        return "Department saved Successfully";
    }

    public String deleteDepartment(int id) {
        if (!departmentRepo.existsById(id)) {
            throw new RuntimeException("Department not found with ID: " + id);
        }
        departmentRepo.deleteById(id);
        return "Department deleted successfully with ID: " + id;
    }

    //get department for the login usser
    public  Department getDepartment(String emailName){

        Employee emp = employeeRepo.findByEmailId(emailName)
                .orElseThrow(() -> new RuntimeException("Employee not found for email: " + emailName));

        Department dept = departmentRepo.findByDeptName(emp.getDeptName())
                .orElseThrow(() -> new RuntimeException("Department not found for: " + emp.getDeptName()));

        return dept;

    }

}
