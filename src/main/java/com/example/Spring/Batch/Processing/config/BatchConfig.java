package com.example.Spring.Batch.Processing.config;

import com.example.Spring.Batch.Processing.entity.Employee;
import com.example.Spring.Batch.Processing.processor.EmployeeProcessor;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class BatchConfig {

    private final EntityManagerFactory entityManagerFactory;
    private final PlatformTransactionManager transactionManager;
    @Bean
    public FlatFileItemReader<Employee> reader() {
        return new FlatFileItemReaderBuilder<Employee>()
                .name("employeeReader")
                .resource(new ClassPathResource("employees.csv"))
                .delimited()
                .names("id", "name", "salary")
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
                    setTargetType(Employee.class);
                }})
                .build();
    }
    @Bean
    public JpaItemWriter<Employee> writer(){
    JpaItemWriter<Employee> writer=new JpaItemWriter<>();
    writer.setEntityManagerFactory(entityManagerFactory);
    return writer;
    }
    @Bean
    public Step step(JobRepository jobRepository,
                     PlatformTransactionManager transactionManager,
                     FlatFileItemReader<Employee> reader,
                     EmployeeProcessor processor,
                     JpaItemWriter<Employee> writer) {

        return new StepBuilder("step1", jobRepository)
                .<Employee, Employee>chunk(5, transactionManager)  // ✅ Correct
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }


    @Bean
    public Job job(JobRepository jobRepository,Step step){
        return new JobBuilder("employeeJob",jobRepository)
                .start(step)
                .build();
    }
}
