package com.es.employee_management.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class LeaveApplyDTO {

    private String leaveType;

    @JsonProperty("from_date")
    @JsonFormat(pattern="dd-MM-yyyy")
    private LocalDate fromDate;

    @JsonProperty("to_date")
    @JsonFormat(pattern="dd-MM-yyyy")
    private LocalDate toDate;

    private  String status = "PENDING";

    private String description;

    private String name;
    private String deptName;



}
