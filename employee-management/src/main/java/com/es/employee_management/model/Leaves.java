package com.es.employee_management.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
public class Leaves {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int sno;

    private String leaveType;

    @JsonFormat(pattern="dd-MM-YYYY")
    private LocalDate fromDate;

    @JsonFormat(pattern="dd-MM-YYYY")
    private LocalDate toDate;

    private String description;

    private  String status = "PENDING";

    @JsonFormat(pattern="dd-MM-YYYY")
    private LocalDateTime appliedDate;


    @ManyToOne
    @JoinColumn(name  = "empId", referencedColumnName = "empId")
    @JsonIgnore
    private Employee employee;

    private String name;
    private String deptName;

}
