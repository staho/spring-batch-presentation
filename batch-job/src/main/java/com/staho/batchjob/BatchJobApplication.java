package com.staho.batchjob;

import com.staho.batchjob.application.SpringBatchConfig;
import com.staho.batchjob.application.SpringConfig;
import com.staho.batchjob.entity.ClientRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.sql.DataSource;
import java.sql.ResultSet;

@SpringBootApplication
@EnableBatchProcessing
@EnableJpaRepositories("com.staho.batchjob.entity" )
@EntityScan("com.staho.batchjob.entity")
@ComponentScan(basePackages = {"com.staho.batchjob.entity", "com.staho.batchjob.job"} )
public class BatchJobApplication {
    @Autowired
    ClientRepository clientRepository;

    public static void main(String[] args) {
//        SpringApplication.run(BatchJobApplication.class, args);
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(BatchJobApplication.class);
        context.register(SpringConfig.class);
        context.register(SpringBatchConfig.class);
        context.refresh();

        JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
        Job job = (Job) context.getBean("firstBatchJob");
        System.out.println("Starting batch job");
        try {
            JobExecution execution = jobLauncher.run(job, new JobParameters());
            var x = (DataSource) context.getBean("dataSource");
            System.out.println("Job Status: " + execution.getStatus());
            System.out.println("Job completed");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Job failed");
        }
    }
//    public static void main(String[] args) { SpringApplication.run(BatchJobApplication.class, args); }
}
