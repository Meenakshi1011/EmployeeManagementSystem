package com.es.employee_management.controller;

import com.es.employee_management.dto.LeaveApplyDTO;
import com.es.employee_management.dto.LeaveStatusUpdateDTOADMIN;
import com.es.employee_management.model.Employee;
import com.es.employee_management.model.Leaves;
import com.es.employee_management.repository.EmployeeRepo;
import com.es.employee_management.service.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
public class LeaveController {

    @Autowired
    private LeaveService leaveService;

    @Autowired
    private EmployeeRepo employeeRepo;

    @PreAuthorize("hasRole('EMPLOYEE')")
    @PostMapping("/leave")
    public ResponseEntity<String> addLeave(@RequestBody LeaveApplyDTO dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Optional<Employee> emp = employeeRepo.findByEmailId(email);

        if (emp == null) {
            return ResponseEntity.badRequest().body("Employee not found for email: " + email);
        }
        Employee allEmp = emp.get();
        Leaves leave = new Leaves();
        leave.setLeaveType(dto.getLeaveType());
        leave.setFromDate(dto.getFromDate());
        leave.setToDate(dto.getToDate());
        leave.setDescription(dto.getDescription());
        leave.setStatus("PENDING");
        leave.setAppliedDate(LocalDateTime.now());

        leave.setEmployee(allEmp);
        leave.setDeptName(allEmp.getDeptName());
        leave.setName(allEmp.getEmpName());

        leaveService.addLeave(leave);
        return ResponseEntity.ok("Leave created successfully");
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @PutMapping("/leave/{sno}")
    public ResponseEntity<String> updateLeave(@PathVariable int sno, @RequestBody LeaveApplyDTO dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Optional<Employee> emp = employeeRepo.findByEmailId(email);

        Leaves existingLeave = leaveService.getLeaveById(sno);


        if (existingLeave.getStatus().equalsIgnoreCase("REJECTED") ||
                existingLeave.getStatus().equalsIgnoreCase("APPROVED")) {
            return ResponseEntity.badRequest().body("Leave cannot be updated after approval/rejection");
        }

        leaveService.updateLeave(sno, dto);
        return ResponseEntity.ok("Leave updated successfully");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/updateStatus/{sno}")
    public ResponseEntity<String> updateLeaveStatus(@PathVariable int sno, @RequestBody LeaveStatusUpdateDTOADMIN dto) {
        leaveService.updateLeaveStatus(sno, dto);
        return ResponseEntity.ok("Leave status updated successfully");
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @DeleteMapping("/leave/{sno}")
    public ResponseEntity<String> deleteLeave(@PathVariable int sno) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Optional<Employee> emp = employeeRepo.findByEmailId(email);

        Leaves leave = leaveService.getLeaveById(sno);



        if (leave.getStatus().equalsIgnoreCase("REJECTED") ||
                leave.getStatus().equalsIgnoreCase("APPROVED")) {
            return ResponseEntity.badRequest().body("Leave cannot be deleted after approval/rejection");
        }

        leaveService.deleteLeave(sno);
        return ResponseEntity.ok("Leave deleted successfully");
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/leave")
    public ResponseEntity<List<Leaves>> getAllLeaves() {
        List<Leaves> leave = leaveService.getAllLeaves();
        return ResponseEntity.ok(leave);
    }


    @PreAuthorize("hasRole('EMPLOYEE')")
    @GetMapping("/leave/me")
    public ResponseEntity<List<Leaves>> getMyLeaves() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        List<Leaves> leave = leaveService.getLeaveByUser(email);
        return ResponseEntity.ok(leave);
    }
}
