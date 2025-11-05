package com.es.employee_management.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;


import java.time.LocalDateTime;

@Entity
@Data
public class Salary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int sno;
    @JsonProperty("emp_id")
    private int empId;

    private int salary;
    private int allowance;
    private int deduction;
    private int total;

    @JsonFormat(pattern="dd-MM-YYYY")

    private LocalDateTime payDate;
}
