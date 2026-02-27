package com.example.Spring.Batch.Processing.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.job.parameters.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class JobController {

    private final JobLauncher jobLauncher;
    private final Job job;

    @GetMapping("/run")
    public String runJob() throws Exception {

        jobLauncher.run(job,
                new JobParametersBuilder()
                        .addLong("time", System.currentTimeMillis())
                        .toJobParameters());

        return "Batch Job Started Successfully!";
    }
}