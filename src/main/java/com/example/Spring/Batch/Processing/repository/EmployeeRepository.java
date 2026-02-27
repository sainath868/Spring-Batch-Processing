package com.example.Spring.Batch.Processing.repository;

import com.example.Spring.Batch.Processing.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {
}
