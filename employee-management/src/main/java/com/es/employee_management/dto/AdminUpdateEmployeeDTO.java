package com.es.employee_management.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class AdminUpdateEmployeeDTO {
    private String empName;
    private String role;
    private LocalDate dateOfJoin;
    private String deptName;
    private String emailId;
    private String password;
    private byte[] image;
}
