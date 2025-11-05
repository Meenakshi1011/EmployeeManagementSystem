package com.es.employee_management.model;



import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data

public class Employee {


    @Id
    private int empId;
    private  String empName;
    private String role;
    private LocalDate dateOfJoin;
    private String deptName;
    private String emailId;
    private String password;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Leaves> leaves;

    @Lob
    private byte[] image;
    //default constructora
    public Employee(){}
    // parametrized constructor
    public Employee(int empId, String emailId, String empName, LocalDate dateOfJoin, byte[] image, String role, String password) {
        this.empId = empId;
        this.emailId = emailId;
        this.empName = empName;
        this.dateOfJoin = dateOfJoin;
        this.image = image;
        this.role = role;
        this.password = password;
    }


}
