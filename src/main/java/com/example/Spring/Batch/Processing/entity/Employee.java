package com.example.Spring.Batch.Processing.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "BatchProcessingTable")
public class Employee {

    @Id
    private Long id;
    private String name;
    private Double salary;
    private Double tax;
}