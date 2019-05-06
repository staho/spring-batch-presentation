package com.staho.batchjob.application;

import com.staho.batchjob.entity.Client;
import com.staho.batchjob.entity.ClientRepository;
import com.staho.batchjob.job.ClientEnhancer;
import com.staho.batchjob.job.ClientProcessor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.*;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.json.GsonJsonObjectReader;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
import org.springframework.batch.item.xml.StaxEventItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.io.File;
import java.net.MalformedURLException;
@Configuration
public class SpringBatchConfig {

    @Autowired
    private JobBuilderFactory jobs;

    @Autowired
    private StepBuilderFactory steps;

    @Autowired
    private ClientRepository clientRepository;

    @Value("clients.json")
    private ClassPathResource inputJson;


    @Bean
    public ItemReader<Client> itemReader()
        throws UnexpectedInputException, ParseException, MalformedURLException {
        return new JsonItemReaderBuilder<Client>()
                .name("jsonClientReader")
                .jsonObjectReader(new GsonJsonObjectReader<>(Client.class))
                .resource(inputJson)
                .build();
            }

    @Bean("clientProcessor")
    public ItemProcessor<Client, Client> itemProcessor() {
        return new ClientProcessor();
    }

    @Bean("clientEnhancer")
    public ItemProcessor<Client, Client> clientEnhancer() { return new ClientEnhancer(); }

    @Bean
    public ItemWriter<Client> itemWriter() throws MalformedURLException {
        RepositoryItemWriter<Client> itemWriter = new RepositoryItemWriter<>();
        itemWriter.setRepository(clientRepository);
        itemWriter.setMethodName("save");
        return itemWriter;
    }


}
