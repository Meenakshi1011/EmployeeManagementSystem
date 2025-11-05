package com.es.employee_management.service;

import com.es.employee_management.dto.LeaveApplyDTO;
import com.es.employee_management.dto.LeaveStatusUpdateDTOADMIN;
import com.es.employee_management.model.Employee;
import com.es.employee_management.model.Leaves;
import com.es.employee_management.repository.EmployeeRepo;
import com.es.employee_management.repository.LeaveRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaveService {

    @Autowired
    private LeaveRepo leaveRepo;

    @Autowired
    private EmployeeRepo employeeRepo;

    public Leaves addLeave(Leaves leave) {
        return leaveRepo.save(leave);
    }

    public Leaves updateLeave(int sno, LeaveApplyDTO dto) {
        Leaves existingLeave = getLeaveById(sno);
        existingLeave.setLeaveType(dto.getLeaveType());
        existingLeave.setFromDate(dto.getFromDate());
        existingLeave.setToDate(dto.getToDate());
        existingLeave.setDescription(dto.getDescription());
        return leaveRepo.save(existingLeave);
    }

    public void deleteLeave(int sno) {
        leaveRepo.deleteById(sno);
    }

    public Leaves getLeaveById(int sno) {
        return leaveRepo.findById(sno)
                .orElseThrow(() -> new RuntimeException("Leave not found with id: " + sno));
    }

    public Leaves updateLeaveStatus(int id, LeaveStatusUpdateDTOADMIN dto) {
        Leaves existingLeave = getLeaveById(id);
        existingLeave.setStatus(dto.getStatus());
        return leaveRepo.save(existingLeave);
    }

    public List<Leaves> getAllLeaves() {

        return leaveRepo.findAll();
    }

    public List<Leaves> getLeaveByUser(String email) {
        Employee emp = employeeRepo.findByEmailId(email)
                .orElseThrow(() -> new RuntimeException("Employee not found for email: " + email));

        return leaveRepo.findByEmployee_EmpId(emp.getEmpId());

    }
}
