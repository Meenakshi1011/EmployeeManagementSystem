package com.es.employee_management.controller;

import com.es.employee_management.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/dashboard/emplooyess")
    public String getTotalEmployees() {
        return "Total Employees: " + dashboardService.getTotalEmployees();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/dashboard/depts")
    public String getTotalDepartments() {
        return "Total Departments: " + dashboardService.getTotalDepartments();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/dashboard/status")
    public Map<String, Long> getLeaveStatus() {
        return dashboardService.getLeaveStatus();
    }
}
