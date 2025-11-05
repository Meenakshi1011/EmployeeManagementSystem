package com.es.employee_management.service;

import com.es.employee_management.dto.AdminUpdateEmployeeDTO;
import com.es.employee_management.dto.EmployeeSelfUpdateDTO;
import com.es.employee_management.model.Employee;
import com.es.employee_management.repository.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Employee addEmployee(int empId, String empName, String role, LocalDate dateOfJoin,
                                String deptName, String emailId, String password, byte[] image) {

        String encodedPassword = passwordEncoder.encode(password);

        Employee employee = new Employee();
        employee.setEmpId(empId);
        employee.setEmpName(empName);
        employee.setRole(role);
        employee.setDateOfJoin(dateOfJoin);
        employee.setDeptName(deptName);
        employee.setEmailId(emailId);
        employee.setPassword(encodedPassword);
        employee.setImage(image);

        return employeeRepo.save(employee);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepo.findAll();
    }

   public Employee getEmployeeById(int id){
        return employeeRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + id));
    }


    public Employee updateEmployeeByAdmin(int empId, AdminUpdateEmployeeDTO dto) {
        Employee existingEmployee = employeeRepo.findById(empId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        existingEmployee.setEmpName(dto.getEmpName());
        existingEmployee.setRole(dto.getRole());
        existingEmployee.setDateOfJoin(dto.getDateOfJoin());
        existingEmployee.setDeptName(dto.getDeptName());
        existingEmployee.setEmailId(dto.getEmailId());

        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            existingEmployee.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        if (dto.getImage() != null) {
            existingEmployee.setImage(dto.getImage());
        }

        return employeeRepo.save(existingEmployee);
    }



    public void deleteEmployee(int id){
        if(!employeeRepo.existsById(id)){
            throw new RuntimeException("Employee not found with ID: " + id);
        }
        employeeRepo.deleteById(id);
    }



    public Employee getEmployeeByEmail(String email){


        return employeeRepo.findByEmailId(email)
                .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + email));
    }


    public Employee updateEmployeeByEmail(String email, AdminUpdateEmployeeDTO dto) {
        Employee existingEmployee = employeeRepo.findByEmailId(email)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        existingEmployee.setEmpName(dto.getEmpName());

        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            existingEmployee.setPassword(passwordEncoder.encode(dto.getPassword()));
        }


        return employeeRepo.save(existingEmployee);


    }



}
