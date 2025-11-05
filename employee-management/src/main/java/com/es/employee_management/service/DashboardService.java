package com.es.employee_management.service;

import com.es.employee_management.repository.DepartmentRepo;
import com.es.employee_management.repository.EmployeeRepo;
import com.es.employee_management.repository.LeaveRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DashboardService {

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private DepartmentRepo departmentRepo;

    @Autowired
    private LeaveRepo leaveRepo;

    public Integer getTotalEmployees() {
        return (int) employeeRepo.count();
    }

    public Integer getTotalDepartments() {
        return (int) departmentRepo.count();
    }

    public Map<String, Long> getLeaveStatus() {
        List<Object[]> results = leaveRepo.findStatusCount();

        Map<String, Long> statusCount = new HashMap<>();
        for (Object[] row : results) {
            String status = (String) row[0];
            Long count = (Long) row[1];
            statusCount.put(status, count);
        }
        return statusCount;
    }
}
