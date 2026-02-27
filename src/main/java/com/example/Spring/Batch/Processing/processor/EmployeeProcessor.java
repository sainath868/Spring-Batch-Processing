package com.example.Spring.Batch.Processing.processor;

import com.example.Spring.Batch.Processing.entity.Employee;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class EmployeeProcessor implements ItemProcessor<Employee,Employee> {
    @Override
    public Employee process(Employee employee){
        double tax=employee.getSalary()*0.10;
        employee.setSalary(tax);
        return employee;
    }
}
