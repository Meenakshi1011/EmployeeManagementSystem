package com.es.employee_management.controller;

import com.es.employee_management.dto.AdminUpdateEmployeeDTO;
import com.es.employee_management.dto.EmployeeSelfUpdateDTO;
import com.es.employee_management.model.Employee;
import com.es.employee_management.service.EmailSenderService;
import com.es.employee_management.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmailSenderService emailSenderService ;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<Employee> addEmployee(
            @RequestParam int empId,
            @RequestParam String empName,
            @RequestParam String role,
            @RequestParam String deptName,
            @RequestParam String emailId,
            @RequestParam String password,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateOfJoin,
            @RequestParam("image") MultipartFile imageFile
    ) throws IOException {

        byte[] image = imageFile.getBytes();


        Employee saved = employeeService.addEmployee(empId, empName, role, dateOfJoin, deptName, emailId, password, image);



        String subject = "Welcome Onboard!";
        String body = "Hello " + empName + ",\n\n" +
                "Welcome to our company! 🎉\n\n" +
                "Here are your login credentials for the Employee Portal:\n" +
                "Email: " + emailId + "\n" +
                "Password: " + password + "\n\n" +
                "Please log in and update your password after first login.\n\n" +
                "Best regards,\nTeam HR";

        emailSenderService.sendEmail(emailId, subject, body);

        return ResponseEntity.ok(saved);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable int id) {
        Employee emp = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(emp);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/admin/{empId}", consumes = {"multipart/form-data"})
    public ResponseEntity<Employee> updateEmployeeByAdmin(
            @PathVariable int empId,
            @RequestParam String empName,
            @RequestParam String role,
            @RequestParam String deptName,
            @RequestParam String emailId,
            @RequestParam(required = false) String password,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateOfJoin,
            @RequestParam(value = "image", required = false) MultipartFile imageFile
    ) throws IOException {

        byte[] image = imageFile != null ? imageFile.getBytes() : null;

        AdminUpdateEmployeeDTO dto = new AdminUpdateEmployeeDTO();
        dto.setEmpName(empName);
        dto.setRole(role);
        dto.setDeptName(deptName);
        dto.setEmailId(emailId);
        dto.setPassword(password);
        dto.setDateOfJoin(dateOfJoin);
        dto.setImage(image);

        Employee updated = employeeService.updateEmployeeByAdmin(empId, dto);
        return ResponseEntity.ok(updated);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable int id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok("Employee deleted successfully");
    }




    @PreAuthorize("hasRole('EMPLOYEE')")
    @GetMapping("/user")
    public ResponseEntity<Employee> getEmployeeByEmail() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Employee employees = employeeService.getEmployeeByEmail(email);
        return ResponseEntity.ok(employees);

    }


    @PreAuthorize("hasRole('EMPLOYEE')")
    @PutMapping("/user")
    public ResponseEntity<Employee> updateEmployeeByEmail(
            @RequestParam String empName,    @RequestParam String password

    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();



        AdminUpdateEmployeeDTO dto = new AdminUpdateEmployeeDTO();
        dto.setEmpName(empName);
        dto.setPassword(password);

        Employee updated = employeeService.updateEmployeeByEmail(email, dto);
        return ResponseEntity.ok(updated);
    }








}
