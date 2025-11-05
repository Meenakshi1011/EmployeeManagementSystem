package com.es.employee_management.repository;

import com.es.employee_management.model.Leaves;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaveRepo extends JpaRepository<Leaves,Integer> {
    List<Leaves> findByEmployee_EmpId(int employeeId);

    @Query("SELECT l.status, COUNT(l.status) FROM Leaves l GROUP BY l.status")
    List<Object[]> findStatusCount();

}
