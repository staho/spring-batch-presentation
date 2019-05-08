package com.staho.batchjob.job;

import com.staho.batchjob.BadUuidException;
import com.staho.batchjob.entity.Client;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
@EnableBatchProcessing
public class JobsAndTasks {

    @Autowired
    private JobBuilderFactory jobs;

    @Autowired
    private StepBuilderFactory steps;

    @Bean
    protected Step step1(ItemReader<Client> reader,
                         @Qualifier("clientProcessor") ItemProcessor<Client, Client> processor,
                         @Qualifier("clientEnhancer") ItemProcessor<Client, Client> enhancer,
                         ItemWriter<Client> writer) {

        CompositeItemProcessor<Client, Client> processAndEnhance = new CompositeItemProcessor<>();

        processAndEnhance.setDelegates(Arrays.asList(
                processor,
                enhancer
        ));



        return steps.get("step1")
                .<Client, Client> chunk(2)
                .reader(reader)
                .processor(processAndEnhance)
                .writer(writer)
                .faultTolerant()
                .retryLimit(5)
                .retry(BadUuidException.class)
                .build();
    }

    @Bean(name="firstBatchJob")
    public Job job(@Qualifier("step1") Step step1) {
        return jobs.get("firstBatchJob").start(step1).build();
    }
}
